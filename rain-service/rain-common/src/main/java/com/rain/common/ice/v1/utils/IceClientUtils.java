package com.rain.common.ice.v1.utils;

import com.rain.common.ice.v1.message.MessageServicePrx;
import com.rain.common.ice.v1.message.MsgRequest;
import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.uitls.AppUtils;
import com.rain.common.uitls.DateUtils;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class IceClientUtils {
    private static volatile Communicator ic = null;
    private static final Map<Class, ObjectPrx> cls2PrxMap = new HashMap<>();
    private static volatile long lastAccessTimestamp;
    private static volatile MonitorThread nonitorThread;
    private static final long idleTimeOutSeconds = 60;//60 没执行成功，关闭ice
    private static final String locatorKey = "--Ice.Default.Locator";
    private static final String ICE_GRID_PROPS = "icegrid.properties";

    public static Communicator getICECommunictor() {
        if (ic == null) {
            synchronized (IceClientUtils.class) {
                Properties iceGrid = getProperties(ICE_GRID_PROPS);
                String iceLocator = iceGrid.getProperty("iceLocator");
                log.info("{} Ice client's locator is {} proxy cache time out seconds:{}", DateUtils.getStrCurrtTime()
                        , iceLocator, idleTimeOutSeconds);
                String[] initParams = new String[]{locatorKey + "=" + iceLocator};
                ic = Util.initialize(initParams);
                createMonitorThread();
            }
        }
        lastAccessTimestamp = System.currentTimeMillis();
        return ic;
    }

    public static Properties getProperties(String config) {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = AppUtils.getInstance().getEnvResource(config);
            prop.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("{}", e);
                }
            }
        }
        return prop;
    }

    private static void createMonitorThread() {
        nonitorThread = new MonitorThread();
        nonitorThread.setDaemon(true);
        nonitorThread.start();
    }

    public static void closeCommunicator(boolean removeServiceCache) {
        synchronized (IceClientUtils.class) {
            if (ic == null)
                return;
            safeShutdown();
            nonitorThread.interrupt();
            if (removeServiceCache && !cls2PrxMap.isEmpty()) {
                try {
                    cls2PrxMap.clear();
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }
        }
    }

    private static void safeShutdown() {
        try {
            ic.shutdown();
        } catch (Exception e) {
            log.error("{}", e);
        } finally {
            ic.destroy();
            ic = null;
        }
    }


    /**
     * 仅限于Ice服务内部之间非异步方法的场景
     *
     * @param communicator
     * @param serviceCls
     * @return ObjectPrx
     */
   /* @SuppressWarnings("rawtypes")
    public static ObjectPrx getSerivcePrx(Communicator communicator, Class serviceCls, String version) {
        return createIceProxy(communicator, serviceCls, version);
    }

    @SuppressWarnings("rawtypes")
    private static ObjectPrx createIceProxy(Communicator communicator, Class serviceCls, String Version) {
        ObjectPrx proxy = null;
        String clsName = serviceCls.getName();
        String serviceName = serviceCls.getSimpleName();
        int pos = serviceName.lastIndexOf("Prx");
        if (pos <= 0) {
            throw new IllegalArgumentException("Invalid ObjectPrx class ,class name must end with Prx");
        }
        String realSvName = serviceName.substring(0, pos);
        try {
            ObjectPrx base = communicator.stringToProxy(realSvName);
            proxy = (ObjectPrx) Class.forName(clsName + "Helper").newInstance();
            Method m1 = proxy.getClass().getDeclaredMethod("checkedCast", ObjectPrx.class, String.class);
            proxy = (ObjectPrx) m1.invoke(proxy, base, Version == null ? null : Version.toUpperCase());
            return proxy;
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("{}", e);
            throw new RuntimeException(e);
        }
    }*/

   /* public static ObjectPrx getSerivcePrx(Class<?> serviceCls) {
        return getSerivcePrx(serviceCls, null);
    }*/

    /**
     * 用于客户端API获取ICE服务实例的场景
     *
     * @param
     * @return ObjectPrx
     */
    /*public static ObjectPrx getSerivcePrx(Class serviceCls, String Version) {
        ObjectPrx proxy = cls2PrxMap.get(serviceCls);
        if (proxy != null) {
            lastAccessTimestamp = System.currentTimeMillis();
            return proxy;
        }
        proxy = createIceProxy(getICECommunictor(), serviceCls, Version);
        cls2PrxMap.put(serviceCls, proxy);
        lastAccessTimestamp = System.currentTimeMillis();
        return proxy;
    }*/

    public static String doService(IceRequest iceRequest) {
        return doService(iceRequest, "MessageService:default -p 20000");
    }

    public static String doService(IceRequest iceRequest, String servcieName) {
        try {
            Communicator ic = getICECommunictor();
            ObjectPrx base = ic.stringToProxy(servcieName);
            MessageServicePrx messageServicePrx = MessageServicePrx.checkedCast(base);
            MsgRequest in = new MsgRequest(iceRequest.getService(), iceRequest.getMethod(),
                    iceRequest.getExtData(), iceRequest.getAttr());
            return messageServicePrx.doInvoke(in);
        } catch (Exception e) {
            log.error("{}", e);
            throw new RuntimeException(e);
        }

    }

    static class MonitorThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(5000L);
                    if (lastAccessTimestamp + idleTimeOutSeconds * 1000L < System.currentTimeMillis()) {
                        closeCommunicator(true);
                    }
                } catch (Exception e) {
                    log.error("{}", e);
                }

            }
        }
    }

    /*    *//*
     * private void setIceLocator(String iceLocator) { ICEClientUtil.iceLocator
     * = iceLocator; }
     *
     * @Value("${Ice.idleTimeOutSeconds}") private void
     * setIdleTimeOutSeconds(long idleTimeOutSeconds) {
     * ICEClientUtil.idleTimeOutSeconds = idleTimeOutSeconds;
     *
     * }
     */
/*    public static void main(String[] args) {
        Properties icegrid = getProperties("icegrid.properties");
        if (icegrid != null) {
            String iceLocator = icegrid.getProperty("iceLocator");
            System.out.println(iceLocator);
        }
    }*/
}
