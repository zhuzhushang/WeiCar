package com.example.rollupdemo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anychat.ConfigEntity;
import com.example.anychat.ConfigHelper;
import com.example.anychat.ControlCenter;
import com.newbrain.weicar.R;

public class LoginActivity extends Activity implements OnClickListener{
	
	private final String TAG = "LoginActivity";
	
	private final int MSG_LOGIN_SUCCESS = 1;//登录成功
	private final int MSG_LOGIN_ERROR_OTHER = 2;//未知错误
	private final int MSG_LOGIN_ERROR_PSW = 3;//密码错误
	private final int MSG_LOGIN_ERROR_NOREGIST = 4;//未注册
	
	private EditText accountEditText = null;
	private EditText psdEditText = null;
	private Button loginButton = null;
	private ProgressDialog mProgressLogin = null;
	
	private String strSelfName = "";
	private String strSelfPass = "";
	private ConfigEntity configEntity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		intParams();
		initView();
		initLoginProgress();
	}
	
	private void initView(){
		accountEditText = (EditText) this.findViewById(R.id.phone_login);
		psdEditText = (EditText) this.findViewById(R.id.password_login);
		loginButton = (Button) this.findViewById(R.id.btn_login);
		loginButton.setOnClickListener(this);
	}
	
	protected void intParams() {
		configEntity = ConfigHelper.getConfigHelper().LoadConfig(this);
		ControlCenter.getControlCenter();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			mProgressLogin.setMessage(this.getString(R.string.login_progress));
			Login();
			break;

		default:
			break;
		}
	}
	
	private void initLoginProgress() {
		mProgressLogin = new ProgressDialog(this);
		mProgressLogin.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				loginButton.setClickable(true);
			}
		});
		mProgressLogin.setCanceledOnTouchOutside(false);
		mProgressLogin.setMessage(this.getString(R.string.login_progress));
	}
	
	//���ڷ���������ʵ���ж��û����Ĵ�Сд���ͻ�����ע��͵�¼ʱ��ͳһ��ǿ��ת��ΪСд����ֹ����ʱtag��ƥ��
		private void Login() {
			strSelfName = accountEditText.getEditableText().toString().trim().toLowerCase();
			strSelfPass = psdEditText.getEditableText().toString().trim();

			configEntity.name = strSelfName;
			configEntity.password = strSelfPass;

			ConfigHelper.getConfigHelper().SaveConfig(this, configEntity);
			if (accountEditText.getText().length() == 0) {
				Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			if (psdEditText.getText().length() == 0) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			mProgressLogin.show();
			new LoginThread().start();
		}
	
	class LoginThread extends Thread {
		@Override
		public void run() {
			Message msg = Message.obtain();
			try {
				Map<String, String> params = new HashMap<String, String>();
				params.put("mobile", strSelfName);
				params.put("password", strSelfPass);
				String str = Utils.sendPostResquest(LoginActivity.this,
						Utils.LOGINPATH, params, Utils.ENCORDING);
				Log.i(TAG, "str-->" + str);
				String status = Utils.praseJson(str);

				if (status.equals("success")) {	
					Utils.saveJsonToDB(LoginActivity.this, str);
					msg.what = MSG_LOGIN_SUCCESS;
				} else if (status.equals("failure")) {
					JSONObject json = new JSONObject(str);
					try {
						String code = json.getString("code");
						if(code != null){
							if (code.contentEquals("001")){
								//�������
								msg.what = MSG_LOGIN_ERROR_PSW;
							}else if (code.contentEquals("002")){
								//δע��
								msg.what = MSG_LOGIN_ERROR_NOREGIST;
							}else{
								msg.what = MSG_LOGIN_ERROR_OTHER;
							}
						}else{
							msg.what = MSG_LOGIN_ERROR_OTHER;
						}
					} catch (Exception e) {
						// TODO: handle exception
						msg.what = MSG_LOGIN_ERROR_OTHER;
					}
				}
			} catch (Exception e) {
				msg.what = MSG_LOGIN_ERROR_OTHER;
				e.printStackTrace();
			}
			mHandler.sendMessage(msg);
		}
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_LOGIN_SUCCESS:
				Utils.isLogin = true;
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, HallActivity.class);
				intent.putExtra("acc", strSelfName);
				intent.putExtra("psd", strSelfPass);
				LoginActivity.this.startActivity(intent);

				break;
			case MSG_LOGIN_ERROR_OTHER:
				Toast.makeText(LoginActivity.this, R.string.login_failure,
						Toast.LENGTH_SHORT).show();
				break;
			case MSG_LOGIN_ERROR_PSW:
				Toast.makeText(LoginActivity.this, R.string.login_psd_error,
						Toast.LENGTH_SHORT).show();
				break;
			case MSG_LOGIN_ERROR_NOREGIST:
				Toast.makeText(LoginActivity.this, R.string.login_regist_error,
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
			if (mProgressLogin != null){
				mProgressLogin.dismiss();
			}

		};
	};

}
