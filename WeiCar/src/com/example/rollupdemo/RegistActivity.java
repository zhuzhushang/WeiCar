package com.example.rollupdemo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.newbrain.weicar.R;

public class RegistActivity extends Activity implements OnClickListener{
	
	private final String TAG = "RegistActivity";
	
	private EditText mobile_reg, password_reg, name_reg,city_reg, email_reg;
	private Button register_reg;
	private String account, password, username, city, device, email;
	private ProgressDialog myDialog = null;

	@SuppressLint("HandlerLeak")
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			String str = (String) msg.obj;
			Log.i("RegisterActivity", "msg.obj = " + str);
			String status = Utils.praseJson(str);
			myDialog.dismiss();
			if (status != null){
				if (status.equals("success")) {
					Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
				} else if (status.equals("failure")) {	
					String code = "";
					try {
						JSONObject json = new JSONObject(str);
						code = json.getString("code");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();	
					}
					if ("001".contentEquals(code)){
						Log.e(TAG, "账号已被注册");
					}else if ("002".contentEquals(code)){//
						Log.e(TAG, "账号密码不能为空");
					}else{
						Log.e(TAG, "注册失败");
					}
				}
			}else{
				myDialog.dismiss();
				Log.e(TAG, "注册失败");
			}
		};
	};

	class RegisterThread implements Runnable {
		@Override
		public void run() {
			try {
				Message msg = Message.obtain();
				Map<String, String> params = new HashMap<String, String>();
				
				params.put("mobile", account);
				params.put("password", password);
				params.put("name", username);
				params.put("city", city);
				params.put("device", device);
				params.put("email", email);
				msg.obj = Utils.sendPostResquest(RegistActivity.this,
						Utils.REGISTERPATH, params, Utils.ENCORDING);
				mHandler.sendMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_regist);

		

		mobile_reg = (EditText) findViewById(R.id.phone_reg);
		password_reg = (EditText) findViewById(R.id.password_reg);
		name_reg = (EditText) findViewById(R.id.username_reg);
		city_reg = (EditText) findViewById(R.id.city_reg);
		email_reg = (EditText) findViewById(R.id.edittext_email);
		register_reg = (Button) findViewById(R.id.register_reg);

		register_reg.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.register_reg:
			if (Utils.IsHaveInternet(this)) {
				account = mobile_reg.getText().toString().trim().toLowerCase();

				password = password_reg.getText().toString();

				username = name_reg.getText().toString();
				city = city_reg.getText().toString();
				email = email_reg.getText().toString();
				String regexEmail = "[_a-zA-Z\\d\\-\\./]+@[_a-zA-Z\\d\\-]+(\\.[_a-zA-Z\\d\\-]+)+";

				if (Utils.getIMEI(this) == null) {
					device = Secure.getString(getContentResolver(),
							Secure.ANDROID_ID);
				} else {
					device = Utils.getIMEI(this);
				}

				if ("".equals(account)) {
					Log.e(TAG,"账号不能为空");
				}else if (account.matches("^0520.*$")){
					Log.e(TAG, "账号不能以0520开始");
				}else if ("".equals(email)){
					Log.e(TAG, "email不能为空");
				}else if (!email.matches(regexEmail)){
					Log.e(TAG,"Email格式有误");
				}else if (!account.matches("^\\w+$")){
					Log.e(TAG, "账户只能包含字母和数字");
				}else if ("".equals(password)) {
					Log.e(TAG, "密码不能为空");
				} else if ("".equals(username)) {
					Log.e(TAG, "姓名不能为空");
				} else if ("".equals(city)) {
					Log.e(TAG, "城市不能为空");
				} else if ("".equals(device)) {
					Log.e(TAG, "未能获取设备IMEI号");
				} else {
					myDialog = ProgressDialog
							.show(this, getString(R.string.being_register), getString(R.string.waiting), true);
					new Thread(new RegisterThread()).start();
				}
			} else {
				Log.e(TAG, "网络不可用");
			}
			break;
		}
	}

}
