package com.example.rollupdemo;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.bairuitech.anychat.AnyChatRecordEvent;
import com.bairuitech.anychat.AnyChatTransDataEvent;
import com.bairuitech.anychat.AnyChatUserInfoEvent;
import com.bairuitech.anychat.AnyChatVideoCallEvent;
import com.example.anychat.ConfigEntity;
import com.example.anychat.ConfigHelper;
import com.example.anychat.ControlCenter;
import com.example.anychat.UserItem;
import com.newbrain.weicar.R;

public class VideoActivity extends Activity implements AnyChatBaseEvent,
OnClickListener, OnTouchListener, AnyChatVideoCallEvent,
AnyChatUserInfoEvent, AnyChatRecordEvent, AnyChatTransDataEvent{

	private SurfaceView mSurfaceRemote;
	private TextView mTxtTarget;
	private TextView mTxtInfo;
	private Button btn_endvideo;
	private View view;
	private Dialog dialog;
	private LinearLayout ll_title;
	private ImageView recordLogo = null;
	
	private int time = 0;
	private static boolean isMicMute = true;
	private static boolean isSpeakerMute = false;

	private AnyChatCoreSDK anychat;

	private Timer mTimerCheckAv;
	private Timer timer;
	private Timer mTImerShowVidoTime;
	private TimerTask mTimerTask ,task;
	private ConfigEntity configEntity;
	private Timer force_timer=null;

	boolean bSelfVideoOpened = false;
	boolean bOtherVideoOpened = false;

	boolean isFirstLoad = true;
	public static final int MSG_CHECKAV = 1;
	public static final int MSG_TIMEUPDATE = 2;
	public static final int MSG_VISIBLE = 3;
	public static final int MSG_FORCE_FINISH = 4;//防止无网络时，无法退出界面的问题，设置超时退出
	
	private static final String TAG = "VideoActivity";
	boolean mIsFirst = true;
	float mOriginalLength = 0;
	float mCurrentLength = 0;
	float mCurrentRate = 1;

	int videoAreaWidth = 0;
	int videoAreaHeight = 0;
	static int dwTargetUserId;
	int videoIndex = 0;
	boolean bNormal = true;
	
	private Timer timeOpenDoor=null;
	
	private Button shortcutBtn = null; 
	private ScreenBroadcastReceiver screenBroadcastReceiver = null;
	private boolean isHideTimeUse;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_CHECKAV:
				CheckVideoStatus();
				updateVolume();
				break;
			case MSG_TIMEUPDATE:
				break;
			case MSG_VISIBLE:
				time++;
				Log.i(TAG, "time:"+time);
				if(time == 5){
					ll_title.setVisibility(View.GONE);
					if (timer != null){
						timer.cancel();
						timer = null;
					}
					if (task != null){
						task.cancel();
						task = null;
					}
					time=0;
				}
				break;
			case MSG_FORCE_FINISH:
				forceClose();
				Log.e(TAG, "强制退出视频！");
				break;
			}
		}
	};
	
	public void startForceTimer(){
		if (force_timer == null)
			force_timer = new Timer();
		force_timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.sendEmptyMessage(MSG_FORCE_FINISH);
			}
		}, 3000);
	}
	
	//结束视频，离开房间。
	private void forceClose(){
		speakCameraControl(-1, 0);
		speakCameraControl(dwTargetUserId, 0);
		anychat.LeaveRoom(-1);
		this.finish();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onConfigurationChanged");
		if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			int screenHeight = getWindowManager().getDefaultDisplay()
					.getHeight();
			adjuestVideoNormalSize(screenHeight, screenHeight * 3 / 5);
			isHideTimeUse = false;
		} else {
			int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
			int screenHeight = getWindowManager().getDefaultDisplay()
					.getHeight();
			adjuestVideoNormalSize(screenWidth, screenHeight);
			isHideTimeUse = true;
		}
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		AudioManager manager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int voice = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (voice == manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)){
        	manager.setStreamVolume(AudioManager.STREAM_MUSIC, voice-2, 0);
        }

		initSdk();
		
		String version = getIntent().getStringExtra("DeviceVer");
		
		if (version!=null && version.compareTo("null")!=0 && version.compareTo("1.0.1")>0){
			isMicMute = true;
		}else{
			isMicMute = false;
		}

		if (ControlCenter.sessionItem != null){
			dwTargetUserId = ControlCenter.sessionItem
					.getPeerUserItem(ControlCenter.selfUserId);
			
			anychat.EnterRoom(ControlCenter.sessionItem.roomId, "");
		}else{
			
		}
		initView();
		
		initTimerCheckAv();
		initTimerShowTime();
		
		screenBroadcastReceiver = new ScreenBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
		registerReceiver(screenBroadcastReceiver, intentFilter);
		Log.i(TAG, "onCreate");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onRestart");
		mHandler.sendEmptyMessage(MSG_FORCE_FINISH);
		super.onRestart();
//		speakCameraControl(-1, 1);
//		speakCameraControl(dwTargetUserId, 1);
//		if (AnyChatCoreSDK
//				.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
//			videoIndex = anychat.mVideoHelper.bindVideo(mSurfaceRemote
//					.getHolder());
//			anychat.mVideoHelper.SetVideoUser(videoIndex, dwTargetUserId);
//		}
		
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onResume");
		super.onResume();
		ControlCenter.mContext = this;
		if (ControlCenter.sessionItem == null){
			mHandler.sendEmptyMessage(MSG_FORCE_FINISH);
		}
	}
	
	@Override
		protected void onPause() {
			// TODO Auto-generated method stub
		Log.i(TAG, "onPause");
			super.onPause();
		}
	
	public static void forceFinishVideoCall(){
		Log.i(TAG, "forceFinishVideoCall....");
		ControlCenter.VideoCallContrl(
				AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH, dwTargetUserId, 0,
				0, ControlCenter.selfUserId, "");
		ControlCenter.sessionItem = null;
	}
	

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStop");
		super.onStop();
		Log.i(TAG, "onStop");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(TAG, "onDestroy");
		mTimerCheckAv.cancel();
		anychat.LeaveRoom(-1);
		speakCameraControl(-1, 0);
		mTImerShowVidoTime.cancel();
		if (force_timer != null){
			force_timer.cancel();
			force_timer=null;
		}
		if (dialog != null && dialog.isShowing())
			dialog.dismiss();
		if (screenBroadcastReceiver != null){
			unregisterReceiver(screenBroadcastReceiver);
		}
	}

	private void speakCameraControl(int user, int type) {
		if (user == -1){
			//不需要打开本地摄像头，只需要打开speaker
			anychat.UserSpeakControl(user, type);
			anychat.UserCameraControl(user, 0);
		}else{
			anychat.UserSpeakControl(user, type);
			anychat.UserCameraControl(user, type);
		}
	}

	private void initSdk() {
		if (anychat == null)
			anychat = new AnyChatCoreSDK();
		anychat.SetBaseEvent(this);
		anychat.SetVideoCallEvent(this);
		anychat.SetUserInfoEvent(this);
		anychat.SetRecordSnapShotEvent(this);
		anychat.mSensorHelper.InitSensor(this);	
		anychat.SetTransDataEvent(this);
		// 初始化Camera上下文句柄
		//AnyChatCoreSDK.mCameraHelper.SetContext(this);
	}

	private void initTimerShowTime() {
		if (mTImerShowVidoTime == null)
			mTImerShowVidoTime = new Timer();
		mTimerTask = new TimerTask() {
			public void run() {
				mHandler.sendEmptyMessage(MSG_TIMEUPDATE);
			}
		};
		mTImerShowVidoTime.schedule(mTimerTask, 100, 1000);
	}

	private void initTimerCheckAv() {
		if (mTimerCheckAv == null)
			mTimerCheckAv = new Timer();
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				mHandler.sendEmptyMessage(MSG_CHECKAV);
			}
		};
		mTimerCheckAv.schedule(mTimerTask, 1000, 100);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_ENDCALL,
					dwTargetUserId, this);
			dialog.show();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initView() {
		// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setContentView(R.layout.activity_video);
		
//		if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){  
//		     // 横屏 
//			this.setContentView(R.layout.video_activity);
//
//		} else if (this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){  
//		     // 竖屏 
//			this.setContentView(R.layout.video_activity2);
//		 
//		}
		String version = getIntent().getStringExtra("DeviceVer");

//		mSurfaceSelf = (SurfaceView) findViewById(R.id.surface_local);
		mSurfaceRemote = (SurfaceView) findViewById(R.id.surface_remote);
		//mProgressSelf = (ProgressBar) findViewById(R.id.progress_local);
		//mProgressRemote = (ProgressBar) findViewById(R.id.progress_remote);
		//mTxtTime = (TextView) findViewById(R.id.txt_time);
		mTxtTarget = (TextView) findViewById(R.id.txt_target);
		mTxtInfo = (TextView) findViewById(R.id.textview_video_2);
//		mBtnEndSession = (Button) findViewById(R.id.btn_endsession);
		btn_endvideo = (Button) findViewById(R.id.btn_endvideo);
		btn_endvideo.setOnClickListener(this);
//		mBtnEndSession.setOnClickListener(this);
		ll_title = (LinearLayout) findViewById(R.id.ll_title);
		recordLogo = (ImageView) findViewById(R.id.textview_recordimage);
		
		
		
		UserItem item = ControlCenter.getControlCenter().getUserItemByUserId(
				dwTargetUserId);
		if (item != null)
			mTxtTarget.setText(item.getUserName());
		mSurfaceRemote.setTag(dwTargetUserId);
		configEntity = ConfigHelper.getConfigHelper().LoadConfig(this);

		// 如果是采用Java视频显示，则需要设置Surface的CallBack
		if (AnyChatCoreSDK
				.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
			videoIndex = anychat.mVideoHelper.bindVideo(mSurfaceRemote
					.getHolder());
			anychat.mVideoHelper.SetVideoUser(videoIndex, dwTargetUserId);
		}
		view = (View) findViewById(R.id.video_session);
		// 得到xml布局中视频区域的大小
		view.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						if (isFirstLoad) {
							isFirstLoad = false;
							videoAreaWidth = view.getWidth();
							videoAreaHeight = view.getHeight();
							adjuestVideoNormalSize(videoAreaWidth,
									videoAreaHeight);
						}
					}
				});

		// 判断是否显示本地摄像头切换图标
		if (AnyChatCoreSDK
				.GetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER) == AnyChatDefine.VIDEOCAP_DRIVER_JAVA) {
			if (AnyChatCoreSDK.mCameraHelper.GetCameraNumber() > 1) {
				// 默认打开前置后置摄像头
				AnyChatCoreSDK.mCameraHelper
						.SelectVideoCapture(AnyChatCoreSDK.mCameraHelper.CAMERA_FACING_BACK);
			}
		} else {
			String[] strVideoCaptures = anychat.EnumVideoCapture();
			if (strVideoCaptures != null && strVideoCaptures.length > 1) {
//				mImgSwitch.setVisibility(View.VISIBLE);
				// 默认打开前置摄像头
				for (int i = 0; i < strVideoCaptures.length; i++) {
					String strDevices = strVideoCaptures[i];
					if (strDevices.indexOf("Front") >= 0) {
						anychat.SelectVideoCapture(strDevices);
						break;
					}
				}
			}
		}
		
		shortcutBtn = (Button) this.findViewById(R.id.btn_shortcut);
		shortcutBtn.setOnClickListener(this);
		if(this.getResources().getConfiguration().orientation ==Configuration.ORIENTATION_PORTRAIT){ 
			isHideTimeUse = false;
		}else{
			isHideTimeUse = true;
		}
		showTitle();
	}

	/***
	 * 调整视频的位置和大小，使本地和远程左右居中显示。
	 * 
	 * @param width
	 * @param height
	 */
	public void adjuestVideoNormalSize(int width, int height) {
		int dwRemoteVideoHeight = 0;
		int dwRemoteVideoWidth = 0;
		if (3.0 * width > 4 * height) {
			dwRemoteVideoHeight = height;
			dwRemoteVideoWidth = (int) (4.0 / 3.0 * dwRemoteVideoHeight);
		} else {
			dwRemoteVideoWidth = (int) (width);
			dwRemoteVideoHeight = (int) (3.0 / 4.0 * dwRemoteVideoWidth);
		}
		RelativeLayout.LayoutParams layoutVideoArea = (android.widget.RelativeLayout.LayoutParams) view
				.getLayoutParams();
		// 视频区域参数
		layoutVideoArea.width = width;
		layoutVideoArea.height = height;
		layoutVideoArea.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
				RelativeLayout.TRUE);
		view.setLayoutParams(layoutVideoArea);
		// 设置远程视频位置
		RelativeLayout.LayoutParams layoutParamOther = (android.widget.RelativeLayout.LayoutParams) mSurfaceRemote
				.getLayoutParams();
		layoutParamOther.width = dwRemoteVideoWidth;
		layoutParamOther.height = dwRemoteVideoHeight;
		mSurfaceRemote.setLayoutParams(layoutParamOther);
	}

	// 判断视频是否已打开
	private void CheckVideoStatus() {
		if (!bOtherVideoOpened) {
			// 摄像头的状态：0，没有摄像头；1,有摄像头但没打开；2，摄像头已打开
			int camerastate = anychat.GetCameraState(dwTargetUserId);
			int myselfcamer = anychat.GetCameraState(-1);
			int videowidth = anychat.GetUserVideoWidth(dwTargetUserId);
			if (camerastate == 0) {// camerastate == 0 没有摄像头
				Log.d(TAG, "没有找到摄像头");
			} else if (camerastate == 2 && videowidth != 0) {
				/*
				 * camerastate == 2 摄像头打开 videowidth == 0 打开视频设备失败
				 */
				SurfaceHolder holder = mSurfaceRemote.getHolder();
				// 如果是采用内核视频显示（非Java驱动），则需要设置Surface的参数
				if (AnyChatCoreSDK
						.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) != AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
					holder.setFormat(PixelFormat.RGB_565);
					holder.setFixedSize(anychat.GetUserVideoWidth(-1),
							anychat.GetUserVideoHeight(-1));
				}
				Surface s = holder.getSurface();
				if (AnyChatCoreSDK
						.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) == AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
					anychat.mVideoHelper.SetVideoUser(videoIndex,
							dwTargetUserId);
				} else {
					anychat.SetVideoPos(dwTargetUserId, s, 0, 0, 0, 0);
				}
				bOtherVideoOpened = true;
			}
		} else {
			Log.i(TAG, "bOtherVideoOpened:" + bOtherVideoOpened);
		}
		if (!bSelfVideoOpened) {
//			if (anychat.GetCameraState(-1) == 2
//					&& anychat.GetUserVideoWidth(-1) != 0) {
//				SurfaceHolder holder = mSurfaceSelf.getHolder();
//				if (AnyChatCoreSDK
//						.GetSDKOptionInt(AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL) != AnyChatDefine.VIDEOSHOW_DRIVER_JAVA) {
//					holder.setFormat(PixelFormat.RGB_565);
//					holder.setFixedSize(anychat.GetUserVideoWidth(-1),
//							anychat.GetUserVideoHeight(-1));
//				}
//				Surface s = holder.getSurface();
//				anychat.SetVideoPos(-1, s, 0, 0, 0, 0);
//				bSelfVideoOpened = true;
//			}
		}
	}

	private void updateVolume() {
		//mProgressSelf.setProgress(anychat.GetUserSpeakVolume(-1));
		//mProgressRemote.setProgress(anychat.GetUserSpeakVolume(dwTargetUserId));
	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		// TODO Auto-generated method stub
		if (dialog != null
				&& dialog.isShowing()
				&& DialogFactory.getCurrentDialogId() == DialogFactory.DIALOGID_RESUME) {
			dialog.dismiss();
		}
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		// TODO Auto-generated method stub
	//	if (dwErrorCode == 0) {
	//		ControlCenter.selfUserId = dwUserId;
	//		ControlCenter.selfUserName = anychat.GetUserName(dwUserId);
	//	}
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub
		if (dwErrorCode == 0) {
			if (isMicMute == true){
				speakCameraControl(-1, 0);
				Log.i(TAG, "MIC 静音");
			}else{
				speakCameraControl(-1, 1);
				Log.i(TAG, "MIC 打开");
			}		
			bSelfVideoOpened = false;
		}
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub
		if (isSpeakerMute == true){
			anychat.UserSpeakControl(dwTargetUserId, 0);
			anychat.UserCameraControl(dwTargetUserId, 1);
		}else{
			speakCameraControl(dwTargetUserId, 1);
		}
		bOtherVideoOpened = false;
	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub
		if (isSpeakerMute==true){
			anychat.UserSpeakControl(dwTargetUserId, 0);
			anychat.UserCameraControl(dwTargetUserId, 1);
		}else{
			speakCameraControl(dwTargetUserId, 1);
		}
		
		bOtherVideoOpened = false;
	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// TODO Auto-generated method stub
		if (dwErrorCode == 0) {
			finish();
		}
	}

	private AlphaAnimation alphaAnimation1=null;
	private boolean isRecord = false;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_endvideo:
			Log.i(TAG, "退出视频");
			dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_ENDCALL,
					dwTargetUserId, this);
			dialog.show();
			break;
		
		case R.id.btn_shortcut:
			if (!isRecord){
				shortcutBtn.setText(R.string.string_end_record);
				AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_RECORD_FILETYPE, 0);
				if (Utils.getSDCardPath()!=null){
					anychat.SetSDKOptionString(AnyChatDefine.BRAC_SO_RECORD_TMPDIR, Utils.getSDCardPath()+Environment.DIRECTORY_DCIM);
				}
				int dwFlags = AnyChatDefine.ANYCHAT_RECORD_FLAGS_VIDEO + AnyChatDefine.ANYCHAT_RECORD_FLAGS_AUDIO;
				anychat.StreamRecordCtrlEx(dwTargetUserId, 1,  dwFlags, 0, "");
				mTxtInfo.setText(getString(R.string.string_recording));
				isRecord = true;
				recordLogo.setVisibility(View.VISIBLE);
				if (alphaAnimation1 == null){
					alphaAnimation1 = new AlphaAnimation(0.1f, 1.0f);  
					alphaAnimation1.setDuration(500);  
					alphaAnimation1.setRepeatCount(Animation.INFINITE);  
					alphaAnimation1.setRepeatMode(Animation.REVERSE);  
				}
				recordLogo.setAnimation(alphaAnimation1); 
				alphaAnimation1.start(); 
			}else{
				shortcutBtn.setText(R.string.string_start_record);
//				int dwFlags = AnyChatDefine.ANYCHAT_RECORD_FLAGS_VIDEO + AnyChatDefine.ANYCHAT_RECORD_FLAGS_AUDIO;
				anychat.StreamRecordCtrlEx(dwTargetUserId, 0, 0, 0, "");
				mTxtInfo.setText(getString(R.string.string_videoing));
				isRecord = false;
				alphaAnimation1.cancel();
				recordLogo.clearAnimation();
				recordLogo.setVisibility(View.GONE);
			}
			break;
		}
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i(TAG, "VIDEO-TOUCH");
		float dwFirstPOS = 0;
		float dweEndPOS = 0;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dwFirstPOS = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if (event.getPointerCount() == 2) {
				if (mIsFirst) {
					mOriginalLength = (float) Math.sqrt(Math.pow(event.getX(0)
							- event.getX(1), 2)
							+ Math.pow(event.getY(0) - event.getY(1), 2));
					mIsFirst = false;
				} else {
					mCurrentLength = (float) Math.sqrt(Math.pow(event.getX(0)
							- event.getX(1), 2)
							+ Math.pow(event.getY(0) - event.getY(1), 2));
					mCurrentRate = (mCurrentLength / mOriginalLength);
				}
				Log.i(TAG, "getPointerCount");
			}
			Log.i(TAG, "ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			if (mCurrentRate > 1) {
				Log.i(TAG, "SCALE");
			} else if (mCurrentRate < 1) {
				adjuestVideoNormalSize(videoAreaWidth, videoAreaHeight);
				Log.i(TAG, "SOUXIAO");
			} else {
				dweEndPOS = event.getX();
				float dis = dweEndPOS - dwFirstPOS;
				if (dis > 10) {
					Log.i(TAG, "dis-->" + dis);
				} else if (dis < -10) {
					adjuestVideoNormalSize(videoAreaWidth, videoAreaHeight);
				}
				dweEndPOS = 0;
				Log.i(TAG, "DIS" + dis);
			}

			mCurrentRate = 1;
			Log.i(TAG, "ACTION_UP");
			mIsFirst = true;
			break;
		}
		return true;
	}

	@Override
	public void OnAnyChatVideoCallEvent(int dwEventType, int dwUserId,
			int dwErrorCode, int dwFlags, int dwParam, String userStr) {
		// TODO Auto-generated method stub
		if (dwEventType == AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH) {
			if (force_timer != null){
				force_timer.cancel();
				force_timer=null;
			}
			speakCameraControl(-1, 0);
			speakCameraControl(dwTargetUserId, 0);
			anychat.LeaveRoom(-1);
			this.finish();
		}
	}

	@Override
	public void OnAnyChatUserInfoUpdate(int dwUserId, int dwType) {
		// TODO Auto-generated method stub
//		if (dwUserId == 0 && dwType == 0) {
//			ControlCenter.getControlCenter().getOnlineFriendDatas();
//		}
	}

	@Override
	public void OnAnyChatFriendStatus(int dwUserId, int dwStatus) {
		// TODO Auto-generated method stub
		// 有人上线，下线提示方法。
		Log.i(TAG, "OnAnyChatFriendStatus");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN://触摸屏幕
			Log.i(TAG, "点击屏幕");
			
			break;

		case MotionEvent.ACTION_UP://终止触摸时刻
			Log.i(TAG, "离开屏幕");
			showTitle();
			break;
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 显示隐藏的退出等按钮
	 */
	private void showTitle(){
		// 1.显示条目
		ll_title.setVisibility(View.VISIBLE);
		//2.三秒钟之后自动隐藏
		if (isHideTimeUse){
			if(task==null){
				task = new TimerTask() {
					@Override
					public void run() {
						Message msg = new Message();
						msg.what =3;
						mHandler.sendMessage(msg);
					}
				};
			}
			if(timer==null){
				timer = new Timer();
				timer.schedule(task, 1, 1000);
			}
		}
	}
	
	private class ScreenBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			forceFinishVideoCall();
		}
		
	}

	@Override
	public void OnAnyChatRecordEvent(int dwUserId, String lpFileName,
			int dwElapse, int dwFlags, int dwParam, String lpUserStr) {
		// TODO Auto-generated method stub
		Log.i(TAG, "RecordFileName:" + lpFileName);
		Uri data = Uri.parse("file://"+lpFileName);       
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));       

	}

	@Override
	public void OnAnyChatSnapShotEvent(int dwUserId, String lpFileName,
			int dwFlags, int dwParam, String lpUserStr) {
		// TODO Auto-generated method stub
		
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
		Log.i(TAG, "OnAnyChatTransBuffer");
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
