package com.rain.common.uitls;

public class UrlUtils {
	public static String joinUrl(String url1, String url2) {
		url1 = StringUtils.trimNull(url1);
		if (!url1.endsWith("/")) {
			url1 += "/";
		}
		String path = StringUtils.trimNull(url2);
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		return url1 + path;
	}
}
