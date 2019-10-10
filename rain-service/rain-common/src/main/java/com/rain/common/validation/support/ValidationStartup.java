package com.rain.common.validation.support;

import java.util.Properties;

import com.rain.common.servcie.StartupService;
import com.rain.common.servcie.config.Startup;

@Startup
public class ValidationStartup implements StartupService {

	public void startup(Properties properties) {
		try {
			ValidationSupport.init();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
