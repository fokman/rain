package com.rain.common.translate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME) 
public @interface Trans {
    //前缀
   // public String prefix();
	//获取其他服务的初始化数据
    //public String[] SrvMethod() default {};
    //参数，每个参数包括三部分 srcId(需要翻译ID),desId(目的表的对应ID),desName(目的表的翻译结果字段)
    String[] params() default {};
    //操作类型
    TransType type() default TransType.QUERY;
}
