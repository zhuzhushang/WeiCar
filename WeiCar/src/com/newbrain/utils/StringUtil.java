package com.newbrain.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;



public class StringUtil {

	// 得到剩余时间
	public static long[] getBetweenDay(long dateString) {
		long[] str1 = new long[4];
		long dif = dateString;
		long one_day = 60 * 60 * 24 * 1000;
		long one_hour = 60 * 60 * 1000;
		long one_minute = 60 * 1000;
		long day, hour, minute, second = 0L;
		day = dif / one_day;
		hour = dif % one_day / one_hour;
		minute = dif % one_day % one_hour / one_minute;
		second = dif % one_day % one_hour % one_minute / 1000;
		str1[0] = day;
		str1[1] = hour;
		str1[2] = minute;
		str1[3] = second;
		return str1;
	}

	// 分割时间
	public static long getSplic(String time) {
		
		int start = time.indexOf("(");
		
		int end = time.indexOf("+");
		
//		LogUtils.e("--->"+start+"--->"+end+"--->substring"+time.substring(start+1, end));
		
		return Long.parseLong(time.substring(start+1, end));
	}

	/**
	 * 时间戳转换成日期
	 * 
	 * @param beginDate
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDate(String time) {
		String beginDate = String.valueOf(getSplic(time));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sd = sdf.format(new Date(Long.parseLong(beginDate)));
		return sd;
	}

	/**
	 * 时间戳转换成日期
	 * 
	 * @param beginDate
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDateTime(String time) {
		String beginDate = String.valueOf(getSplic(time));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd = sdf.format(new Date(Long.parseLong(beginDate)));
		return sd;
	}
	
	
	

	// 设置商品评价等级
//	public static void setImageFuntion(ImageView image, int type) {
//		if (type <= 0) {
//			image.setImageResource(R.drawable.fundtion0);
//		} else if (type == 1) {
//			image.setImageResource(R.drawable.fundtion1);
//		} else if (type == 2) {
//			image.setImageResource(R.drawable.fundtion2);
//		} else if (type == 3) {
//			image.setImageResource(R.drawable.fundtion3);
//		} else if (type == 4) {
//			image.setImageResource(R.drawable.fundtion4);
//		} else {
//			image.setImageResource(R.drawable.fundtion5);
//		}
//	}

	// 把金额保留2为小数
	public static String getMoney(double money) {
		String moneyStr = "";
		if (0 != money) {
			DecimalFormat df = new DecimalFormat("###.00");
			moneyStr = df.format(money);
		} else {
			moneyStr = "0.00";
		}
		return moneyStr;
	}

	public static int a(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				* paramContext.getResources().getDisplayMetrics().density);
	}

	/*
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobile) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
}
