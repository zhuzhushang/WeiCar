package com.newbrain.utils;

import com.lidroid.xutils.HttpUtils;

public class HttpUtil {

	private static HttpUtils httpUtils;

	public static HttpUtils instance() {
		if (null == httpUtils) {
			httpUtils = new HttpUtils();
			httpUtils.configResponseTextCharset("UTF-8");
			httpUtils.configCurrentHttpCacheExpiry(0);
			httpUtils.configSoTimeout(60 * 1000);
			httpUtils.configTimeout(60 * 1000);
		}
		return httpUtils;
	}
}
