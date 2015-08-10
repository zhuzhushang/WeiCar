package com.example.rollupdemo;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.bairuitech.anychat.AnyChatTransDataEvent;
import com.bairuitech.anychat.AnyChatUserInfoEvent;
import com.bairuitech.anychat.AnyChatVideoCallEvent;
import com.example.anychat.BaseConst;
import com.example.anychat.ConfigEntity;
import com.example.anychat.ConfigHelper;
import com.example.anychat.ControlCenter;
import com.example.anychat.SessionItem;
import com.example.anychat.UserItem;
import com.example.anychat.VideoCallContrlHandler;
import com.newbrain.weicar.R;

public class HallActivity extends Activity implements OnItemClickListener,
OnClickListener, AnyChatBaseEvent, AnyChatVideoCallEvent, AnyChatTransDataEvent,
AnyChatUserInfoEvent{
	private final String TAG = "HallActivity";
	
	private AnyChatCoreSDK anychat;
	private ListView mListView;
	private Dialog dialog;
	private UserAdapter mUserAdapter;
	private String userName = null;
	private String deviceVer = ""; 
	private Button mAddDoor = null;

	private ConfigEntity configEntity;
	
	private Timer time = null;
	private TimerTask task = null;
	
	private String account = null;
	private String password = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSdk();
		intParams();
		ConfigHelper.getConfigHelper().ApplyVideoConfig(this);
		ControlCenter.getControlCenter().initFriendDatas(1);

		anychat.Connect(configEntity.ip, configEntity.port);
		anychat.Login(configEntity.name, configEntity.password);

		initView();
		password = getIntent().getStringExtra("psd");
		account = getIntent().getStringExtra("acc");
		startBackServce();
	}

		
	protected void intParams() {
		configEntity = ConfigHelper.getConfigHelper().LoadConfig(this);
		ControlCenter.getControlCenter();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	public void onStart() {
		Log.i(TAG, "onStart");
		super.onStart();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		initSdk();
		ControlCenter.mContext = HallActivity.this;
		new Thread(){
			public void run() {
				getLoginData();
			};
		}.start();

		super.onResume();

	}
	
	private void getLoginData() {
		// 把账号和密码发送至服务器
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", account);
		map.put("password", password);

		String result = Utils.sendPostResquest(this, Utils.LOGINPATH, map,
				Utils.ENCORDING);

		String status = Utils.praseJson(result);
		
		if (status != null){
			if (status.equals("success")) {
				// 更新设备id和name
				Utils.saveJsonToDB(this, result);
				ControlCenter.getControlCenter().getOnlineFriendDatas();
				mHandler.sendEmptyMessage(1);
			} else if (status.equals("failure")) {
				Log.i(TAG, "账号和密码是错误的！！！");
			}
		}else{
			ControlCenter.getControlCenter().initFriendDatas(1);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestroy");
		ControlCenter.getControlCenter().realseData();
		Intent intent = new Intent();
		intent.setAction(BaseConst.ACTION_BACKSERVICE);
		intent.setPackage(getPackageName());
		HallActivity.this.stopService(intent);

		anychat.Logout();
		anychat.Release();

		if (time != null){
			time.cancel();
			time = null;
		}
		if (task!=null){
			task = null;
		}
		super.onDestroy();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub	
		super.onPause();
		Log.i(TAG, "onPause");
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			ConfigHelper.getConfigHelper().ApplyVideoConfig(this);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_link_binddoor:
			Intent intent2 = new Intent(HallActivity.this, BindActivity.class);
			intent2.putExtra("acc", account);
			intent2.putExtra("psd", password);
			startActivity(intent2);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		UserItem item = ControlCenter.getControlCenter().getUserItemByIndex(
				arg2);
		userName = item.getUserName();
		deviceVer = item.getDeviceVer();
		Log.i(TAG, "用户名：" + item.getUserName()+",设备版本："+deviceVer);
		final int id = item.getUserId();
		if (item != null && item.getIsOnline()==1) {
			if (item.getUserId() == ControlCenter.selfUserId) {
				return;
			}
			if (ControlCenter.mEventType!=-1 && ControlCenter.mEventType!=AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH){
				//发起视频通话后未结束
				if(ControlCenter.sessionItem != null){
				ControlCenter.VideoCallContrl(
						AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH, ControlCenter.sessionItem.targetUserId, 0,
						0, ControlCenter.selfUserId, "");
				}
			}
			ControlCenter.sessionItem = new SessionItem(0, item.getUserId(),
					ControlCenter.selfUserId);
			
			ControlCenter.VideoCallContrl(
					AnyChatDefine.BRAC_VIDEOCALL_EVENT_REQUEST, id, 0,
					0, 0, "");
		}else{
			Toast.makeText(this, getString(R.string.device_offline), Toast.LENGTH_SHORT).show();
		}
	}

	private void initView() {
		// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_hall);
		
		mAddDoor = (Button) findViewById(R.id.btn_link_binddoor);
		mAddDoor.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.list);

		mUserAdapter = new UserAdapter(this);
		mListView.setAdapter(mUserAdapter);
		mListView.setOnItemClickListener(this);
	}

	private void initSdk() {
		if (anychat == null) {
			anychat = new AnyChatCoreSDK();
			ConfigHelper.getConfigHelper().ApplyVideoConfig(this);
		}
		anychat.SetBaseEvent(this);
		anychat.SetVideoCallEvent(this);
		anychat.SetUserInfoEvent(this);
		anychat.SetTransDataEvent(this);
		anychat.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
		Log.i(TAG, "initSdk");
	}

	protected void startBackServce() {
		Intent intent = new Intent();
		intent.setAction(BaseConst.ACTION_BACKSERVICE);
		intent.setPackage(getPackageName());//这里你需要设置你应用的包名（5.0开始需要显式调用）
		this.startService(intent);
	}
	
	class ExitThread extends Thread {
		@Override
		public void run() {
			super.run();
			Message msg = Message.obtain();
			try {
				String str = Utils.sendPostResquest(HallActivity.this,
						Utils.EXITPATH, null, Utils.ENCORDING);
				String status = Utils.praseJson(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {		
			new ExitThread().start();
			mHandler.sendEmptyMessage(4);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private int count = 0;

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		// TODO Auto-generated method stub
		if (dialog != null
				&& dialog.isShowing()
				&& DialogFactory.getCurrentDialogId() == DialogFactory.DIALOGID_RESUME) {
			dialog.dismiss();
		}
		if (!bSuccess){
			if (count < 10){
				count++;
			}else{
				Toast.makeText(getApplicationContext(), getString(R.string.connect_fail), Toast.LENGTH_SHORT).show();
//				MainApplication.getInstance().AppExitToMain();
				finish();
			}
		}
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		// TODO Auto-generated method stub
		Log.i(TAG, "dwUserId:" + dwUserId + ",dwErrorCode:" + dwErrorCode);
		if (dwErrorCode == 0) {
			ControlCenter.selfUserId = dwUserId;
			ControlCenter.selfUserName = anychat.GetUserName(dwUserId);

		} else {
			Toast.makeText(this, getString(R.string.login_failure), Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub
		Log.i(TAG, "dwRoomId:" + dwRoomId + ", dwErrorCode:" + dwErrorCode);
		if (ControlCenter.sessionItem == null)
			return;
		if (dialog != null)
			dialog.dismiss();
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatUserAtRoomMessage");
	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// TODO Auto-generated method stub
		Log.i(TAG, "OnAnyChatLinkCloseMessage dwErrorCode"+dwErrorCode);
		if (dwErrorCode != 0){
			Toast.makeText(getApplicationContext(), getString(R.string.connect_fail)+"("+dwErrorCode+")", Toast.LENGTH_SHORT).show();
		}else{
			ControlCenter.getControlCenter().initFriendDatas(0);
			if (mUserAdapter != null)
				mUserAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void OnAnyChatVideoCallEvent(int dwEventType, int dwUserId,
			int dwErrorCode, int dwFlags, int dwParam, String userStr) {
		// TODO Auto-generated method stub
		Log.i(TAG, "dwUserId-->" + dwUserId);
		Log.i(TAG, "dwErrorCode-->" + dwErrorCode);

		switch (dwEventType) {
		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_REQUEST:// < 呼叫请求
			Log.i(TAG, "有人发呼叫请求过来了");
			ControlCenter.getControlCenter().VideoCall_SessionRequest(dwUserId,
					dwFlags, dwParam, userStr);
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_REQUEST,
					dwUserId, this);
			dialog.show();
			break;
		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY:// < 呼叫请求回复 开始向设备端发送视频请求
			Log.i(TAG, "呼叫请求回复");
			ControlCenter.getControlCenter().VideoCall_SessionReply(dwUserId,
					dwErrorCode, dwFlags, dwParam, userStr);
			if (dwErrorCode == VideoCallContrlHandler.ERRORCODE_SUCCESS) {
				dialog = DialogFactory.getDialog(
						DialogFactory.DIALOGID_CALLING, dwUserId,
						HallActivity.this);
				dialog.show();
			} else if (dwErrorCode == VideoCallContrlHandler.ERRORCODE_SESSION_REFUSE) {
				Log.i(TAG, "userName->" + userName);
				
				if (dialog != null && dialog.isShowing())
					dialog.dismiss();
				
				if (userStr!=null && !userStr.contentEquals("")){
					//设备端在用户绑定房间
					Toast.makeText(this, userStr, Toast.LENGTH_SHORT).show();
				}else{
					// 设备端拒绝视频即设备端解除了和手机端绑定
					// 门铃端解除了绑定，要实现1、更新本地数据库；2、更新服务器数据库
					//updataLink(userName);
					Toast.makeText(this, getString(R.string.str_returncode_requestrefuse), Toast.LENGTH_SHORT).show();
				}
				
			} else if (dwErrorCode == VideoCallContrlHandler.ERRORCODE_SESSION_BUSY) {// 用户忙,两种情况：1、对方正在和别人在通话，2、对方的摄像头正被别应用使用
				Log.i(TAG, "子机正忙，请稍后再试");
				Toast.makeText(this, getString(R.string.device_busy), Toast.LENGTH_SHORT).show();
			} else {// 超时，网络中断，不在线
				if (dialog !=null){
					dialog.dismiss();
				}
			}
			break;
		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_START:// 视频呼叫会话开始事件
			Log.i(TAG, "视频呼叫会话开始事件");
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			ControlCenter.deviceVer = deviceVer;
			ControlCenter.getControlCenter().VideoCall_SessionStart(dwUserId,
					dwFlags, dwParam, userStr);
			break;
		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH:// < 挂断（结束）呼叫会话
			Log.i(TAG, " 挂断（结束）呼叫会话");
			ControlCenter.getControlCenter().VideoCall_SessionEnd(dwUserId,
					dwFlags, dwParam, userStr);
			break;
		}
	}

	/**
	 * @param number
	 *            设备号
	 */
	private void updataLink(String number) {
		// 门铃端解除了绑定，更新服务器数据库
		updataServiceNum(number);
	}

	private void updataServiceNum(final String number) {
		Log.i(TAG, "number:" + number);
		new Thread(new Runnable() {
			public void run() {
				Message msg = Message.obtain();
				Map<String, String> params = new HashMap<String, String>();
				params.put("device", number);
				String status = Utils.sendPostResquest(HallActivity.this,
						Utils.REMOVEDOORPATH, params, Utils.ENCORDING);
				Log.i(TAG, "status =" + status);
				if (status != null){
					String result = Utils.praseJson(status);
					if (!result.equals("success")) {
						// 解除绑定失败
						msg.what = 0;
					} else {
						// 解除绑定成功
						msg.what = 1;
						//更新devicelist数据
						Utils.deleteUnbindDevice(number);
					}
				}else{
					// 解除绑定失败
					msg.what = 0;
				}
				mHandler.sendMessage(msg);
			}
		}).start();
	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(TAG, "msg.what:" + msg.what);
			switch (msg.what) {
			case 1:
				Log.i(TAG, "刷新列表");
//				Utils.deviceList.clear();
				ControlCenter.getControlCenter().getOnlineFriendDatas();
				mUserAdapter = new UserAdapter(HallActivity.this);
				mListView.setAdapter(mUserAdapter);
				break;

			case 0:
				Toast.makeText(HallActivity.this, getString(R.string.result_failure), Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(HallActivity.this, getString(R.string.result_success), Toast.LENGTH_SHORT).show();
				break;
			case 3:
			case 4:
				Intent intent = new Intent();
				intent.setAction(BaseConst.ACTION_BACKSERVICE);
				intent.setPackage(getPackageName());
				HallActivity.this.stopService(intent);
				Utils.isLogin = false;
				finish();
				break;
			}
			
			if (dialog != null) {
				dialog.dismiss();
			}
		}
	};

	@Override
	public void OnAnyChatUserInfoUpdate(int dwUserId, int dwType) {
		// TODO Auto-generated method stub
		// 同步完成服务器中的所有好友数据，可以在此时获取数据
		
		if (dwUserId == 0 && dwType == 0) {
			ControlCenter.getControlCenter().getOnlineFriendDatas();
			if (mUserAdapter != null)
				mUserAdapter.notifyDataSetChanged();
			Log.i("ANYCHAT", "OnAnyChatUserInfoUpdate");
		}
		
		//
		Log.i(TAG, "OnAnyChatUserInfoUpdate  dwUserId="+dwUserId+" dwType="+dwType);
	}

	@Override
	public void OnAnyChatFriendStatus(int dwUserId, int dwStatus) {
		// TODO Auto-generated method stub
		ControlCenter.getControlCenter().getOnlineFriendDatas();
//		ControlCenter.getControlCenter().updateFriendStatus(dwUserId, dwStatus);
		// BussinessCenter.getBussinessCenter().onUserOnlineStatusNotify(dwUserId,dwStatus);
		if (mUserAdapter != null) {
			mUserAdapter.notifyDataSetChanged();
			Log.i(TAG, "OnAnyChatFriendStatus  userid:"+dwUserId+" status:" + dwStatus + ",0:离线,1:上线");
		}
	}

	@Override
	public void OnAnyChatTransFile(int dwUserid, String FileName,
			String TempFilePath, int dwFileLength, int wParam, int lParam,
			int dwTaskId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void OnAnyChatTransBuffer(int dwUserid, byte[] lpBuf, int dwLen) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void OnAnyChatTransBufferEx(int dwUserid, byte[] lpBuf, int dwLen,
			int wparam, int lparam, int taskid) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void OnAnyChatSDKFilterData(byte[] lpBuf, int dwLen) {
		// TODO Auto-generated method stub
		
	}

}
