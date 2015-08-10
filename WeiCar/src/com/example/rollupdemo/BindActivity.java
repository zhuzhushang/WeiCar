package com.example.rollupdemo;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatTransDataEvent;
import com.example.anychat.ControlCenter;
import com.newbrain.weicar.R;

public class BindActivity extends Activity implements OnClickListener,
OnItemClickListener, AnyChatTransDataEvent, AnyChatBaseEvent{
	private static final String TAG = "BindActivity";
	
	private static final int MAXDOORCOUNT = 10;// 最多可以绑定10个设备
	private Button btn_binddoor_back, btn_adddoor;
	public static BindListAdapter mAdapter;
	private ProgressDialog mProgressDialog, mpd;
	private ListView mListView;
	private AnyChatCoreSDK anychat;
	int dwTargetUserId=0;
	private Dialog dialog;
	private Timer time;
	private TimerTask task;
	private String path;
	private String number;
	private String deviceName;
	
	private String account = null;
	private String password = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		account = getIntent().getStringExtra("acc");
		password = getIntent().getStringExtra("psd");
		Log.w(TAG, "account="+account+" psd="+password);
		setContentView(R.layout.activity_bind);
		initSdk();
		btn_binddoor_back = (Button) findViewById(R.id.btn_binddoor_back);
		btn_binddoor_back.setOnClickListener(this);
		btn_adddoor = (Button) findViewById(R.id.btn_adddoor);
		btn_adddoor.setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.deviceslist);
		mListView.setOnItemClickListener(this);

		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle(R.string.is_binding);
		mProgressDialog.setMessage(getString(R.string.waiting));

		mpd = new ProgressDialog(this);
		mpd.setTitle(R.string.cancel_binging);
		mpd.setMessage(getString(R.string.waiting));
		
		anychat.EnterRoom(1, "");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "onStart");
//		initSdk();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		anychat.LeaveRoom(-1);
		super.onDestroy();
		Log.i(TAG, "onDestroy");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mAdapter = new BindListAdapter(this, Utils.deviceList);
		mListView.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_binddoor_back:// 返回按钮
			finish();
			break;
		case R.id.btn_adddoor:// 添加门铃按钮
			Log.i(TAG, "开始添加门铃绑定");
			bindDoor();
			break;
		}
	}

	private void bindDoor() {
		int count = mAdapter.getCount();
		if (count < MAXDOORCOUNT) {
			// 开始绑定门铃
			path = Utils.BINDDOORPATH;			
			final Dialog mDialog = new Dialog(this,R.style.CommonDialog);
			mDialog.setCanceledOnTouchOutside(false);
			LayoutInflater mLayoutInlater = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = mLayoutInlater
						.inflate(R.layout.dialog_bind, null);
			final EditText et_number = (EditText) view
					.findViewById(R.id.et_number);
			final EditText et_name = (EditText) view
					.findViewById(R.id.et_name);
			Button buttonPuase = (Button) view.findViewById(R.id.btn_ok);
			Button buttonCancel = (Button) view.findViewById(R.id.btn_cancel);
			
			buttonPuase.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					number = et_number.getText().toString().trim();
					deviceName = et_name.getText().toString().trim();
					if (deviceName.contains("(")
							||deviceName.contains(")")
							||deviceName.contains("（")
							||deviceName.contains("）")){
						Toast.makeText(BindActivity.this, getString(R.string.input_name_error), Toast.LENGTH_SHORT).show();
						return;
					}

					if (number.length() >= 14) {//14
						boolean result = Utils.isBindDevice(number);
						if (!result) {
							mProgressDialog.show();
							String s = number;
							Log.i(TAG, "设备号是："+number);
							byte buf[] = s.getBytes();
							int flag = anychat.TransBuffer(-1, buf,1024);
							Log.i(TAG, "flag:" + flag);//0是正常的
							recordRunTime();
						} else {
							Toast.makeText(BindActivity.this, 
									getString(R.string.had_binded), 
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(BindActivity.this, getString(R.string.input_error), Toast.LENGTH_SHORT).show();
					}
					mDialog.dismiss();
				}
			});
			buttonCancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mDialog.dismiss();
				}
			});
			mDialog.setContentView(view);
			mDialog.show();
		} else {
			Toast.makeText(BindActivity.this, getString(R.string.max_door_count), Toast.LENGTH_SHORT).show();
		}
	}

	private void recordRunTime() {
		time = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(2);
			}
		};
		time.schedule(task, 40 * 1000);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View myview, int position,
			long id) {
		// TODO Auto-generated method stub

		final HashMap<String, String> devices = (HashMap<String, String>) mAdapter.getItem(position);
		// 解除门铃绑定		
		final Dialog mDialog = new Dialog(this);
		mDialog.setCanceledOnTouchOutside(false);
		LayoutInflater mLayoutInlater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = mLayoutInlater
					.inflate(R.layout.dialog_resumeorcancel, null);
		TextView mTextViewTitle = (TextView) view.findViewById(R.id.txt_dialog_prompt);
		mTextViewTitle.setTextSize(20);
		mTextViewTitle.setText(getString(R.string.enture_cancel));
		Button buttonPuase = (Button) view.findViewById(R.id.btn_resume);
		Button buttonCancel = (Button) view.findViewById(R.id.btn_cancel);
		buttonPuase.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mpd.show();
				number = devices.get("device_name");
				path = Utils.REMOVEDOORPATH;
				new MyThread().start();
			}
		});
		buttonCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDialog.dismiss();
			}
		});
		mDialog.setContentView(view);
		mDialog.show();
	}

	private void initSdk() {
		if (anychat == null)
			anychat = new AnyChatCoreSDK();
		anychat.SetBaseEvent(this);
		anychat.SetTransDataEvent(this);
	}

	private void getLoginData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", account);
		map.put("password", password);

		String result = Utils.sendPostResquest(this, Utils.LOGINPATH, map,
				Utils.ENCORDING);

		String status = Utils.praseJson(result);
		if (status != null){
			if (status.contentEquals("success")) {
				// 更新设备id和name
				Utils.saveJsonToDB(this, result);
			} else if (status.equals("failure")) {
				Log.i(TAG, "手机号和密码是错误的！！！");
			}
		}
	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {

			mProgressDialog.dismiss();
			mpd.dismiss();
			switch (msg.what) {
			case 0:
				Toast.makeText(BindActivity.this, getString(R.string.failure), Toast.LENGTH_SHORT);
				break;
			case 1:
				//透明通道传输告知服务器有新的绑定关系建立
				if (Utils.BINDDOORPATH.contentEquals(path)){
					if (dwTargetUserId != 0){
						String s = "addfriend:"+dwTargetUserId;
						byte buf[] = s.getBytes();
						Log.i(TAG, s);
						anychat.TransBuffer(0, buf,1024);
						Toast.makeText(BindActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
					}else{
						Log.e(TAG, "通知anychat服务器绑定的好友id失败，dwTargetUserId="+dwTargetUserId);
					}
				}else{
					Toast.makeText(BindActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
				}
				
				Intent intent = new Intent(BindActivity.this,
						HallActivity.class);
				intent.putExtra("acc", account);
				intent.putExtra("psd", password);
				startActivity(intent);
				BindActivity.this.finish();
				break;
			case 2:
				if (time != null){
					time.cancel();
				}
				Toast.makeText(BindActivity.this, getString(R.string.bind_timeout), Toast.LENGTH_SHORT).show();
				break;
			case 3:
				if (dwTargetUserId != 0){
					String s = "delfriend:"+dwTargetUserId;
					byte buf[] = s.getBytes();
					Log.i(TAG, s);
					anychat.TransBuffer(0, buf,1024);
					Toast.makeText(BindActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
				}else{
					Log.e(TAG, "通知anychat服务器解除绑定失败");
				}

			}
		};
	};

	/**
	 * @param number
	 *            IMEI号
	 * @param path
	 *            绑定设备的接口
	 */
	class MyThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			Message msg = Message.obtain();
			try {
				Map<String, String> params = new HashMap<String, String>();
				params.put("device", number);
				params.put("alias", deviceName);
				String status = Utils.sendPostResquest(BindActivity.this,
						path, params, Utils.ENCORDING);
				Log.i(TAG, "status:" + status);
				if (!status.equals("")) {
					String result = Utils.praseJson(status);
					if (result.equals("success")) {
						if (path!=null && path.contentEquals(Utils.REMOVEDOORPATH)){
							//解除绑定
							for(int i=0; i<Utils.deviceList.size(); i++ ){
								String devcieString = Utils.deviceList.get(i).get("device_name");
								Log.i(TAG, "设备号："+devcieString);
								if (devcieString!=null && devcieString.contentEquals(number)){
									String s = "delfriend:"+Utils.deviceList.get(i).get("device_anychat_id");
									byte buf[] = s.getBytes();
									Log.i(TAG, "解除绑定"+s);
									anychat.TransBuffer(0, buf,1024);
									break;
								}
							}
						}
						getLoginData();
						msg.what = 1;
					} else {
						msg.what = 0;
					}
				} else {
					msg.what = 0;
				}
			} catch (Exception e) {
				msg.what = 0;
				e.printStackTrace();
			}
			mHandler.sendMessage(msg);
		}
	}

	@Override
	public void OnAnyChatTransFile(int dwUserid, String FileName,
			String TempFilePath, int dwFileLength, int wParam, int lParam,
			int dwTaskId) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatTransFile");
	}

	@Override
	public void OnAnyChatTransBuffer(int dwUserid, byte[] lpBuf, int dwLen) {
		// TODO Auto-generated method stub
		if (time != null){
			time.cancel();
			String string = new String(lpBuf);
			Log.i(TAG, "string-->" + string);
			if (number.equals(string)) {
				dwTargetUserId = dwUserid;
				new MyThread().start();//开始绑定设备
			} else if (string.equals("13")) {// 拒绝绑定
				if (mProgressDialog != null) {
					mProgressDialog.dismiss();
				}
				Toast.makeText(BindActivity.this, getString(R.string.reject_bind), Toast.LENGTH_SHORT).show();
			} else if (string.equals("14")) {// 设备端已经绑定
				dwTargetUserId = dwUserid;
				new MyThread().start();
			} else {
			}
		}
		
	}

	@Override
	public void OnAnyChatTransBufferEx(int dwUserid, byte[] lpBuf, int dwLen,
			int wparam, int lparam, int taskid) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatTransBufferEx");
	}

	@Override
	public void OnAnyChatSDKFilterData(byte[] lpBuf, int dwLen) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatSDKFilterData");
	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatLoginMessage");
		if (dialog != null
				&& dialog.isShowing()
				&& DialogFactory.getCurrentDialogId() == DialogFactory.DIALOGID_RESUME) {
			dialog.dismiss();
		}
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatLoginMessage");
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatEnterRoomMessage");
		if (ControlCenter.sessionItem == null)
			return;
//		if (dwErrorCode == 0) {
//			Toast.makeText(this, R.string.reconnect_net, Toast.LENGTH_LONG)
//					.show();
//		} 
//		else {
//			BaseMethod.showToast(this.getString(R.string.str_enterroom_failed),
//					this);
//		}
		if (dialog != null)
			dialog.dismiss();
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatOnlineUserMessage");
	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatOnlineUserMessage");
	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatLinkCloseMessage");
	}


}
