package com.rain.common.config;

import com.rain.common.servcie.StartupService;
import com.rain.common.servcie.config.Startup;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * @author kunliu
 */
@Startup
@Slf4j
public class ConfigStartup implements StartupService {

    @Override
    public void startup(Properties properties) {
        log.info("[ConfigStartup startup] properties:{}", properties);
    }

}
