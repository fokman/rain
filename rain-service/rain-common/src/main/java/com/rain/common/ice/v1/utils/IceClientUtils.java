package com.rain.common.ice.v1.utils;

import com.rain.common.ice.v1.message.MessageServicePrx;
import com.rain.common.ice.v1.message.MsgRequest;
import com.rain.common.ice.v1.message._MessageServicePrxI;
import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.uitls.AppUtils;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class IceClientUtils {
    private static volatile Communicator ic = null;
    private static Map<Class, ObjectPrx> cls2PrxMap = new HashMap<>();
    private static volatile long lastAccessTimestamp;
    private static volatile MonitorThread nonitorThread;
    private static long idleTimeOutSeconds = 60;//60 没执行成功，关闭ice
    private static String iceLocator = null;
    private static final String locatorKey = "--Ice.Default.Locator";

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

    public static Communicator getICECommunictor() {
        if (ic == null) {
            synchronized (IceClientUtils.class) {
                if (ic == null) {
                    if (iceLocator == null) {
                        Properties icegrid = getProperties("icegrid.properties");
                        if (icegrid != null) {
                            iceLocator = icegrid.getProperty("iceLocator");
                        }
                        if (iceLocator == null)
                            iceLocator = "RainIceGrid/Locator:tcp -h 127.0.0.1 -p 12000";
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println(sdf.format(new Date()) + "\tIce client's locator is " + iceLocator
                            + " proxy cache time out seconds :" + idleTimeOutSeconds);
                    log.info(sdf.format(new Date()) + "\tIce client's locator is " + iceLocator
                            + " proxy cache time out seconds :" + idleTimeOutSeconds);
                    ;
                    String[] initParams = new String[]{locatorKey + "=" + iceLocator};

                    ic = com.zeroc.Ice.Util.initialize(initParams);
                    createMonitorThread();
                }
            }
        }
        lastAccessTimestamp = System.currentTimeMillis();
        return ic;
    }

    private static void createMonitorThread() {
        nonitorThread = new MonitorThread();
        nonitorThread.setDaemon(true);
        nonitorThread.start();
    }

    public static void closeCommunicator(boolean removeServiceCache) {
        synchronized (IceClientUtils.class) {
            if (ic != null) {
                safeShutdown();
                nonitorThread.interrupt();
                if (removeServiceCache && !cls2PrxMap.isEmpty()) {
                    try {
                        cls2PrxMap.clear();
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        }

    }

    private static void safeShutdown() {
        try {
            ic.shutdown();
        } catch (Exception e) {
            log.error("{}", e);
            //e.printStackTrace();
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
    @SuppressWarnings("rawtypes")
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
    }

    public static ObjectPrx getSerivcePrx(Class<?> serviceCls) {
        return getSerivcePrx(serviceCls, null);
    }

    /**
     * 用于客户端API获取ICE服务实例的场景
     *
     * @param serviceCls
     * @return ObjectPrx
     */
    @SuppressWarnings("rawtypes")
    public static ObjectPrx getSerivcePrx(Class serviceCls, String Version) {
        ObjectPrx proxy = cls2PrxMap.get(serviceCls);
        if (proxy != null) {
            lastAccessTimestamp = System.currentTimeMillis();
            return proxy;
        }
        proxy = createIceProxy(getICECommunictor(), serviceCls, Version);
        cls2PrxMap.put(serviceCls, proxy);
        lastAccessTimestamp = System.currentTimeMillis();
        return proxy;
    }

    public static String doService(IceRequest iceRequest) {
        return doService(iceRequest, "MessageService");
    }

    public static String doService(IceRequest iceRequest, String servcieName) {
        try {
            Communicator ic = getICECommunictor();
            ObjectPrx base = ic.stringToProxy(servcieName);
            _MessageServicePrxI proxy = new _MessageServicePrxI();
            Method method = proxy.getClass().getDeclaredMethod("checkedCast", ObjectPrx.class, String.class);
            MessageServicePrx messagePre = (MessageServicePrx) method.invoke(proxy, base, null);
            MsgRequest in = new MsgRequest(iceRequest.getService(), iceRequest.getMethod(),
                    iceRequest.getExtData(), iceRequest.getAttr());
            String out = messagePre.doInvoke(in);
            return out;
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
                    //e.printStackTrace();
                }

            }
        }
    }

    /*
     * private void setIceLocator(String iceLocator) { ICEClientUtil.iceLocator
     * = iceLocator; }
     *
     * @Value("${Ice.idleTimeOutSeconds}") private void
     * setIdleTimeOutSeconds(long idleTimeOutSeconds) {
     * ICEClientUtil.idleTimeOutSeconds = idleTimeOutSeconds;
     *
     * }
     */
    public static void main(String[] args) {
        Properties icegrid = getProperties("icegrid.properties");
        if (icegrid != null) {
            String iceLocator = icegrid.getProperty("iceLocator");
            System.out.println(iceLocator);
        }
    }
}
