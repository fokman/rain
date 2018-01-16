
package com.rain.ice.service.config;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Startup {
	int sort()  default 10;
}
