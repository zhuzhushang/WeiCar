package com.newbrain.weicar.gridview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.Model_Login;
import com.newbrain.model.Model_Login.Result;
import com.newbrain.user.User;
import com.newbrain.utils.Constant;
import com.newbrain.weicar.MainActivity;
import com.newbrain.weicar.R;


public class LoginActivity extends BaseActivity{
	
	
	private Context context;
	
	
	@ViewInject(R.id.login_phone_num)
	private EditText et_phone_num;
	
	
	@ViewInject(R.id.login_phone_num)
	private EditText mTv_login_phone_num;
	
	@ViewInject(R.id.login_password)
	private EditText mTv_login_password;
	
	private String phone_num,password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login_activity);
		
		context = this;
		ViewUtils.inject(this);
		
		actionbarInit();
		
		falseDataInit();		
		
	}
	
	
	
	private void falseDataInit() {
		// TODO Auto-generated method stub
		
		mTv_login_phone_num.setText("13823214300");
		mTv_login_password.setText("123456");
		
		
		
	}



	@OnClick({R.id.login_register})
	private void onClick_login(View view)
	{
		switch (view.getId()) {
		case R.id.login_register:
			
			openActivity(RegisterActivity.class);
			
			break;
			

		default:
			break;
		}
		
		
	}
	
	
	
//	http://192.168.0.110:8080/weiCar/interf/customer/login?phoneNo=x&password=xxx
	
	private void requestHttp_login() {
		// TODO Auto-generated method stub
		
		if(checkData())
		{
			List<Bean> list = new ArrayList<Bean>();
			
			list.add(new Bean("phoneNo", phone_num));
			list.add(new Bean("password", password));
			
			JsonThread jsonThread = new JsonThread(context, list, mHandler, Constant.FLAG_LOGIN);
			
			jsonThread.start();
			
			CustomProgressDialog.startProgressDialog(context);
		}
		
		
		
	}
	
	
	public Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) 
		{
			
			switch (msg.what) {
			case Constant.FLAG_LOGIN:
				
				
//			{"code":"1","message":"登录成功","result":{"address":"","age":22,"hxId":"0","id":"402880ed4e8b6fc7014e8b76f86f0000","image":"",
//				"nickName":"未命名","phoneNo":"13823214300","sex":0,"signature":""}}

				
				
				
				
				Model_Login login = (Model_Login) msg.obj;
				Result result = login.getResult();
				
				User user = User.getInstance();
				
				user.setAge(result.getAge());
				user.setImage(result.getImage());
				user.setNickName(result.getNickName());
				user.setPassword(result.getPassword());
				user.setPhoneNo(result.getPhoneNo());
				user.setSex(result.getSex());
				user.setSignature(result.getSignature());
				user.setHxId(result.getHxId());
				user.setId(result.getId());
				
				
				openActivity(MainActivity.class);
				showShortToast("登陆成功"); 
				
				CustomProgressDialog.stopProgressDialog();
				
				finish();
				
				
				break;

			default:
				break;
			}
			
		};
		
		
		
	};
	
	
	
	private boolean checkData()
	{
		password = mTv_login_password.getText().toString();
		phone_num = mTv_login_phone_num.getText().toString();
		
		
		 if("".equals(phone_num))
		{
			
			
			showShortToast(getString(R.string.phone_num)+getString(R.string.can_not_null));
			return false;
			
		}else if("".equals(password))
		{
			
			showShortToast(getString(R.string.password)+getString(R.string.can_not_null));
			
			return false;
			
		}
		
		
		return true;
		
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
		actionbar_tv_name.setText(getString(R.string.login));
		actionbar_btn_right.setText(getString(R.string.comfirm));
		
		
//		actionbar_rl_contain.setBackgroundResource(R.color.theme_actionbar_background_color_blue);
		

		actionbar_ll_left.setVisibility(View.GONE);
		actionbar_btn_right.setVisibility(View.VISIBLE);

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
			
			
			requestHttp_login();
			
			

			break;

		default:
			break;
		}

	}
	
	
}
