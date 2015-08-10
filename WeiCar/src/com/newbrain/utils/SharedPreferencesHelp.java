package com.newbrain.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelp {

	/*
	 * �����ֻ����뱣��String
	 */
	public static void saveFindString(Context context, String key, String value) {
		try {
			String link = SharedPreferencesHelp.getString(context, "phone");
			SharedPreferences params = context.getSharedPreferences(link, 0);
			params.edit().putString(key, value).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * �����ֻ������ȡString
	 */
	public static String getFindString(Context context, String key) {
		String object = "";
		String link = SharedPreferencesHelp.getString(context, "phone");
		try {
			SharedPreferences params = context.getSharedPreferences(link, 0);
			object = params.getString(key, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/*
	 * ����String
	 */
	public static void saveString(Context context, String key, String value) {
		try {
			SharedPreferences params = context.getSharedPreferences(
					"linkGroup", 0);
			params.edit().putString(key, value).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ��ȡString
	 */
	public static String getString(Context context, String key) {
		String object = "";
		try {
			SharedPreferences params = context.getSharedPreferences(
					"linkGroup", 0);
			object = params.getString(key, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/*
	 * ����long
	 */
	public static void saveLong(Context context, String key, long value) {
		try {
			SharedPreferences params = context.getSharedPreferences(
					"linkGroup", 0);
			params.edit().putLong(key, value).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ��ȡlong
	 */
	public static long GetLong(Context context, String key) {
		long object = 0;
		try {
			SharedPreferences params = context.getSharedPreferences(
					"linkGroup", 0);
			object = params.getLong(key, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/*
	 * ��ȡBoolean
	 */
	public static boolean getBoolean(Context context, String key) {
		boolean object = false;
		try {
			SharedPreferences params = context.getSharedPreferences(
					"linkGroup", 0);
			object = params.getBoolean(key, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/*
	 * ����Boolean
	 */
	public static void saveBoolean(Context context, String key, boolean value) {
		try {
			SharedPreferences params = context.getSharedPreferences(
					"linkGroup", 0);
			params.edit().putBoolean(key, value).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void saveFloat(Context context, String key, float value) {
		try {
			SharedPreferences params = context.getSharedPreferences(
					"linkGroup", 0);
			params.edit().putFloat(key, value).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static float getFloat(Context context, String key) {
		try {
			SharedPreferences params = context.getSharedPreferences(
					"linkGroup", 0);
			
			return params.getFloat(key, 0.0f);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0f;
	}
	

	public static String GetPhone(Context context) {
		String object = null;
		try {
			SharedPreferences params = context.getSharedPreferences(
					"linkGroup", 0);
			object = params.getString("username", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	/*
	 * ����long
	 */
	public static void saveCarInt(Context context, String key, int value) {
		try {
			String phone = GetPhone(context);
			SharedPreferences params = context.getSharedPreferences(phone, 0);
			params.edit().putInt(key, value).commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ����long
	 */
	public static int GetCarInt(Context context, String key) {
		int object = 0;
		try {
			String phone = GetPhone(context);
			SharedPreferences params = context.getSharedPreferences(phone, 0);
			object = params.getInt(key, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

}
