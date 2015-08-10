package com.newbrain.jsonthread;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.utils.Constant;
import com.newbrain.weicar.R;
import com.newbrain.xutils.Xutils_HttpUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @author Administrator
 *
 */


public class HttpJsonString {	
	/**
	 * @param context 上下文
	 * @param baseUrl 基础的url
	 * @param params  url 需要带的参数
	 * @param flag    判断是请求的是哪一个接口
	 * @param httpMode  请求的方式  是post get
	 */
	public static void httpGetJsonString(final Context context,RequestParams params,final Handler handler,String baseUrl,final int flag,HttpMethod httpMethod)
	{
		
		HttpUtils httpUtils = Xutils_HttpUtils.getHttpUtils(context);
		
		
		
		httpUtils.send(httpMethod, baseUrl, params, new RequestCallBack<String>() {
			
			
			/* (non-Javadoc)
			 * @see com.lidroid.xutils.http.callback.RequestCallBack#onFailure(com.lidroid.xutils.exception.HttpException, java.lang.String)
			 *
			 *请求失败
			 *
			 */
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub

				/**请求失败  可能错误的地方
				 * 主要体现在网址上面
				 * 再就是可能没网
				 * 下面列举几个易错的地方
				 * 1。参数错误  网址所要带的参数params写错了或者没写   
				 * 2.没有加baseurl
				 * 
				 * 
				 * 
				 * */
				
				CustomProgressDialog.stopProgressDialog();
				Message message = handler.obtainMessage(Constant.FLAG_HTTP_ERROR, context.getResources().getString(R.string.request_fail));
				
				handler.sendMessage(message);
			}
			
			
			
			/* (non-Javadoc)
			 * @see com.lidroid.xutils.http.callback.RequestCallBack#onSuccess(com.lidroid.xutils.http.ResponseInfo)
			 *
			 *请求成功
			 *
			 */
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				
				String su = arg0.result;
				
				LogUtils.e("---请求成功---"+su);
				
				
				Message message = handler.obtainMessage(flag, arg0.result);
				
				handler.sendMessage(message);
				
				
				
			}
		});
		
	}}
