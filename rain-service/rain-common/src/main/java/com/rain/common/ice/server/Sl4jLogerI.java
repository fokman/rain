package com.rain.common.ice.server;

import com.zeroc.Ice.Logger;
import com.rain.common.uitls.DateUtils;
import org.slf4j.LoggerFactory;

public class Sl4jLogerI implements Logger {
    private final org.slf4j.Logger logger;
    private String prefix;

    public Sl4jLogerI(String loggerName) {
        this.prefix = loggerName;
        logger = LoggerFactory.getLogger(loggerName);
    }

    public void print(String message) {
        logger.info(getNowDate() + "\t" + message);

    }

    public void trace(String category, String message) {
        logger.debug(getNowDate() + "\t" + category + " " + message);
    }

    public void warning(String message) {
        logger.warn(getNowDate() + "\t" + message);
    }

    public void error(String message) {
        logger.error(getNowDate() + "\t" + message);
    }

    public Logger cloneWithPrefix(String prefix) {

        return new Sl4jLogerI(prefix);
    }

    public String getPrefix() {
        return prefix;
    }

    private String getNowDate() {
        return DateUtils.formatDateTime(DateUtils.getDate(), DateUtils.YYYY_MM_DD_12HH_MM_SS_SSS);
    }

}
