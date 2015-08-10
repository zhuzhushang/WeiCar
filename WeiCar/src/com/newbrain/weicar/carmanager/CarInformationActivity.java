package com.newbrain.weicar.carmanager;


import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.model.Model_GetAllCar.Result;
import com.newbrain.weicar.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CarInformationActivity extends BaseActivity{
	
	
	
	@ViewInject(R.id.addcar_ac_car_device_num)
	private EditText mEt_addcar_ac_car_device_num;

	@ViewInject(R.id.device_information_ac_car_num)
	private EditText mEt_device_information_ac_car_num;

	@ViewInject(R.id.device_information_ac_car_VIN)
	private EditText mEt_device_information_ac_car_VIN;

	@ViewInject(R.id.device_information_ac_car_sort)
	private EditText mEt_device_information_ac_car_sort;

	@ViewInject(R.id.device_information_ac_car_buy_time)
	private EditText mEt_device_information_ac_car_buy_time;

	@ViewInject(R.id.device_information_ac_car_recommend)
	private EditText mEt_device_information_ac_car_recommend;
	
	@ViewInject(R.id.device_information_ac_car_engine)
	private EditText mEt_device_information_ac_car_engine;
	
	
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		
		
		setContentView(R.layout.car_manager_information);
		
		
		context = this;
		ViewUtils.inject(this);
		
		actionbarInit() ;
		dataInit();
		
	}



	private void dataInit() {
		// TODO Auto-generated method stub
		
		
		
		Result  result =  (Result) getIntent().getSerializableExtra("value");
		
		
		 mEt_addcar_ac_car_device_num.setText(""+result.getEquipmentNo());
			mEt_device_information_ac_car_buy_time.setText(""+result.getBuyTime());
			mEt_device_information_ac_car_num.setText(""+result.getLicensePlate());
			mEt_device_information_ac_car_recommend.setText(""+result.getRecommend());
			mEt_device_information_ac_car_sort.setText(""+result.getModels());
			mEt_device_information_ac_car_VIN.setText(""+result.getFrameNo());
		 mEt_device_information_ac_car_engine.setText(""+result.getEngineNo());
		
		 
	}
	
	
	/** 定义actionbar相关参数 */

	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name;

	@ViewInject(R.id.all_actionbar_button_right)
	private Button actionbar_btn_right;

	/** 初始化actionbar */
	private void actionbarInit() {
		actionbar_tv_back_name.setText(getString(R.string.back));
		actionbar_tv_name.setText(getString(R.string.car_manager));

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_btn_right.setVisibility(View.GONE);

		// actionbar_ll_left.setOnClickListener(l);
		actionbar_btn_right.setText(getString(R.string.add));
		
	}

	/** 设置actionbar的监听 */
	@OnClick({ R.id.all_actionbar_linear_left, R.id.all_actionbar_button_right })
	private void onClick_actionBar(View view) {

		switch (view.getId()) {
		// 左边的返回
		case R.id.all_actionbar_linear_left:

			finish();

			break;
		// 右边的button
		case R.id.all_actionbar_button_right:
			
			
			startActivity(new Intent(context, AddCarActivity.class));
			

			break;

		default:
			break;
		}

	}

	
	

}
