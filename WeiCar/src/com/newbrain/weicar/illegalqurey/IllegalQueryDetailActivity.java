package com.newbrain.weicar.illegalqurey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.model.Model_IllegalQuery.Result.IllegalList;
import com.newbrain.utils.Constant;
import com.newbrain.weicar.R;

public class IllegalQueryDetailActivity extends BaseActivity {

	private Context context;

	@ViewInject(R.id.illegal_query_address)
	private TextView mTv_illegal_query_address;

	@ViewInject(R.id.illegal_query_content)
	private TextView mTv_illegal_query_content;

	@ViewInject(R.id.illegal_query_city)
	private TextView mTv_illegal_query_city;

	@ViewInject(R.id.illegal_query_money)
	private TextView mTv_illegal_query_money;

	@ViewInject(R.id.illegal_query_points)
	private TextView mTv_illegal_query_points;

	@ViewInject(R.id.illegal_query_state)
	private TextView mTv_illegal_query_state;

	@ViewInject(R.id.illegal_query_time)
	private TextView mTv_illegal_query_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.illegal_query_detail_activity);

		context = this;
		ViewUtils.inject(this);

		actionbarInit();

		getSetData();

	}

	
	/*"lists":[
				{
				"date":"2013-12-29 11:57:29",
				"area":"316省道53KM+200M",
				"act":"16362 : 驾驶中型以上载客载货汽车、校车、危险物品运输车辆以外的其他机动车在高速公路以外的道路上行驶超过规定时速20%以上未达50%的",
				"code":"",
				"fen":"6",
				"money":"100",
				"handled":"0"
				}
			]*/
	
	
	private void getSetData() {
		// TODO Auto-generated method stub
		
		
		
		IllegalList illegalList = (IllegalList) getIntent().getSerializableExtra(Constant.FLAG_ACTIVITY_RESULT);
		
		String hphm = getIntent().getStringExtra(Constant.FLAG_ACTIVITY_INDEX_FRIST);
		
		actionbar_tv_name.setText(hphm);
		
		mTv_illegal_query_address.setText(illegalList.getArea());
		mTv_illegal_query_content.setText(illegalList.getAct());
		mTv_illegal_query_money.setText(illegalList.getMoney());
		mTv_illegal_query_points.setText(illegalList.getFen());
		if(illegalList.getHandled().equals("0"))
		{
			
			mTv_illegal_query_state.setText("未处理");
			mTv_illegal_query_state.setTextColor(getResources().getColor(R.color.theme_text_normal_color_red));
			
		}else
		{
			mTv_illegal_query_state.setText("已处理");
			mTv_illegal_query_state.setTextColor(getResources().getColor(R.color.theme_text_normal_color_black));
			
		}
		
		mTv_illegal_query_time.setText(illegalList.getDate());
		
		

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
		actionbar_tv_name.setText(getString(R.string.comfirm1));
		actionbar_btn_right.setText(getString(R.string.comfirm1));

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_btn_right.setVisibility(View.GONE);

		// actionbar_ll_left.setOnClickListener(l);

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

			break;

		default:
			break;
		}

	}

}
