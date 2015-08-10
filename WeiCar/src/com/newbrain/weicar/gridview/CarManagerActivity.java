package com.newbrain.weicar.gridview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.adapter.MyBaseAdapter_CarManager;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.Model_CarManager;
import com.newbrain.model.Model_GetAllCar;
import com.newbrain.model.Model_GetAllCar.Result;
import com.newbrain.user.User;
import com.newbrain.utils.Constant;
import com.newbrain.weicar.R;
import com.newbrain.weicar.carmanager.AddCarActivity;
import com.newbrain.weicar.carmanager.CarInformationActivity;

public class CarManagerActivity extends BaseActivity {

	private Context context;

	@ViewInject(R.id.car_manager_listview)
	private ListView mListView;

	private List<Result> mList_carManager;

	private MyBaseAdapter_CarManager mMyAdapter_CarManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.car_manager_activity);

		context = this;
		ViewUtils.inject(this);

		actionbarInit();

//		listviewInit();

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		listviewInit();
		
		
	}

	private void listviewInit() {
		// TODO Auto-generated method stub
		
		
		mList_carManager = new ArrayList<Result>();
		
		/**模拟数据*/
		mMyAdapter_CarManager = new MyBaseAdapter_CarManager(context, mList_carManager);
		
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				
				Intent intent = new Intent(context, CarInformationActivity.class);
				intent.putExtra("position", position);
				intent.putExtra("value", mList_carManager.get(position));
				
				startActivity(intent);
				
				
			}
		});
		
		listDataInit();

	}
	
	
	/**初始化listview的数据*/
	private void listDataInit() {
		// TODO Auto-generated method stub
		
		
		
		requestHttp_GetAllCar();
		
	}
	
	
	
	public final int MFLAG = Constant.FLAG_GET_ALL_CAR;
	
	private void requestHttp_GetAllCar() {

		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("customerId", "" + User.getInstance().getId()));

		JsonThread jsonThread = new JsonThread(context, list, mHandler,
				MFLAG);

		jsonThread.start();

		CustomProgressDialog.startProgressDialog(context);

	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case MFLAG:
				
				
				
				
				
				Model_GetAllCar model_GetAllCar = (Model_GetAllCar) msg.obj;
				
				mList_carManager = model_GetAllCar.getResult();
				
				mMyAdapter_CarManager.setList(model_GetAllCar.getResult());
				
				
				
				mListView.setAdapter(mMyAdapter_CarManager);
				
				CustomProgressDialog.stopProgressDialog();

				break;

			default:
				break;
			}

		};

	};
	
	
	
	
	

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
		actionbar_btn_right.setVisibility(View.VISIBLE);

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
