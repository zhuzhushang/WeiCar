package com.newbrain.weicar.location.service;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.EventLogTags.Description;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.utils.DistanceUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.model.Model_Navigation;
import com.newbrain.utils.Util;
import com.newbrain.weicar.R;
import com.newbrain.weicar.location.service.MapFragment.MyLocationListener;

public class NavigationActivity extends BaseActivity {

	

	private Context context;

	@ViewInject(R.id.navigation_mapview)
	private MapView mMapView;

	private BaiduMap mBaiduMap;

	private GeoCoder mGeoCoder;

	private LocationClient mLocationClient;

	private MyLocationListener mMyLocationListener;

	@ViewInject(R.id.navigation_address)
	private TextView mTvAddress;

	@ViewInject(R.id.navigation_distance)
	private TextView mTvdistance;

	@ViewInject(R.id.navigation_destination)
	private AutoCompleteTextView mACTVDestination;
	
	private ArrayAdapter<String> mArrayAdapter;	
	private PoiSearch mPoiSearch;
	
	private SuggestionSearch mSuggestionSearch;
	
	private LatLng mMyLatLng;

	@ViewInject(R.id.navigation_city)
	private TextView mTvCity ;
	
	@ViewInject(R.id.navigation_to)
	private Button btn_nacition_to;
	
	private Marker mMarkerOnClick;
	
	
	//Destination : 目的地
	private Model_Navigation mModelNavigationDestination;
	
	private Model_Navigation mModelNavigationMyLocation;
	

	
	private static final LatLng GEO_BEIJING = new LatLng(39.945, 116.404);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.navigation_activity);

		context = this;
		ViewUtils.inject(this);

		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		
		mModelNavigationDestination = new Model_Navigation();
		mModelNavigationMyLocation = new Model_Navigation();
		
		
		mBaiduMap = mMapView.getMap();

		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mBaiduMap.setTrafficEnabled(true);
		
		mBaiduMap.setOnMapClickListener(onMapClickListener);

		mBitmapDescriptorMyLocation = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_track_mark);
		
		mBitmapDescriptorSearchLocation = BitmapDescriptorFactory.fromResource(R.drawable.my_location_arrows);

		mGeoCoder = GeoCoder.newInstance();

		mGeoCoder.setOnGetGeoCodeResultListener(mOnCoderResultListener);
		
		
		mMyLatLng = GEO_BEIJING;
		
		
		locationInit();
		searchInit();
		actvInit();
		
		overlayInit();
	}
	
	
	private void overlayInit() {
		// TODO Auto-generated method stub
		
		markerOptions = new MarkerOptions();
		
		
		bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_track_mark);
		markerOptions.icon(bd);
		markerOptions.position(mMyLatLng);
		markerOptions.visible(true);
		
		mMarkerOnClick =  (Marker)( mBaiduMap.addOverlay(markerOptions));
		
		
	
	}
	
	
	@OnClick({R.id.navigation_to})
	private void onClick_navigation(View view)
	{
		
		switch (view.getId()) {
		case R.id.navigation_to:
			
			
			
			
			
			
			break;

		default:
			break;
		}
		
		
		
	}
	
	

	private double mDistance;
	
	/**应对回到桌面再从桌面回来   消失的mark*/
	private boolean isAddOverlay;

	private OnMapClickListener onMapClickListener = new OnMapClickListener() {
		
		@Override
		public boolean onMapPoiClick(MapPoi arg0) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onMapClick(LatLng latlng) {
			// TODO Auto-generated method stub
			
			mBaiduMap.clear();
			
			
			
			
			addOverlayClick(latlng);
			
			
		}

		
	};
	
	private void addOverlayClick(LatLng latlng) {
		// TODO Auto-generated method stub
	
		
		
		addOverlay(latlng,bd);
		
		
		mDistance = DistanceUtil.getDistance( latlng, mMyLatLng);
		
		ReverseGeoCodeOption codeOption = new ReverseGeoCodeOption();
		codeOption.location( latlng);
		
		
		mGeoCoder.reverseGeoCode(codeOption);
		
		showShortToast("点击");
		
		
	}
	
	

	private void actvInit() {
		// TODO Auto-generated method stub
		
		mArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line);
		
		
		
		mACTVDestination.setAdapter(mArrayAdapter);
		mACTVDestination.setDropDownHeight(Util.dip2px(context, 250));
//		mACTVDestination.setThreshold(2);
	
		mACTVDestination.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
				if(s.length() <0)
				{
					return;
				}
				
				
				PoiCitySearchOption pcso = new PoiCitySearchOption();
				pcso.city(mCity);
				pcso.keyword(s.toString());
				mPoiSearch.searchInCity(pcso);
				
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
				
				
				
				
			}
		});
		
	}

	private void searchInit() {
		// TODO Auto-generated method stub
		
		
		
		
		
		
		mPoiSearch = PoiSearch.newInstance();
		mSuggestionSearch = SuggestionSearch.newInstance();
		
		mPoiSearch.setOnGetPoiSearchResultListener(mOnGetPoiSearchResultListener);
		mSuggestionSearch.setOnGetSuggestionResultListener(mOnGetSuggestionResultListener);
		
		
		
	}
	
	
	private OnGetPoiSearchResultListener mOnGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
		
		@Override
		public void onGetPoiResult(PoiResult result) {
			// TODO Auto-generated method stub
			
			if(result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)
			{
				
				showLongToast(R.string.sorry_no_result);
				
				return;
			}
			
			if(result.error == SearchResult.ERRORNO.NO_ERROR)
			{
				mBaiduMap.clear();
				PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
				mBaiduMap.setOnMarkerClickListener(overlay);
				overlay.setData(result);
				overlay.addToMap();
				overlay.zoomToSpan();
				
				return;
				
			}
			
			if(result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD)
			{
				
				showShortToast("在别的城市发现结果，请切换城市！");
			}
			
			
			
		}
		
		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {
			// TODO Auto-generated method stub
			
			if(result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)
			{
				
				showLongToast(R.string.sorry_no_result);
				
				
			}else
			{
				
				mTvAddress.setText(result.getName());
				showLongToast(result.getName()+":"+result.getAddress());
				
			}
			
			
		}
	};
	
	
	
	@OnClick({R.id.navigation_search})
	private void onClick_search(View view)
	{
		
		switch (view.getId()) {
		case R.id.navigation_search:
			
			String text = mACTVDestination.getText().toString();
			
			if(!text.isEmpty())
			{
				PoiCitySearchOption option = new PoiCitySearchOption();
				
				option.city(mCity).keyword(text);
				
				mPoiSearch.searchInCity(option);
				
				
			}else
			{
				
				showLongToast(R.string.sorry_no_result);
			}
			
			break;

		default:
			break;
		}
		
		
		
		
	}
	
	
	
	private OnGetSuggestionResultListener mOnGetSuggestionResultListener = new OnGetSuggestionResultListener() {
		
		@Override
		public void onGetSuggestionResult(SuggestionResult result) {
			// TODO Auto-generated method stub
			
			
			mArrayAdapter.clear();
			
			for (SuggestionResult.SuggestionInfo info : result.getAllSuggestions()) {
				
				if(info.key != null)
				{
					
					
					
					mArrayAdapter.add(info.key);
					
				}
				
			}
			
			mArrayAdapter.notifyDataSetChanged();
			mACTVDestination.showDropDown();
			
			
		}
	};
	
	
	
	

	private OnGetGeoCoderResultListener mOnCoderResultListener = new OnGetGeoCoderResultListener() {

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
			// TODO Auto-generated method stub
			
			if(arg0 !=null && arg0.error == SearchResult.ERRORNO.NO_ERROR)
			{
				
				
				mTvAddress.setText(" "+arg0.getAddress());
				mTvdistance.setText(""+mDistance+"km");
			}
			
			
		}

		@Override
		public void onGetGeoCodeResult(GeoCodeResult arg0) {
			// TODO Auto-generated method stub
			
			if(arg0 != null&&arg0.error == SearchResult.ERRORNO.NO_ERROR)
			{
				
//				mBaiduMap.addOverlay(arg0);
				
			}
			
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

	private BitmapDescriptor mBitmapDescriptorMyLocation;
	private BitmapDescriptor mBitmapDescriptorSearchLocation;

	private String mCity;

	private String mAddress;

	private BitmapDescriptor bd;

	private MarkerOptions markerOptions;

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub

			// mBaiduMap.clear();

			// 车的位置

			LatLng latLng_person = new LatLng(arg0.getLatitude(),
					arg0.getLongitude());

			

			OverlayOptions options = new MarkerOptions()
					.position(latLng_person).icon(mBitmapDescriptorSearchLocation);
			
			
			mCity = arg0.getCity();
			
			mBaiduMap.addOverlay(options);
			
			toLocation(latLng_person, mBaiduMap.getMaxZoomLevel()-1);
			
			mLocationClient.stop();

			

		}

	}
	
	
	/**添加overlay 
	 * @param latlng */
	private void addOverlay(LatLng latlng,BitmapDescriptor bitmapDescriptor)
	{
		
		MarkerOptions overlayOptions = new MarkerOptions();
		overlayOptions.position(latlng).icon(bitmapDescriptor).zIndex((int) (mBaiduMap.getMaxZoomLevel()-1));
		
		
		
		mBaiduMap.addOverlay(overlayOptions);
		
	}
	
	/** 跳转到指定位置 */
	private void toLocation(LatLng latLng, float zoom) {

		MapStatus mapStatus = new MapStatus.Builder().target(latLng).zoom(17)
				.build();

		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mapStatus);

		mBaiduMap.animateMapStatus(mapStatusUpdate);

	
	}
	
	
	public class MyPoiOverlay extends PoiOverlay
	{

		public MyPoiOverlay(BaiduMap arg0) {
			super(arg0);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public boolean onPoiClick(int arg0) {
			// TODO Auto-generated method stub
			super.onPoiClick(arg0);
			PoiInfo poiInfo = getPoiResult().getAllPoi().get(arg0);
			
			mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));
						
			
			return true;
		}
		
		
		
	}
	
	
	
	
	@Override
	public void onResume() {
		
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
		
		super.onResume();
	}

	@Override
	public void onPause() {
		
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
		isAddOverlay = true;
		super.onPause();
	}

	
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		
		bd.recycle();
		mLocationClient.unRegisterLocationListener(mMyLocationListener);
		mBitmapDescriptorMyLocation.recycle();
		
		mGeoCoder.destroy();
		mSuggestionSearch.destroy();
		mPoiSearch.destroy();

	}

}
