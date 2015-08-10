package com.newbrain.weicar.car.friends;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.weicar.R;

public class GroupVerityMessageActivity extends BaseActivity{
	
	
	private Context context;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.group_verify_message_activity);
		
		context = this;
		ViewUtils.inject(this);
		
		actionbarInit();
	}
	
	
	
	
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
