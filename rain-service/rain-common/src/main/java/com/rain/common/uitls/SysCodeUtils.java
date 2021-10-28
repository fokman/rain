package com.rain.common.uitls;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SysCodeUtils {
    private Properties props;
    private String filePath;
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

    public String getProperty(String property, String def) {
        String retVal;
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

    public String getProperty(String key) {
        return getProperty(key, "");
    }

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

    protected void loadProperties(String file) throws Exception {
        props = loadPropertiesFile(file);
    }

    protected Properties loadPropertiesFile(String file){
        Properties retVal = null;
        InputStream in = null;
        try {
            retVal = new Properties();
            in = this.getClass().getClassLoader().getResourceAsStream(file);
            if (in != null)
                retVal.load(in);
            assert in != null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return retVal;
    }
}
