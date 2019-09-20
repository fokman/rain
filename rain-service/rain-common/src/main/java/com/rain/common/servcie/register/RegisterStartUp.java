package com.rain.common.servcie.register;

import com.rain.common.ice.impl.IceServiceRegister;
import com.rain.common.servcie.StartupService;
import com.rain.common.servcie.config.IceService;
import com.rain.common.servcie.config.Startup;
import com.rain.common.uitls.ClassUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Startup
@Slf4j
public class RegisterStartUp implements StartupService {

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
            log.info("register-service-start");
            for (String string : pkgList) {
                Set<Class<?>> classList = ClassUtils.getClasses(string);
                for (Class<?> classService : classList) {
                    IceService[] services = classService.getDeclaredAnnotationsByType(IceService.class);
                    for (IceService service : services) {
                        String key = service.name();
                        log.info("register-service:name-{} class-{}", key, classService.getName());
                        IceServiceRegister.getInstance().putIceService(key, classService.newInstance());
                    }
                }
            }
            log.info("startup-service-end");
        } catch (Exception e) {
            log.error("start-init-error : {}", e);
        }
    }

}
