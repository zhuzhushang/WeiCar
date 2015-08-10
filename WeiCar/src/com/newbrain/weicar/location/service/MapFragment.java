package com.newbrain.weicar.location.service;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.weicar.R;


public class MapFragment extends Fragment{

	private Context context;

	private LocationClient mLocationClient;

	private MyLocationListener mMyLocationListener;

	@ViewInject(R.id.location_service_bmapView1)
	private MapView mMapView;

	private BaiduMap mBaiduMap;
	
	private GeoCoder mGeocode;

	@ViewInject(R.id.location_service_car_friends)
	private Button mBtnCarFriend;

	@ViewInject(R.id.location_service_record)
	private Button mBtnRecord;

	@ViewInject(R.id.location_service_myself_location)
	private Button mBtnMyselfAndCarLocation;
	
	
	@ViewInject(R.id.location_service_address)
	private TextView mTvAddress;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		View view = inflater.inflate(R.layout.location_service_map_fragment, container,false);
		
		
		context = getActivity();
		ViewUtils.inject(this,view);
		
//		mMapView = (MapView) view.findViewById(R.id.location_service_bmapView);
		
		mapViewInit();
		locationInit();
		
		
		return view;
	}
	
	

	private void mapViewInit() {
		// TODO Auto-generated method stub

		mBaiduMap = mMapView.getMap();

		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mBaiduMap.setTrafficEnabled(true);
		
		mGeocode = GeoCoder.newInstance();
		
		mGeocode.setOnGetGeoCodeResultListener(mOnGetGeoCoderResultListener);
		
		
	}
	
	private OnGetGeoCoderResultListener mOnGetGeoCoderResultListener = new OnGetGeoCoderResultListener() {
		
		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
			// TODO Auto-generated method stub
			
			if(isOpenReverseGeo&&arg0!=null)
			{
				
				mTvAddress.setText("位置："+arg0.getAddress());
				
			}
			
		}
		
		@Override
		public void onGetGeoCodeResult(GeoCodeResult arg0) {
			// TODO Auto-generated method stub
			
			
//			arg0.getAddress();
			
			
		}
	};

	private void locationInit() {
		// TODO Auto-generated method stub

		mLocationClient = new LocationClient(context);

		mMyLocationListener = new MyLocationListener();

		mLocationClient.registerLocationListener(mMyLocationListener);

		LocationClientOption option = new LocationClientOption();

		option.setIsNeedAddress(true);
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");
		option.setScanSpan(1000 * 1);
		option.setNeedDeviceDirect(true);

		mLocationClient.setLocOption(option);

		mLocationClient.start();
	}

	/** 当前地图显示的位子 是人还是车 */
	private boolean isPeople = true;

	/**反地理编码   根据经纬度获取地址*/
	private boolean isOpenReverseGeo;
	
	private BitmapDescriptor descriptor_person;
	private BitmapDescriptor descriptor_car;
	
	
	public class MyLocationListener implements BDLocationListener {

		

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub

//			mBaiduMap.clear();
			
			// 车的位置

			LatLng latLng_person = new LatLng(arg0.getLatitude(),
					arg0.getLongitude());

			descriptor_person = BitmapDescriptorFactory
					.fromResource(R.drawable.my_location_arrows);

			OverlayOptions options = new MarkerOptions()
					.position(latLng_person).icon(descriptor_person);

			mBaiduMap.addOverlay(options);

			// 车的位置

			LatLng latLng_car = new LatLng(39.963175, 116.400244);  


			descriptor_car = BitmapDescriptorFactory
					.fromResource(R.drawable.car);

			OverlayOptions options2 = new MarkerOptions().icon(descriptor_car)
					.position(latLng_car);

			mBaiduMap.addOverlay(options2);

			// 移动到车或者人的位置
			if (isPeople) {

				toLocation(latLng_person, mBaiduMap.getMaxZoomLevel());
				
				mTvAddress.setText("位置："+arg0.getAddrStr());
				mBtnMyselfAndCarLocation.setBackgroundResource(R.drawable.myself_location);
				
				mLocationClient.stop();
				

			} else {

				toLocation(latLng_car, mBaiduMap.getMaxZoomLevel());
				
				isOpenReverseGeo = true;
				mGeocode.reverseGeoCode(new ReverseGeoCodeOption().location(latLng_car));
				mBtnMyselfAndCarLocation.setBackgroundResource(R.drawable.my_car_location);
				mLocationClient.stop();
			}
			
			

		}

	}

	/** 跳转到指定位置 */
	private void toLocation(LatLng latLng, float zoom) {

		MapStatus mapStatus = new MapStatus.Builder().target(latLng).zoom(17)
				.build();

		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mapStatus);

		mBaiduMap.animateMapStatus(mapStatusUpdate);

//		mLocationClient.start();
		

	}

	@OnClick({ R.id.location_service_car_friends, R.id.location_service_record,
			R.id.location_service_myself_location })
	private void onClick_locationService(View v) {

		switch (v.getId()) {
		case R.id.location_service_car_friends:

			break;
		case R.id.location_service_record:

			break;

		case R.id.location_service_myself_location:
			
			isPeople = !isPeople;
			mLocationClient.start();

			break;

		default:
			break;
		}

	}



	@Override
	public void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		mLocationClient.unRegisterLocationListener(mMyLocationListener);
		descriptor_car.recycle();
		descriptor_person.recycle();

	}

	@Override
	public void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

}
