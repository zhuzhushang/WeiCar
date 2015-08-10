package com.example.rollupdemo;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.anychat.BaseConst;
import com.example.anychat.ControlCenter;
import com.newbrain.weicar.R;

public class BackService extends Service {
	private final String TAG = "BackService";
	private ActivityManager activityManager;
	List<RunningAppProcessInfo> appProcesses;
	private String packageName;
	private boolean bStop = false;
	private boolean bFirstShow = true;
	public final static int BACK_NOTIFICATION_APP = 0x457893;
	public final static int BACK_NOTIFICATIONID_BASE = 0x888;
	public RequestSdkBroadCast mBroadCastRecevier;
	public static boolean isBackRunning = false;

	class RequestSdkBroadCast extends BroadcastReceiver {
 
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(BaseConst.ACTION_BACK_CANCELSESSION)) {
				Bundle bundle = intent.getExtras();
				if (bundle != null) {
					int userId = bundle.getInt("USERID");
					cancelNotification(BACK_NOTIFICATIONID_BASE+userId);
				}
			}
			if (intent.getAction().equals(BaseConst.ACTION_BACK_EQUESTSESSION)) {
				Bundle bundle = intent.getExtras();
				if (bundle != null) {

					int userId = bundle.getInt("USERID");
					showNotification(bundle.getString("USERNAME"),
							BACK_NOTIFICATIONID_BASE + userId);
				}
			}
			if (intent.getAction().equals(
					BaseConst.ACTION_BACK_CANCELNOTIFYTION)) {
				cancelNotification();
			}

		}

	}
	
	private void registerBroad() {
		mBroadCastRecevier = new RequestSdkBroadCast();
		IntentFilter intentFilter = new IntentFilter(
				BaseConst.ACTION_BACK_EQUESTSESSION);
		this.registerReceiver(mBroadCastRecevier, intentFilter);
		intentFilter = new IntentFilter(BaseConst.ACTION_BACK_CANCELSESSION);
		this.registerReceiver(mBroadCastRecevier, intentFilter);
		intentFilter = new IntentFilter(BaseConst.ACTION_BACK_CANCELNOTIFYTION);
		this.registerReceiver(mBroadCastRecevier, intentFilter);
		this.registerReceiver(mBroadCastRecevier, intentFilter);
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}


	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if(isBackRunning!=true){
			isBackRunning = true;
			activityManager = (ActivityManager) this
					.getSystemService(Context.ACTIVITY_SERVICE);
			packageName = this.getPackageName();
			new Thread() {
				public void run() {
					try {
						while (!bStop) {
							if (isAppOnForeground()) {
								
								if (!bFirstShow) {
									cancelNotification();
									bFirstShow = true;
									ControlCenter.bBack = false;
								}
							} else {
								if (bFirstShow) {
									showNotification(
											BackService.this
													.getString(R.string.back_runing),
											BACK_NOTIFICATION_APP);
									bFirstShow = false;
									ControlCenter.bBack = true;
								}
								
								if (ControlCenter.sessionItem!=null){
									VideoActivity.forceFinishVideoCall();	
								}
								Thread.sleep(1000);
							}
	
							Thread.sleep(1000);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	
		return super.onStartCommand(intent, flags, startId);
	}

	public boolean isAppOnForeground() {
		appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
			return false;
		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "onDestory");
		isBackRunning = false;
		this.unregisterReceiver(mBroadCastRecevier);
		bStop = true;
		cancelNotification();//防止后台运行退出后，通知栏的消息不消失。
		super.onDestroy();
	}

	/***
	 * 显示指定通知
	 * 
	 * @param strTitle
	 *            通知内容
	 * @param notification_id
	 *            通知id
	 */
	public void showNotification(String strText, int notification_id) {
		// 得到NotificationManager
		Log.i(TAG, "showNotification");
		NotificationManager notificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher,
				strText, System.currentTimeMillis());
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;// FLAG_NO_CLEAR
		notification.defaults = Notification.DEFAULT_LIGHTS;
		notification.ledARGB = Color.BLUE;
		notification.ledOnMS = 100;
		notification.ledOffMS = 100;
		Intent notificationIntent = new Intent(ControlCenter.mContext,
				ControlCenter.mContext.getClass());
		notificationIntent.putExtra("action", 2);
		notificationIntent.setAction(Intent.ACTION_MAIN);
		notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent contentIntent = PendingIntent.getActivity(
				ControlCenter.mContext, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification
				.setLatestEventInfo(this,
						this.getString(R.string.back_runing), strText,
						contentIntent);
		notificationManager.notify(notification_id, notification);
	}

	public void cancelNotification(int notificationId) {

		try {
			NotificationManager notificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
			notificationManager.cancel(notificationId);
		} catch (Exception e) {

		}
	}
	public void cancelNotification() {

		try {
			NotificationManager notificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
			notificationManager.cancelAll();
		} catch (Exception e) {
			 
		}
	}

}
