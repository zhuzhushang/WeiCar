package com.example.rollupdemo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.newbrain.weicar.R;


public class Utils {
	private static final String TAG = "Utils";
	//------------------------------------------
	
	
	//------------------------------------------
	public static final String ENCORDING = "UTF-8";
	
	//-----------------url接口--------------------
	//注册
	public static final String REGISTERPATH = "/app/signup";
	//登录
	public static final String LOGINPATH = "/app/login";
	// 退出
	public static final String EXITPATH = "/app/logout";
	// 绑定设备接口
	public static final String BINDDOORPATH = "/app/devices";
	// 取消设备绑定接口
	public static final String REMOVEDOORPATH = "/app/remove";
	//--------------------------------------------
	
	public static List<HashMap<String, String>> deviceList = new ArrayList<HashMap<String,String>>(){};
	public static boolean isLogin= false;
	
	//--------------------------------------------
	public static final String ANYCHAT_DOMAIN = "ihomecn.rollupcn.com";
	public static final int ANYCHAT_PORT = 8906;
	//--------------------------------------------
	public static final String getDomain(){
		return "http://ihomecn.rollupcn.com";
	}

	public static String praseJson(String str) {
		String status = null;
		try {
			JSONObject json = new JSONObject(str);
			status = json.getString("status");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	public static String sendPostResquest(Context context, String path,
			Map<String, String> params, String encoding) {
		Log.i(TAG, getDomain()+path);
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		if ((params != null) && !params.isEmpty()) {
			for (Map.Entry<String, String> param : params.entrySet()) {
				list.add(new BasicNameValuePair(param.getKey(), param
						.getValue()));
			}
		}
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,
					encoding);
			HttpPost httpPost = new HttpPost(getDomain()+path);
			httpPost.setEntity(entity);
			DefaultHttpClient client = new DefaultHttpClient();
			/** 保持会话Session **/
			/** 设置Cookie **/
			MyHttpCookies li = new MyHttpCookies(context);
			CookieStore cs = li.getuCookie();
			/** 第一次请求App保存的Cookie为空，所以什么也不做，只有当APP的Cookie不为空的时。把请请求的Cooke放进去 **/
			if (cs != null) {
				((AbstractHttpClient) client).setCookieStore(li.getuCookie());
			}
			/** 保持会话Session end **/

			/** 发送请求并等待响应 */
			HttpResponse httpResponse = client.execute(httpPost);
			int reponseCode = httpResponse.getStatusLine().getStatusCode();
			Log.i(TAG, "code:" + reponseCode);
			if (reponseCode == HttpStatus.SC_OK) {
				Log.i(TAG, "OK");
				String resultData = EntityUtils.toString(httpResponse
						.getEntity());
				/** 执行成功之后得到 **/
				/** 成功之后把返回成功的Cookis保存APP中 **/
				// 请求成功之后，每次都设置Cookis。保证每次请求都是最新的Cookis
				li.setuCookie(((AbstractHttpClient) client).getCookieStore());

				/** 设置Cookie end **/
				return resultData;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static boolean IsHaveInternet(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	public static String getIMEI(Context context) {

		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}
	
	public static void deleteUnbindDevice(String name){
		boolean find = false;
		for (int i=0; i<deviceList.size() && find==false; i++){
			if (deviceList.get(i).get("device_name").contentEquals(name)){
				find = true;
				deviceList.remove(i);
			}
		}
	}
	
	public static void saveJsonToDB(Context context, String str) {
		deviceList.clear();
		try {
			JSONObject json = new JSONObject(str);
			JSONObject user = json.getJSONObject("user");

			ContentResolver contentResolver = context.getContentResolver();
			ContentValues values = new ContentValues();

			JSONArray devices = user.getJSONArray("user_devices");
			for (int i = 0; i < devices.length(); i++) {
				JSONObject device = (JSONObject) devices.get(i);
				int device_id = device.getInt("id");
				String device_name = device.getString("device_name");
				String device_name_other;
				String device_anychat_id="0";
				String device_version="";
				try {
					device_name_other = device.getString("alias");
					device_anychat_id = device.getString("device_anychat_id");					
					Log.i(TAG, "device anychat id:"+device_anychat_id);
				} catch (Exception e) {
					// TODO: handle exception
					device_name_other = context.getString(R.string.string_no_name);
				}
				try {
					device_version = device.getString("version");
				} catch (Exception e) {
					// TODO: handle exception
					device_version = null;
				}
				if (device_name_other.contentEquals("")){
					device_name_other = context.getString(R.string.string_no_name);
				}
				
				Log.i(TAG, "device_id:" + device_id + ",device_name:"
						+ device_name);
				if (!device_name.equals(getIMEI(context))) {
					values.put("device_id", device_id);
					values.put("device_name", device_name);
					values.put("device_name_other", device_name_other);
					values.put("device_version", device_version);
					Log.i(TAG, "device_id:" + device_id + ", device_name:"
							+ device_name);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("device_name", device_name);
					map.put("device_name_other", device_name_other);
					map.put("device_anychat_id", device_anychat_id);
					map.put("device_version", device_version);
					deviceList.add(map);
				}
			}
			Log.i(TAG, "devicelist-->" + deviceList.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.i(TAG, "save device info finish ");
	}
	
	public static boolean isBindDevice(String device_name){
		boolean res = false;
		for (HashMap<String, String> data:deviceList){
			if (data.get("device_name").contentEquals(device_name)){
				Log.i(TAG, "isBindDevice true");
				res = true;
				break;
			}
		}
		Log.i(TAG, "isBindDevice res="+res);
		return res;
	}
	
	public static String getSDCardPath() {
		File sdcardDir = null;
		// 判断SDCard是否存在
		boolean sdcardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		if (sdcardDir != null){
			return sdcardDir.toString() + "/";
		} else {
			return null;
		}
	}
}
