package com.newbrain.utils;

import java.text.DecimalFormat;

public class DecimalFormatUtil {

	/**
	 * 保留2为小数
	 * 
	 * @param mumber
	 * @return
	 */
	public static String getDecimalFormat(float mumber) {
		DecimalFormat df = new DecimalFormat("######0.00");
		return df.format(mumber);
	}
}
