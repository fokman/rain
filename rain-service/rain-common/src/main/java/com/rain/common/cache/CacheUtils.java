package com.rain.common.cache;

import com.rain.common.cache.annotation.Cache;
import com.rain.common.ice.v1.model.IceRequest;

/**
* 缓存Key生成规则
* 源文件名：CacheUtils.java
* 文件版本：1.0.0
* 创建作者：冰风影
* 创建日期：2016年5月20日
* 修改作者：冰风影
* 修改日期：2016年5月20日
* 文件描述：
* 加入缓存
* @Cache(prefix="DICT",params={"dictId","schoolId"})
* 删除缓存
* @Cache(prefix="DICT",params={"dictId","schoolId"},type=CacheTypeEnum.DEL)
* Key生成规则：前缀prefix和多个参数params组成缓存的KEY
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/

public class CacheUtils {
	private static final String PREFIX_LINKER = "$";
	private static final String NAME_LINKER = ".";
	public static String getKey(Cache cache, IceRequest request){
		StringBuilder sb = new StringBuilder();
		sb.append(cache.prefix());
		sb.append(PREFIX_LINKER);
		for (int i = 0; i < cache.params().length; i++) {
			String pattern = cache.params()[i];
			String param=request.getAttr(pattern);
			if (param !=null && param !="") {
			  sb.append(param.trim());
			  sb.append(NAME_LINKER);
			}
		}
		return sb.toString();
	}
}
