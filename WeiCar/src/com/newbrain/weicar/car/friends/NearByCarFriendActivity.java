package com.newbrain.weicar.car.friends;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.adapter.MyBaseAdapter_Nearby;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.Model_NearbyCF;
import com.newbrain.model.Model_NearbyCF.Result;
import com.newbrain.utils.Constant;
import com.newbrain.weicar.R;

public class NearByCarFriendActivity extends BaseActivity{
	
	
	private Context context;
	
	@ViewInject(R.id.nearby_listview)
	private ListView mListview;
	
	
	private List<Result> mList;

	private MyBaseAdapter_Nearby mMyAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.nearby_carfriend_activity);
		
		context = this;
		ViewUtils.inject(this);
		
		actionbarInit();
		
		dataInit();
		
	}
	
	
	private LocationClient mLocationlient;
	
	private MyBDLocation myBDLocation;
	
	private void dataInit() {
		// TODO Auto-generated method stub
		
		
		mLocationlient = new LocationClient(context);
		
		mLocationlient.setLocOption(getlocationOption());
		
		myBDLocation = new MyBDLocation();
		
		mLocationlient.registerLocationListener(myBDLocation);
		
		mLocationlient.start();
		
		mList = new ArrayList<Result>();
		
		mMyAdapter = new MyBaseAdapter_Nearby(context, mList);
		
		mListview.setAdapter(mMyAdapter);
		
		
		CustomProgressDialog.startProgressDialog(context);
		
	}

	
	private LocationClientOption  getlocationOption()
	{
		
		
		LocationClientOption option = new LocationClientOption();
		option.setIsNeedAddress(false);
		option.setScanSpan(1000*1);
		option.setAddrType("bd09ll");
		
		
		return option;
	}
	
	
	private double latitude,longtitude;
	
	
	public class MyBDLocation implements BDLocationListener
	{
		
		@Override
		public void onReceiveLocation(BDLocation arg0) {
			// TODO Auto-generated method stub
			
			
			mLocationlient.stop();
			
			latitude = arg0.getLatitude();
			
			longtitude = arg0.getLongitude();
			
			requestHttp_Nearby();
			
			
		}
		
		
	}
	
	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		
		
		mLocationlient.unRegisterLocationListener(myBDLocation);
		
		
	}






	private void requestHttp_Nearby()
	{
		
		List<Bean> list = new ArrayList<Bean>();
		
		list.add(new Bean("muLongitud", ""+longtitude));
		list.add(new Bean("muLatitude", ""+latitude));
		
		JsonThread jsonThread = new JsonThread(context, list, mHandler, Constant.FLAG_NEARBY_CF);
		
		jsonThread.start();
		
		
		
		
	}
	
	
	
	private Handler mHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch (msg.what) {
			case Constant.FLAG_NEARBY_CF:
				
				
				Model_NearbyCF model_NearbyCF = (Model_NearbyCF) msg.obj;
				
				mList = model_NearbyCF.getResult();
				mMyAdapter.setList(model_NearbyCF.getResult());
				
				
				
				break;

			default:
				break;
			}
			
			
		}
		
		
	};




	/**定义actionbar相关参数*/
	
	
	@ViewInject(R.id.all_actionbar_contain_relative)
	private RelativeLayout actionbar_rl_contain;
	
	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name;

	@ViewInject(R.id.all_actionbar_button_right)
	private Button actionbar_btn_right;

	/**初始化actionbar*/
	private void actionbarInit() {
		actionbar_tv_back_name.setText(getString(R.string.back));
		actionbar_tv_name.setText(getString(R.string.comfirm1));
		actionbar_btn_right.setText(getString(R.string.comfirm1));
		
		
//		actionbar_rl_contain.setBackgroundResource(R.color.theme_actionbar_background_color_blue);
		

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_btn_right.setVisibility(View.GONE);

		// actionbar_ll_left.setOnClickListener(l);

	}

	/**设置actionbar的监听*/
	@OnClick({ R.id.all_actionbar_linear_left, R.id.all_actionbar_button_right })
	private void onClick_actionBar(View view) {

		switch (view.getId()) {
		// 左边的返回
		case R.id.all_actionbar_linear_left:
			
			finish();

			break;
		// 右边的button
		case R.id.all_actionbar_button_right:
			
			

			break;

		default:
			break;
		}

	}
	
	
}
