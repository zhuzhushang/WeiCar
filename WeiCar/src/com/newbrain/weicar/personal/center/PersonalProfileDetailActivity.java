package com.newbrain.weicar.personal.center;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.user.User;
import com.newbrain.weicar.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PersonalProfileDetailActivity extends BaseActivity{
	
	
	private Context context;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.personal_profile_detail_activity);
		
		context = this;
		ViewUtils.inject(this);
		
		actionbarInit();
		
		dataInit();
	}


	@ViewInject(R.id.personal_center_user_avatars)
	private ImageView mIvHeadPic;
	
	
	@ViewInject(R.id.personal_profile_nicheng)
	private TextView mTvpersonal_profile_nicheng;
	
	@ViewInject(R.id.personal_profile_sex)
	private TextView mTvpersonal_profile_sex;
	
	
	@ViewInject(R.id.personal_profile_address)
	private TextView mTvpersonal_profile_address;
	
	
	@ViewInject(R.id.personal_profile_sign)
	private TextView mTvpersonal_profile_textview_black;
	
	
	@ViewInject(R.id.personal_profile_password_change)
	private TextView mTvpersonal_profile_password_change;
	
	
	private void dataInit() {
		// TODO Auto-generated method stub
		
		User user = User.getInstance();
		
		
		ImageLoader.getInstance().displayImage(user.getImage(), mIvHeadPic);
		
		mTvpersonal_profile_nicheng.setText(user.getNickName());
		mTvpersonal_profile_address.setText(user.getAddress());
		mTvpersonal_profile_sex.setText(user.getSex());
		mTvpersonal_profile_textview_black.setText(user.getSignature());
		
		
		
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
		actionbar_tv_name.setText(getString(R.string.comfirm1));
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
