package com.newbrain.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络
 * 
 * @author Administrator
 * 
 */
public class NetworkDetector {

	/**
	 * 判断网络是否连接
	 * 
	 * @param act
	 * @return
	 */
	public static boolean detect(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			ToastUtil.Toast(context, "网络不可用，请检查一下您的网络");
			return false;
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info == null) {
				ToastUtil.Toast(context, "网络不可用，请检查一下您的网络");
				return false;
			} else {
				if (info.isAvailable()) {
					return true;
				}

			}
		}
		return false;
	}
}
