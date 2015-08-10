package com.example.rollupdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bairuitech.anychat.AnyChatDefine;
import com.example.anychat.ConfigEntity;
import com.example.anychat.ControlCenter;
import com.example.anychat.UserItem;
import com.example.anychat.VideoCallContrlHandler;
import com.newbrain.weicar.R;

public class DialogFactory {
	private Activity mContext;
	private static Dialog mDialog;
	private LayoutInflater mLayoutInlater;
	private TextView mTextViewTitle;
	private EditText mEditIP;
	private EditText mEditPort;
	private ConfigEntity configEntity;

	public static int mCurrentDialogId = 0;

	public static final int DIALOGID_CALLING = 1;//电话
	public static final int DIALOGID_REQUEST = 2;
	public static final int DIALOGID_RESUME = 3;
	public static final int DIALOGID_CALLRESUME = 4;
	public static final int DIALOGID_ENDCALL = 5;
	public static final int DIALOGID_EXIT = 6;
	public static final int DIALOGID_CONFIG = 7;
	public static final int DIALOGID_MEETING_INVITE = 8;
	public static final int DIALOG_SERCLOSE = 1;
	public static final int DIALOG_AGAINLOGIN = 2;
	public static final int DIALOG_NETCLOSE = 3;
	protected static final String TAG = "DialogFactory";
	public static DialogFactory mDialogFactory = new DialogFactory();

	private DialogFactory() {
	}

	public static DialogFactory getDialogFactory() {
		if (mDialogFactory == null) {
			mDialogFactory = new DialogFactory();
		}
		return mDialogFactory;
	}

	/**
	 * 获取指定类型的对话框实例
	 * 
	 * @param dwDialogId
	 *            对话框类型
	 * @param object
	 *            对话框数据
	 * @param context
	 *            对话框位于的上下文
	 * @return 对话框实例
	 */
	public static Dialog getDialog(int dwDialogId, Object object,
			Activity context) {
		mDialogFactory.initDialog(dwDialogId, object, context);
		return mDialog;
	}

	public static int getCurrentDialogId() {
		return mCurrentDialogId;
	}

	public void initDialog(int dwDialogId, Object object, Activity context) {
		if (mContext != context) {
			mContext = context;
			mLayoutInlater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		mCurrentDialogId = dwDialogId;
		mDialog = new Dialog(mContext,R.style.CommonDialog);
		mDialog.setCanceledOnTouchOutside(false);
//		mDialog.setCancelable(false);
		switch (dwDialogId) {
		case DIALOGID_CALLING:
			initCallingDialog(mDialog, object);
			break;
		case DIALOGID_CALLRESUME:
			initCallResume(mDialog, object);
			break;
		case DIALOGID_ENDCALL:
			initEndSessionResumeDialg(mDialog, object);
			break;
		case DIALOGID_EXIT:
			initQuitResumeDialg(mDialog);
			break;
		case DIALOGID_REQUEST:
			initCallReceivedDialg(mDialog, object);
			mDialog.setCancelable(false);
			break;
		case DIALOGID_RESUME:
			initResumeDialg(mDialog, object);
			break;
		case DIALOGID_CONFIG:
			initConfigDialog(mDialog, object);
			break;
		}

	}

	public static void releaseDialog() {
		mCurrentDialogId = 0;
		mDialog = null;
		mDialogFactory = null;
	}
	
	// 初始化设置对话框
	public void initConfigDialog(final Dialog dialog, final Object object) {
//		configEntity = (ConfigEntity) object;
//		View view = mLayoutInlater.inflate(
//				R.layout.dialog_config, null);
//		mEditPort = (EditText) view.findViewById(R.id.edit_serverport);
//		mEditPort.setText(configEntity.port + "");
//		mEditIP = (EditText) view.findViewById(R.id.edit_serverip);
//		mEditIP.setText(configEntity.ip);
//		ImageView imageView = (ImageView) view.findViewById(R.id.image_cancel);
//		Button buttonR = (Button) view.findViewById(R.id.btn_resume);
//		buttonR.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				final String strServerIP = mEditIP.getText().toString();
//				final String strPort = mEditPort.getText().toString();
//				String strMessage = mContext
//						.getString(R.string.str_serveripinput);
//				if (strServerIP.length() == 0) {
//					strMessage = mContext.getString(R.string.str_serveripinput);
//					BaseMethod.showToast(strMessage, mContext);
//					return;
//				}
//				if (strPort.length() == 0) {
//					strMessage = mContext
//							.getString(R.string.str_serverportinput);
//					BaseMethod.showToast(strMessage, mContext);
//					return;
//				}
//				configEntity.ip = strServerIP;
//				configEntity.port = Integer.valueOf(strPort);
//				ConfigHelper.getConfigHelper().SaveConfig(mContext,
//						configEntity);
//				dialog.dismiss();
//
//			}
//		});
//		Button buttonC = (Button) view.findViewById(R.id.btn_cancel);
//		buttonC.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dialog.dismiss();
//			}
//		});
//		imageView.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dialog.dismiss();
//			}
//		});
//		dialog.setContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT));
	}

	/***
	 * 初始化呼叫对话框
	 * 
	 * @param dialog
	 * @param object
	 */
	public void initCallingDialog(final Dialog dialog, Object object) {
		final int userId = (Integer) object;
		View view = mLayoutInlater.inflate(R.layout.dialog_calling, null);
		Button buttonCancel = (Button) view.findViewById(R.id.btn_cancel);
		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i(TAG, "取消呼叫");
				ControlCenter
						.VideoCallContrl(
								AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY,
								userId,
								VideoCallContrlHandler.ERRORCODE_SESSION_QUIT,
								0, 0, "");
				dialog.dismiss();
			}
		});
		String strTitle = mContext.getString(R.string.str_calling);
		initDialogTitle(view, strTitle, userId);
		dialog.setContentView(view);
	}

	/***
	 * 初始化呼叫确认对话框
	 * 
	 * @param dialog
	 * @param object
	 */
	public void initCallResume(final Dialog dialog, Object object) {
		final int userId = (Integer) object;
		View view = mLayoutInlater.inflate(R.layout.dialog_call_resume, null);
		Button btnCall = (Button) view.findViewById(R.id.btn_call);
		btnCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generate)d method stub
				ControlCenter.VideoCallContrl(
						AnyChatDefine.BRAC_VIDEOCALL_EVENT_REQUEST, userId, 0,
						0, 0, "");
				mDialog.dismiss();
			}
		});
		String strTitle = "";
		UserItem userItem = ControlCenter.getControlCenter()
				.getUserItemByUserId(userId);
		if (userItem != null)
			strTitle = mContext.getString(R.string.ready_connect_string)+"\n" + userItem.getUserName() + " "+mContext.getString(R.string.ihome_device);
		initDialogTitle(view, strTitle);
		dialog.setContentView(view);
	}

	/***
	 * 初始化确认对话框
	 * @param dialog
	 * @param object
	 */
	public void initResumeDialg(final Dialog dialog, final Object object) {
		final int dwTag = (Integer) object;
		View view = mLayoutInlater.inflate(R.layout.dialog_resume, null);
		Button buttonResume = (Button) view.findViewById(R.id.btn_resume);
		buttonResume.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent;
				switch (dwTag) {
				case DIALOG_AGAINLOGIN:
//					mContext.stopService(new Intent(
//							BaseConst.ACTION_BACKSERVICE));
					intent = new Intent();
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.setClass(mContext, LoginActivity.class);
					mContext.startActivity(intent);
					break;
				case DIALOG_SERCLOSE:
//					mContext.stopService(new Intent(
//							BaseConst.ACTION_BACKSERVICE));
					intent = new Intent();
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.setClass(mContext, LoginActivity.class);
					mContext.startActivity(intent);
					break;
				case DIALOG_NETCLOSE:
					Toast.makeText(mContext, "WNNMLGB", Toast.LENGTH_LONG).show();
					Intent intentSetting = new Intent();
					intentSetting
							.setAction(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					mContext.startActivity(intentSetting);
					break;

				}
				dialog.dismiss();
			}
		});

		String strTitle = "";
		switch (dwTag) {
		case DIALOG_AGAINLOGIN:
			strTitle = mContext.getString(R.string.str_againlogin);
			break;
		case DIALOG_SERCLOSE:
			strTitle = mContext.getString(R.string.str_servercolse);
			break;
		case DIALOG_NETCLOSE:
			strTitle = mContext.getString(R.string.str_networkcheck);
			break;
		}
		initDialogTitle(view, strTitle);
		dialog.setContentView(view);
	}

	/***
	 * 初始化接收到呼叫请求对话框
	 * 
	 * @param dialog
	 * @param object
	 */
	public void initCallReceivedDialg(final Dialog dialog, final Object object) {
		final int userId = (Integer) object;
		View view = mLayoutInlater.inflate(R.layout.dialog_requesting, null);
		ImageView buttonAccept = (ImageView) view.findViewById(R.id.btn_accept);
		ImageView buttonRefuse = (ImageView) view.findViewById(R.id.btn_refuse);
		// buttonAccept.setText(mContext.getString(R.string.str_resume));
		buttonAccept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generate)d method stub
				ControlCenter.VideoCallContrl(
						AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY, userId,
						VideoCallContrlHandler.ERRORCODE_SUCCESS, 0, 0, "");
				dialog.dismiss();

			}

		});
		buttonRefuse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ControlCenter.VideoCallContrl(
						AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY, userId,
						VideoCallContrlHandler.ERRORCODE_SESSION_REFUSE, 0, 0,
						"");
				dialog.dismiss();
				ControlCenter.sessionItem = null;
				ControlCenter.getControlCenter().stopSessionMis();

			}
		});
		UserItem userItem = ControlCenter.getControlCenter()
				.getUserItemByUserId(userId);
		String strTitle = "";
		if (userItem != null)
			strTitle = userItem.getUserName()
					+ mContext.getString(R.string.sessioning_reqite);

		initDialogTitle(view, strTitle, userId);
		dialog.setContentView(view);
	}

	/***
	 * 初始化退出程序对话框
	 * 
	 * @param dialog
	 */
	public void initQuitResumeDialg(final Dialog dialog) {
		View view = mLayoutInlater
				.inflate(R.layout.dialog_resumeorcancel, null);
		Button buttonQuit = (Button) view.findViewById(R.id.btn_resume);
		Button buttonCancel = (Button) view.findViewById(R.id.btn_cancel);
		buttonQuit.setText(mContext.getString(android.R.string.ok));
		buttonCancel.setText(mContext.getString(R.string.cancle));
		buttonQuit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generate)d method stub
//				Intent intent = new Intent();
//				intent.setAction(BaseConst.ACTION_BACKSERVICE);
//				mContext.stopService(intent);
				new ExitThread().start();
				android.os.Process.killProcess(android.os.Process.myPid());
//				intent = new Intent();
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				intent.setClass(mContext, LoginActivity.class);
//				mContext.startActivity(intent);
				
				dialog.dismiss();

			}
		});
		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		String strTitle = mContext.getString(R.string.str_exitresume);
		initDialogTitle(view, strTitle);
		dialog.setContentView(view);
	}
	class ExitThread extends Thread {
		@Override
		public void run() {
			super.run();
			try {
				String str = Utils.sendPostResquest(mContext,
						Utils.EXITPATH, null, Utils.ENCORDING);
				String status = Utils.praseJson(str);
				Log.i("TAG", "视频退出 status ："+status);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/***
	 * 初始化通话结束确认对话框
	 * 
	 * @param dialog
	 * @param object
	 */
	public void initEndSessionResumeDialg(final Dialog dialog,
			final Object object) {
		final int userId = (Integer) object;
		View view = mLayoutInlater
				.inflate(R.layout.dialog_resumeorcancel, null);
		Button buttonPuase = (Button) view.findViewById(R.id.btn_resume);
		Button buttonCancel = (Button) view.findViewById(R.id.btn_cancel);
		buttonPuase.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "点击 确定  退出视频");
				ControlCenter.VideoCallContrl(
						AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH, userId, 0,
						0, ControlCenter.selfUserId, "");
				if (mContext instanceof VideoActivity){
					((VideoActivity) mContext).startForceTimer();
				}
			}
		});
		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "点击 取消  退出视频");
				dialog.dismiss();
			}
		});
		String strTitle = mContext.getString(R.string.str_endsession);
		initDialogTitle(view, strTitle);
		dialog.setContentView(view);
	}

	private void initDialogTitle(View view, final String strTitle) {
		ImageView imageView = (ImageView) view.findViewById(R.id.image_cancel);
		mTextViewTitle = (TextView) view.findViewById(R.id.txt_dialog_prompt);
//		mTextViewTitle.setTextColor(Color.BLUE);
		mTextViewTitle.setTextSize(20);
		if (imageView!=null){
			imageView.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (mCurrentDialogId == DIALOGID_CALLING) {
					} else if (mCurrentDialogId == DIALOGID_REQUEST) {
					}
					mDialog.dismiss();
				}
			});
		}
		mTextViewTitle.setText(strTitle);
	}

	private void initDialogTitle(View view, final String strTitle,
			final int userId) {
		ImageView imageView = (ImageView) view.findViewById(R.id.image_cancel);
		mTextViewTitle = (TextView) view.findViewById(R.id.txt_dialog_prompt);
//		mTextViewTitle.setTextColor(Color.BLUE);
		mTextViewTitle.setTextSize(20);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCurrentDialogId == DIALOGID_CALLING) {
					ControlCenter.VideoCallContrl(
							AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY, userId,
							VideoCallContrlHandler.ERRORCODE_SESSION_QUIT, 0,
							0, "");
				} else if (mCurrentDialogId == DIALOGID_REQUEST) {
					ControlCenter.VideoCallContrl(
							AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY, userId,
							VideoCallContrlHandler.ERRORCODE_SESSION_REFUSE, 0,
							0, "");
					ControlCenter.sessionItem = null;
					ControlCenter.getControlCenter().stopSessionMis();
				}
				mDialog.dismiss();
			}
		});
		mTextViewTitle.setText(strTitle);
	}

}
