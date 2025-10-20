package com.marttapps.screenrotator.util;

import com.marttapps.screenrotator.service.WinKernel32Service;

public class PathUtil {

	private PathUtil() {
		throw new UnsupportedOperationException("Utility class should not be instantiated");
	}

	public static String getCurrentExecutablePath() {
		char[] buffer = new char[1024];
		int len = WinKernel32Service.INSTANCE.GetModuleFileNameW(null, buffer, buffer.length);
		if (len == 0) {
			return null;
		}
		return new String(buffer, 0, len);
	}
}
