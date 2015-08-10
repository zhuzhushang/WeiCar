package com.imageloader;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;

public class Configuration {
	
	
	public static DisplayImageOptions getOptionDefault()
	{
		
		Builder option = new DisplayImageOptions.Builder();
		
//		option.showImageOnFail(R)
		
		
		
		return option.build();
		
		
	}
	
	
	

}
