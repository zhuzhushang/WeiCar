package com.newbrain.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {

	// 吐息
	public static void Toast(Context activity, String context) {
		Toast.makeText(activity, context, Toast.LENGTH_SHORT).show();
	}

	public static void Toast_Center(Context con, String context) {
		
		
		
		
		Toast toast = Toast.makeText(con, context, Toast.LENGTH_SHORT);
		
		toast.setGravity(Gravity.CENTER, 0, 0);
		
		toast.show();
		
	}
	
}
