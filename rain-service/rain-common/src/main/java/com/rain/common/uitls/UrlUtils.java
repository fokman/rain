package com.rain.common.uitls;

public class UrlUtils {
    public static String joinUrl(String url1, String url2) {
        if (!url1.endsWith("/")) {
            url1 += "/";
        }
        if (url2.startsWith("/")) {
            url2 = url2.substring(1);
        }
        return url1 + url2;
    }
}
