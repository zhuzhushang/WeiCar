package com.newbrain.xutils;

import android.content.Context;
import android.graphics.Bitmap;


import com.lidroid.xutils.BitmapUtils;

public class Xutils_BitmapUtils
{
	
	private static BitmapUtils bitmapUtils_small;
	private static BitmapUtils bitmapUtils_detail;

	/**
	 * 
	 */
	private Xutils_BitmapUtils()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public static BitmapUtils getBitmapUtils_small(Context context)
	{
		if(bitmapUtils_small == null)
		{
			
			bitmapUtils_small = new BitmapUtils(context);
			
//			bitmapUtils_small.configDefaultLoadFailedImage(R.drawable.home_tu_1);
//			bitmapUtils_small.configDefaultLoadingImage(R.drawable.home_tu_1);
			bitmapUtils_small.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
			
			bitmapUtils_small.configMemoryCacheEnabled(true);
			bitmapUtils_small.configDiskCacheEnabled(false);
			
		}
		
		
		return bitmapUtils_small;
	}
	
	
	
	public static BitmapUtils getbitmapUtils_detail(Context context)
	{
		if(bitmapUtils_detail == null)
		{
			
			bitmapUtils_detail = new BitmapUtils(context);
			
//			bitmapUtils_detail.configDefaultLoadFailedImage(R.drawable.home_viewflow_tu_4);
//			bitmapUtils_detail.configDefaultLoadingImage(R.drawable.home_viewflow_tu_4);
			bitmapUtils_detail.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
			
			bitmapUtils_detail.configMemoryCacheEnabled(true);
			bitmapUtils_detail.configDiskCacheEnabled(true);
			
		}
		
		
		return bitmapUtils_detail;
		
	}

}
