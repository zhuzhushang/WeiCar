package com.newbrain.weicar.personal.center;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.user.User;
import com.newbrain.weicar.R;
import com.newbrain.weicar.gridview.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PersonalCenterActivity extends BaseActivity{
	
	
	private Context context;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.personal_center_activity);
		
		context = this;
		ViewUtils.inject(this);
		
		actionbarInit();
		
		
		dataInit();
	}
	
	@ViewInject(R.id.personal_center_user_login_or_user_detail)
	private TextView mTvDetail;
	
	
	@ViewInject(R.id.personal_center_user_avatars)
	private ImageView mIVHeadPicture;
	
	@ViewInject(R.id.personal_center_user_message)
	private RelativeLayout mRlCenter;
	
	

	private void dataInit() {
		// TODO Auto-generated method stub
		
		if(!User.getInstance().getId().equals(""))
		{
			
			mTvDetail.setText(User.getInstance().getNickName());
			
			
			if(!User.getInstance().getImage().equals(""))
			{
				
				ImageLoader.getInstance().displayImage(User.getInstance().getImage(), mIVHeadPicture);
				
			}
			
		}else
		{
			
			
			openActivity(LoginActivity.class);
			
		}
		
		
	}



	
	@OnClick({R.id.personal_center_user_message})
	private void  onClick_Center(View view)
	{
		switch (view.getId()) {
		case R.id.personal_center_user_message:
			
			
			if(User.getInstance().getId().equals(""))
			{
				
				
				startActivity(new Intent(context, LoginActivity.class));
				
				
			}else
			{
				
				startActivity(new Intent(context, PersonalProfileDetailActivity.class));
				
				
			}
			
			
			break;

		default:
			break;
		}
		
		
	}
	
	
	
	
/**定义actionbar相关参数*/
	
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
		actionbar_tv_name.setText(getString(R.string.personal_center_));
		actionbar_btn_right.setText(getString(R.string.comfirm1));

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
