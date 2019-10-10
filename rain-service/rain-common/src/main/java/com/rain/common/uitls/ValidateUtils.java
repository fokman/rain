package com.rain.common.uitls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lang工具类
 *
 */
public class ValidateUtils {

	public static boolean isIp(String ip) {
		String ipPattren = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
		Pattern pattern = Pattern.compile(ipPattren);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

}
