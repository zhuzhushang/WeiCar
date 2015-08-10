package com.newbrain.xutils;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;

public class Xutils_HttpUtils 
{
	
	private static HttpUtils httpUtils;

	/**
	 * 
	 */
	private  Xutils_HttpUtils()
	{
		// TODO Auto-generated constructor stub
	}
	
	public  static HttpUtils getHttpUtils(Context context)
	{
		// TODO Auto-generated constructor stub
		
		if(httpUtils == null)
		{
			
			httpUtils = new HttpUtils();
			httpUtils.configCurrentHttpCacheExpiry(1000*10);
			httpUtils.configTimeout(1000*10);
			httpUtils.configResponseTextCharset("utf-8");
			
			
		}
		
		return httpUtils;
		
	}
	

}
