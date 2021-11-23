package com.rain.common.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME) 
public @interface Cache {
	//过期时间
    int seconds() default -1;
    //前缀
    String prefix();
    //参数
    String[] params() default {};
    //操作类型
    CacheTypeEnum type() default CacheTypeEnum.ADD;
}
