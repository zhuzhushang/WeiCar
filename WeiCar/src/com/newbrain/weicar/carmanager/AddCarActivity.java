package com.newbrain.weicar.carmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.Model_AddCar;
import com.newbrain.user.User;
import com.newbrain.utils.Constant;
import com.newbrain.weicar.R;

public class AddCarActivity extends BaseActivity {

	private Context context;

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

	private String addcar_ac_car_device_num, device_information_ac_car_num,
			device_information_ac_car_VIN, device_information_ac_car_sort,
			device_information_ac_car_buy_time,
			device_information_ac_car_recommend,device_information_ac_car_engine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_car_activity);

		context = this;
		ViewUtils.inject(this);

		actionbarInit();

	}

	/*
	 * equipmentNo String 设备号 否 models String 车型 否 frameNo String 车架号 否
	 * licensePlate String 车牌号 否 engineNo String 发动机号 否 buyTime String 购买时间 否
	 * recommend String 推荐人 否 customer.id String 会员id 是 sim String 手机sim卡号 是
	 */

	private void requestHttp_AddCar() {

		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("equipmentNo", addcar_ac_car_device_num));
		list.add(new Bean("models", device_information_ac_car_sort));
		list.add(new Bean("frameNo", device_information_ac_car_VIN));
		list.add(new Bean("licensePlate", device_information_ac_car_num));
		list.add(new Bean("engineNo",device_information_ac_car_engine));
		list.add(new Bean("buyTime", device_information_ac_car_buy_time));
		list.add(new Bean("recommend", device_information_ac_car_recommend));
		list.add(new Bean("customer.id", "" + User.getInstance().getId()));
		list.add(new Bean("sim", User.getInstance().getPhoneNo()));
		
		

		JsonThread jsonThread = new JsonThread(context, list, mHandler,
				Constant.FLAG_ADD_CAR);

		jsonThread.start();

		CustomProgressDialog.startProgressDialog(context);

	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case Constant.FLAG_ADD_CAR:

				mEt_addcar_ac_car_device_num.setText("");
				mEt_device_information_ac_car_buy_time.setText("");
				mEt_device_information_ac_car_num.setText("");
				mEt_device_information_ac_car_recommend.setText("");
				mEt_device_information_ac_car_sort.setText("");
				mEt_device_information_ac_car_VIN.setText("");
				mEt_device_information_ac_car_engine.setText("");

				showShortToast("添加成功");

				CustomProgressDialog.stopProgressDialog();

				break;

			default:
				break;
			}

		};

	};

	private boolean checkData() {
		// TODO Auto-generated method stub

		addcar_ac_car_device_num = mEt_addcar_ac_car_device_num.getText()
				.toString();
		device_information_ac_car_buy_time = mEt_device_information_ac_car_buy_time
				.getText().toString();
		device_information_ac_car_num = mEt_device_information_ac_car_num
				.getText().toString();
		device_information_ac_car_recommend = mEt_device_information_ac_car_recommend
				.getText().toString();
		device_information_ac_car_sort = mEt_device_information_ac_car_sort
				.getText().toString();
		device_information_ac_car_VIN = mEt_device_information_ac_car_VIN
				.getText().toString();
		
		device_information_ac_car_engine  = mEt_device_information_ac_car_engine.getText().toString();

		if (addcar_ac_car_device_num.equals("")) {

			showShortToast(getString(R.string.ac_car_device_num)
					+ getString(R.string.can_not_null));

			return false;
		} else if (device_information_ac_car_num.equals("")) {

			showShortToast(getString(R.string.ac_car_num)
					+ getString(R.string.can_not_null));
			return false;
		} else if (device_information_ac_car_VIN.equals("")) {

			showShortToast(getString(R.string.ac_car_Vehicle_Identification_Number)
					+ getString(R.string.can_not_null));
			return false;
		}

		return true;
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
		actionbar_tv_name.setText(getString(R.string.ac_add_car));

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_btn_right.setVisibility(View.VISIBLE);

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

			if (checkData()) {

				requestHttp_AddCar();

			}

			break;

		default:
			break;
		}

	}

}
