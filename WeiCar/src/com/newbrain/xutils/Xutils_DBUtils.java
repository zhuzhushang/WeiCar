package com.newbrain.xutils;

import android.content.Context;

import com.lidroid.xutils.DbUtils;

public class Xutils_DBUtils {

	private static DbUtils dbUtils;

	private Xutils_DBUtils() {

	}

	public static DbUtils getDBUtils(Context context) {

		if (dbUtils == null) {

			dbUtils = DbUtils.create(context,"anerfa.db");

		}
		
		
		return dbUtils;

	}

}
