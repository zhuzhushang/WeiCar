package com.newbrain.jsonthread;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.model.Bean;
import com.newbrain.model.Model_GetAllCar;
import com.newbrain.model.Model_GetFriendList;
import com.newbrain.model.Model_IllegalQuery;
import com.newbrain.model.Model_IllegalQueryCityQuery;
import com.newbrain.model.Model_Login;
import com.newbrain.model.Model_NearbyCF;
import com.newbrain.model.Model_Weather;
import com.newbrain.utils.Constant;
import com.newbrain.utils.ToastUtil;
import com.newbrain.utils.Util;
import com.newbrain.weicar.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class JsonThread extends Thread {

	private Context mContext;

	private int mFlag;

	private List<Bean> mList;

	private Handler mHandler;

	public JsonThread(Context context, List<Bean> list, Handler mHandler,
			int flag) {
		super();
		this.mContext = context;
		this.mFlag = flag;
		this.mList = list;
		this.mHandler = mHandler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		switch (mFlag) {
		
		/**附近好友 Constant.FLAG_NEARBY_CF*/
		case Constant.FLAG_NEARBY_CF:

			requestPamars_get(mFlag, Constant.METHOD_NEARBY_CF);

			break;

		/** 获取天气情况 */
		case Constant.FLAG_WEATHER_JUHE:

			requestPamars_get(mFlag, Constant.METHOD_WEATHER_JUHE);

			break;

		case Constant.FLAG_GETPAC:

			requestPamars_get(mFlag, Constant.METHOD_GETPAC);

			// Flag_ILLEGALQUERY

			break;
		/** 获取违章查询数据 */
		case Constant.Flag_ILLEGALQUERY:

			requestPamars_get(mFlag, Constant.METHOD_ILLEGALQUERY);

			break;
		/** 登陆 */
		case Constant.FLAG_LOGIN:

			requestPamars_get(mFlag, Constant.METHOD_LOGIN);

			break;
		/** 注册 */
		case Constant.FLAG_REGISTER:

			requestPamars_post(mFlag, Constant.METHOD_REGISTER);

			break;
			
			/** 增加车辆 */
		case Constant.FLAG_ADD_CAR:

			requestPamars_post(mFlag, Constant.METHOD_ADD_CAR);

			break;
			
			/** 获取好友列表 */
		case Constant.FLAG_GET_FRIEND_LIST:

			requestPamars_get(mFlag, Constant.METHOD_GET_FRIEND_LIST);

			break;
			/** 获取所有的车辆*/
		case Constant.FLAG_GET_ALL_CAR:

			requestPamars_get(mFlag, Constant.METHOD_GET_ALL_CAR);

			break;
			
			

		}

	}

	public Handler handler_jsonthread = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			String obj = (String) msg.obj;

			LogUtils.e("----jsonthread里面的handler--->" + obj);

			switch (msg.what) {
			
			
			/**附近好友 Constant.FLAG_NEARBY_CF*/
			case Constant.FLAG_NEARBY_CF:

				JSONObject objectNearby = null;
				try {
					objectNearby = new JSONObject(obj);

					if (objectNearby.getString("code").equals("0")) {

						Model_NearbyCF model_login = JSON.parseObject(obj,
								Model_NearbyCF.class);

						mHandler.sendMessage(mHandler.obtainMessage(mFlag, model_login));
					} else {
						
						
						ToastUtil.Toast(mContext, objectNearby.getString("message"));
						
						CustomProgressDialog.stopProgressDialog();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			
			
			/**获取朋友列表FLAG_GET_FRIEND_LIST*/
			case Constant.FLAG_GET_FRIEND_LIST:

				JSONObject objectGetFriendList = null;
				try {
					objectGetFriendList = new JSONObject(obj);

					if (objectGetFriendList.getString("code").equals("0")) {

						Model_GetFriendList model_login = JSON.parseObject(obj,
								Model_GetFriendList.class);

						mHandler.sendMessage(mHandler.obtainMessage(mFlag, model_login));
					} else {
						
						
						ToastUtil.Toast(mContext, objectGetFriendList.getString("message"));
						
						CustomProgressDialog.stopProgressDialog();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				break;
				
				
				/** 获取所有的车辆*/
			case Constant.FLAG_GET_ALL_CAR:

				
				JSONObject FLAG_GET_ALL_CAR = null;
				try {
					FLAG_GET_ALL_CAR = new JSONObject(obj);

					if (FLAG_GET_ALL_CAR.getString("code").equals("1")) {

						Model_GetAllCar model_login = JSON.parseObject(obj,
								Model_GetAllCar.class);

						mHandler.sendMessage(mHandler.obtainMessage(mFlag, model_login));
					} else {
						
						
						ToastUtil.Toast(mContext, FLAG_GET_ALL_CAR.getString("message"));
						
						CustomProgressDialog.stopProgressDialog();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				

				break;
				
				
			/** 增加车辆 */
			case Constant.FLAG_ADD_CAR:

				JSONObject objectAddCar = null;
				try {
					objectAddCar = new JSONObject(obj);

					if (objectAddCar.getString("code").equals("0")) {

						Model_Login model_login = JSON.parseObject(obj,
								Model_Login.class);

						mHandler.sendMessage(mHandler.obtainMessage(mFlag, model_login));
					} else {
						
						
						ToastUtil.Toast(mContext, objectAddCar.getString("message"));
						
						CustomProgressDialog.stopProgressDialog();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				break;
			
			
			case Constant.FLAG_HTTP_ERROR:
				
				ToastUtil.Toast(mContext, mContext.getString(R.string.request_fail_please_try_again_later));
				
				break;

			/** 天气预报 */
			case Constant.FLAG_WEATHER_JUHE:

				JSONObject object;
				try {
					object = new JSONObject(obj);

					if (object.getString("resultcode").equals("200")) {

						Model_Weather weather = JSON.parseObject(obj,
								Model_Weather.class);

						mHandler.sendMessage(mHandler.obtainMessage(mFlag,
								weather));
					} else {
						
						
						ToastUtil.Toast(mContext, "获取天气失败");

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;

			case Constant.FLAG_GETPAC:

				Model_IllegalQueryCityQuery model = JSON.parseObject(obj,
						Model_IllegalQueryCityQuery.class);

				mHandler.sendMessage(mHandler.obtainMessage(mFlag, model));

				break;

			case Constant.Flag_ILLEGALQUERY:

				Model_IllegalQuery model_IllegalQuery = JSON.parseObject(obj,
						Model_IllegalQuery.class);

				mHandler.sendMessage(mHandler.obtainMessage(mFlag,
						model_IllegalQuery));

				break;

			case Constant.FLAG_LOGIN:
				
				JSONObject objectLogin = null;
				try {
					objectLogin = new JSONObject(obj);

					if (objectLogin.getString("code").equals("1")) {

						Model_Login model_login = JSON.parseObject(obj,
								Model_Login.class);

						mHandler.sendMessage(mHandler.obtainMessage(mFlag, model_login));
					} else {
						
						
						ToastUtil.Toast(mContext, objectLogin.getString("message"));
						
						CustomProgressDialog.stopProgressDialog();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				

				break;

			/** 注册 */
			case Constant.FLAG_REGISTER:

				
				JSONObject objectRegister = null;
				try {
					objectRegister = new JSONObject(obj);

					if (objectRegister.getString("code").equals("2")) {

						Model_Login model_register = JSON.parseObject(obj,
								Model_Login.class);

						mHandler.sendMessage(mHandler.obtainMessage(mFlag,
								model_register));
					} else {
						
						
						ToastUtil.Toast(mContext, objectRegister.getString("message"));
						
						CustomProgressDialog.stopProgressDialog();

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				

				break;
				
			

			}

		}

	};

	/**
	 * @param flag
	 *            接口标识
	 * @param method
	 * 
	 *            get专用
	 * 
	 */
	private void requestPamars_get(int mFlag, String baseUrl) {

		RequestParams params = new RequestParams();

		for (int i = 0; i < mList.size(); i++) {

			params.addQueryStringParameter(mList.get(i).getKey(), mList.get(i)
					.getValue());
		}

		HttpJsonString.httpGetJsonString(mContext, params, handler_jsonthread,
				baseUrl, mFlag, HttpMethod.GET);

	}

	private void requestPamars_post(int mFlag, String baseUrl) {

		RequestParams params = new RequestParams();

		for (int i = 0; i < mList.size(); i++) {

			params.addBodyParameter(mList.get(i).getKey(), mList.get(i)
					.getValue());

		}

		HttpJsonString.httpGetJsonString(mContext, params, handler_jsonthread,
				baseUrl, mFlag, HttpMethod.POST);

	}

}
