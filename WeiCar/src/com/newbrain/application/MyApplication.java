package com.newbrain.application;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class MyApplication extends Application
{

	
	public LocationClient mLocationClient;
	
	public BDLocation mBDLocation;
	
	public MyLocationListener mMyLocationListener;
	
	
	
	@Override
	public void onCreate()
	{
		// TODO Auto-generated method stub
		super.onCreate();
		
		SDKInitializer.initialize(getApplicationContext());
		
		mLocationClient = new LocationClient(getApplicationContext());
		
		mMyLocationListener = new MyLocationListener();
		
		mLocationClient.registerLocationListener(mMyLocationListener);
		
		mLocationClient.registerLocationListener(mMyLocationListener);
		
		
	} 
	
	public class MyLocationListener implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub
			
			mBDLocation = arg0;
			
		}
		
		
	}
	
	

}
