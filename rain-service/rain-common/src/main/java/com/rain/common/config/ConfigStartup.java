package com.rain.common.config;

import java.util.Properties;

import com.rain.common.servcie.StartupService;
import com.rain.common.uitls.ContextUtils;
import com.rain.common.uitls.IdWorker;
import com.rain.common.servcie.config.Startup;

@Startup
public class ConfigStartup implements StartupService {

	@Override
	public void startup(Properties properties) {
		ContextUtils.setIdWorker(IdWorker.getFlowIdWorkerInstance());
	}

}
