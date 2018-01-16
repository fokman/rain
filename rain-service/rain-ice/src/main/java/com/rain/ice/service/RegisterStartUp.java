package com.rain.ice.service;

import com.rain.ice.service.config.Service;
import com.rain.ice.service.config.Startup;
import com.rain.ice.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Startup
public class RegisterStartUp implements StartupService{

	private static final Logger logger = LoggerFactory.getLogger(RegisterStartUp.class);
	@Override
	public void startup(Properties ctontext) {
		try {
			List<String> pkgList = new ArrayList<>();
			String pkgs = (String) ctontext.get("scan");
			if (pkgs != null) {
				pkgs = pkgs.trim();
				String[] strs = pkgs.split(";");
				for (String string : strs) {
					if (string != null && string.trim().length() > 0) {
						pkgList.add(string.trim());
					}
				}
			}
			logger.info("register-service-start");
			for (String string : pkgList) {
				Set<Class<?>> list = ClassUtils.getClasses(string);
				for (Class<?> class1 : list) {
					Service[] services = class1.getDeclaredAnnotationsByType(Service.class);
					for (Service service : services) {
						String key = service.name();
						logger.info("register-service:name-{} class-{}", key, class1.getName());
						IceServiceRegister.getInstance().putIceService(key, class1.newInstance());
					}
				}
			}
			logger.info("startup-service-end");
		} catch (Exception e) {
			logger.error("start-init-error", e);
		}
	}

}
