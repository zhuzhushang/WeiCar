package com.newbrain.utils;

/**
 * 防止控件被重复点击
 * 
 * @author Administrator
 * 
 */
public class CommonUtils {
	private static long lastClickTime;
	
	public static boolean isFastDoubleClick(int times) {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < times) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
}
