package com.rain.common.uitls;

import java.io.InputStream;
import java.util.Properties;

/**
 * 
* 源文件名：SysCodeUtils.java
* 文件版本：1.0.0
* 创建作者：he.kui
* 创建日期：2016年7月7日
* 修改作者：he.kui
* 修改日期：2016年7月7日
* 文件描述：系统错误代码处理工具类
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
 */
public class SysCodeUtils {
    private Properties props;
    private String filePath = "";
    private static SysCodeUtils instance = null;

    private SysCodeUtils() {
        filePath = "syscode.properties";
        getProperties();
    }

    public static SysCodeUtils getInstance() {
        if (instance == null) {
            instance = new SysCodeUtils();
        }
        return instance;
    }

    // protected SysCodeUtils(String filePath) {
    // this.filePath = filePath;
    // getProperties();
    // }

    /**
     * This method loads the properties object and returns it.
     * 
     * @return Properties the loaded property object, else null
     */
    private Properties getProperties() {
        if (props == null) {
            try {
                loadProperties(this.filePath);
            } catch (Exception exc) {
                System.err.println(exc.getMessage());
                props = null;
            }
        }
        return props;
    }

    /**
     * 根据属性获取值,如果为空,可以设置默认值
    * 方法描述
    * @param property key
    * @param def  为空时默认值
    * @return
    * @创建日期 2016年7月7日
     */
    public String getProperty(String property, String def) {
        String retVal = null;
        getProperties();
        if (props != null)
            retVal = props.getProperty(property, def).trim();
        else
            retVal = def;
        return retVal;
    }

    public String getProperty(int keycode, String def) {
        return getProperty(String.valueOf(keycode), def);
    }

    /***
     * getProperty:根据给定的KEY获取配置信息,没有默认返回空字符串 <br/>
     * 
     * @author he.kui
     * @param key
     * @return
     * @since JDK 1.7
     */
    public String getProperty(String key) {
        return getProperty(key, "");
    }

    /**
     * 根据配置文件读取int值,可设置默认值
    * 方法描述
    * @param key
    * @param defaultVal
    * @return
    * @创建日期 2016年7月7日
     */
    public int getInt(String key, int defaultVal) {
        String val = getProperty(key, defaultVal + "");
        if (StringUtils.isEmpty(val)) {
            return defaultVal;
        }
        return Integer.parseInt(val);
    }

    public void setProperty(String strKey, String strValue) {
        if (props != null)
            props.setProperty(strKey, strValue);
    }

    /**
     * This method loads the Properties object from the Map Properties file
     * 
     * @param String
     *            file the configuration file
     */
    protected void loadProperties(String file) throws Exception {
        props = loadPropertiesFile(file);
    }

    /**
     * This method loads a Properties object and returns it
     * 
     * @param String
     *            file the configuration file to load
     */
    protected Properties loadPropertiesFile(String file) throws Exception {
        Properties retVal = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
        if (in != null)
            retVal.load(in);
        in.close();
        return retVal;
    }
}
