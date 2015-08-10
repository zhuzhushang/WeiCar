package com.newbrain.weicar.gridview;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;
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
import com.newbrain.utils.Util;
import com.newbrain.weicar.MainActivity;
import com.newbrain.weicar.R;
import com.newbrain.xutils.Xutils_HttpUtils;

public class RegisterActivity extends BaseActivity {

	private Context context;

	@ViewInject(R.id.register_viewpager)
	private ViewPager mViewPager;

	@ViewInject(R.id.register_radioGroup)
	private RadioGroup mRadioGroup;

	private List<View> mList;

	private MyPagerAdapter mMyPagerAdapter;

	private LayoutInflater mLayoutInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.register_activity);

		context = this;
		ViewUtils.inject(this);

		mLayoutInflater = LayoutInflater.from(context);

		actionbarInit();

		radioButtonInit();
		viewPagerInit();

	}

	private void viewPagerInit() {
		// TODO Auto-generated method stub

		mRadioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub

						switch (checkedId) {
						case R.id.register_tab1_not_null:

							mViewPager.setCurrentItem(0);

							break;
						case R.id.register_tab2_person_information:

							break;
						case R.id.register_tab3_device_information:

							mViewPager.setCurrentItem(1);

							break;

						default:
							break;
						}

					}
				});

	}

	private MyCount mycuont;

	/** 短信验证码 */
	private void requestHttp_smsVerify() {
		String phone = mEt_register_phone.getText().toString();

		if (!checkPhone(phone)) {

			showShortToast("手机号码不正确！请修改");

			return;
		}
		
		mBtn_verfity_num.setClickable(false);
		
		String url = Constant.BASE_URL
				+ "weiCar/interf/customer/sendMessage?phoneNo=" + phone;
		
		CustomProgressDialog.startProgressDialog(context);

		Xutils_HttpUtils.getHttpUtils(context).send(HttpMethod.GET, url,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						
						mBtn_verfity_num.setClickable(true);
						showShortToast(getString(R.string.verify_num_send_fail));
						
						CustomProgressDialog.stopProgressDialog();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub

						LogUtils.e("------------>" + arg0.result);

						try {
							org.json.JSONObject jsonObject = new org.json.JSONObject(
									arg0.result);

							boolean status = jsonObject.getBoolean("status");

							if (status) {

								mycuont = new MyCount(1000 * 120, 1000 * 1);

								mycuont.start();

								showShortToast(R.string.verify_num_send_success);
								
								
								
								
								
								

							} else {

								showShortToast(getString(R.string.verify_num_send_fail));

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						CustomProgressDialog.stopProgressDialog();

					}
				});

	}

	/* 定义一个倒计时的内部类 */
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			mBtn_verfity_num.setText(getString(R.string.verify_num));
			mBtn_verfity_num.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {

			mBtn_verfity_num.setText("" + (millisUntilFinished / 1000) + "s");

		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		
		if (!mBtn_verfity_num.getText().toString()
				.equals(getString(R.string.verify_num))) {

			mycuont.cancel();

		}
	}

	public boolean checkPhone(String phone) {
		Pattern pattern = Pattern
				.compile("^((13[0-9])|(15[^4,\\d])|(18[0,5-9]))\\d{8}$");
		Matcher matcher = pattern.matcher(phone);

		return matcher.matches();
	}

	/** view1 里面的控件 */
	private Button mBtn_verfity_num;

	private EditText mEt_register_phone;

	private EditText mEt_register_phone_verfity_num;

	private EditText mEt_register_password;

	private EditText mEt_register_password_confirm;

	private String mView1_register_phone, mView1_register_phone_verfity_num,
			mView1_register_password, mView1_register_password_confirm;

	/** view2的控件 */
	
	private EditText mEt_device_information_ac_device_id;


	private EditText mEt_device_information_ac_car_num;

	
	private EditText mEt_device_information_ac_car_recommend;

	
	private EditText mEt_device_information_ac_car_sort;

	
	private EditText mEt_device_information_ac_car_VIN;

	
	private EditText mEt_device_information_ac_car_buy_time;;
	
	private EditText mEt_device_information_ac_car_engine;

	private String mView2_device_information_ac_device_id ,
			mView2_device_information_ac_car_num,
			mView2_device_information_ac_car_recommend,
			mView2_device_information_ac_car_sort,
			mView2_device_information_ac_car_VIN,
			mView2_device_information_ac_car_buy_time,
			mView2_device_information_ac_car_engine = ""
			;

	@SuppressWarnings("deprecation")
	private void radioButtonInit() {
		// TODO Auto-generated method stub

		View view_tab1 = mLayoutInflater.inflate(R.layout.register_not_null,
				null);
		View view_tab2 = mLayoutInflater.inflate(
				R.layout.register_device_information, null);

		initView1(view_tab1);
		initView2(view_tab2);

		mList = new ArrayList<View>();

		mList.add(view_tab1);
		mList.add(view_tab2);

		mMyPagerAdapter = new MyPagerAdapter(context, mList);

		mViewPager.setAdapter(mMyPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int arg0) {
						// TODO Auto-generated method stub

						switch (arg0) {
						case 0:

							mRadioGroup.check(R.id.register_tab1_not_null);

							actionbar_btn_right.setText(NEXT);
							break;

						case 1:

							mRadioGroup
									.check(R.id.register_tab3_device_information);

							actionbar_btn_right.setText(CONFIRM);

							break;

						default:
							break;
						}
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void initView1(View view) {
		// mEt_register_ = (EditText) view.findViewById(R.id.register_);

		mEt_register_phone = (EditText) view.findViewById(R.id.register_phone);
		mEt_register_password_confirm = (EditText) view
				.findViewById(R.id.register_password_confirm);
		mEt_register_password = (EditText) view
				.findViewById(R.id.register_password);
		mEt_register_phone_verfity_num = (EditText) view
				.findViewById(R.id.register_phone_verfity_num);

		mBtn_verfity_num = (Button) view
				.findViewById(R.id.register_verfity_num);

		mBtn_verfity_num.setOnClickListener(onClickListenerView1);

	}

	/** tab2的控件初始化 */
	private void initView2(View view_tab2) {
		mEt_device_information_ac_car_buy_time = (EditText) view_tab2
				.findViewById(R.id.device_information_ac_car_buy_time);
		mEt_device_information_ac_car_num = (EditText) view_tab2
				.findViewById(R.id.device_information_ac_car_num);
		mEt_device_information_ac_car_recommend = (EditText) view_tab2
				.findViewById(R.id.device_information_ac_car_recommend);
		mEt_device_information_ac_car_sort = (EditText) view_tab2
				.findViewById(R.id.device_information_ac_car_sort);
		mEt_device_information_ac_car_VIN = (EditText) view_tab2
				.findViewById(R.id.device_information_ac_car_Vehicle_Identification_Number);
		mEt_device_information_ac_device_id = (EditText) view_tab2
				.findViewById(R.id.device_information_ac_device_id);
		
		
		mEt_device_information_ac_car_engine = (EditText) (EditText) view_tab2
				.findViewById(R.id.device_information_ac_car_engine);
		
	}

	private OnClickListener onClickListenerView1 = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch (v.getId()) {
			case R.id.register_verfity_num:

				requestHttp_smsVerify();

				break;

			default:
				break;
			}

		}
	};

	private final String NEXT = "下一步";

	private final String CONFIRM = "确定";

	public class MyPagerAdapter extends PagerAdapter {

		private Context mContext;
		private List<View> mList;

		public MyPagerAdapter(Context mContext, List<View> mList) {
			super();
			this.mContext = mContext;
			this.mList = mList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			super.destroyItem(container, position, object);

			container.removeView(mList.get(position));

		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub

			container.addView(mList.get(position));

			return mList.get(position);
		}

	}

	/** 定义actionbar相关参数 */

	@ViewInject(R.id.all_actionbar_linear_left)
	private LinearLayout actionbar_ll_left;

	@ViewInject(R.id.all_actionbar_name)
	private TextView actionbar_tv_name;

	@ViewInject(R.id.all_actionbar_textview_back_name)
	private TextView actionbar_tv_back_name;

	@ViewInject(R.id.all_actionbar_button_right)
	private Button actionbar_btn_right;

	/** 初始化actionbar */
	private void actionbarInit() {
		actionbar_tv_back_name.setText(getString(R.string.back));
		actionbar_tv_name.setText(getString(R.string.register));
		actionbar_btn_right.setText(NEXT);

		actionbar_ll_left.setVisibility(View.GONE);

		actionbar_btn_right.setVisibility(View.VISIBLE);

		// actionbar_ll_left.setOnClickListener(l);

	}

	/** 设置actionbar的监听 */
	@OnClick({ R.id.all_actionbar_linear_left, R.id.all_actionbar_button_right })
	private void onClick_actionBar(View view) {

		switch (view.getId()) {
		// 左边的返回
		case R.id.all_actionbar_linear_left:

			finish();

			break;
		// 右边的button
		case R.id.all_actionbar_button_right:

			// 先检查数据是否在缺 在注册界面的时候

			if (actionbar_btn_right.getText().toString().equals(NEXT)) {

				if (checkDataOne()) {

					mViewPager.setCurrentItem(1);

				}

			} else {

				if (checkDataTwo()) {
					
					
					requestHttp_register();

				}

				

			}

			break;

		default:
			break;
		}

	}

	/*
	 * phoneNo String 手机号码 是 password String 密码 是 image String 图片 是 nickName
	 * String 昵称 是 sex Int 性别 否 age int 年龄 否 equipmentNo String 设备号 否 models
	 * String 车型 否 frameNo String 车架号 否 engineNo String 发动机号 否 licensePlate
	 * String 车牌号 否 buyTime String 购买时间 否 recommend String 推荐人 否 sim String
	 * sim卡号 是
	 */

	/** 对注册接口 */
	private void requestHttp_register() {

		List<Bean> list = new ArrayList<Bean>();

		list.add(new Bean("phoneNo", mView1_register_phone));
		list.add(new Bean("password", mView1_register_password));
		list.add(new Bean("image", ""));
		list.add(new Bean("nickName", getString(R.string.no_named)));
		list.add(new Bean("sex", "" + 0));
		list.add(new Bean("age", "22"));
		
		list.add(new Bean("frameNo", mView2_device_information_ac_car_VIN));
		list.add(new Bean("models", mView2_device_information_ac_car_sort));
		list.add(new Bean("equipmentNo", mView2_device_information_ac_device_id));
		list.add(new Bean("engineNo", mView2_device_information_ac_car_engine));
		list.add(new Bean("licensePlate", mView2_device_information_ac_car_num));
		list.add(new Bean("buyTime", mView2_device_information_ac_car_buy_time));
		list.add(new Bean("recommend", mView2_device_information_ac_car_recommend));
		list.add(new Bean("sim", mView1_register_phone));

		JsonThread jsonThread = new JsonThread(context, list, mHandler,
				Constant.FLAG_REGISTER);

		jsonThread.start();
		
		CustomProgressDialog.startProgressDialog(context);

	}

	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			case Constant.FLAG_REGISTER:

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
				
				CustomProgressDialog.stopProgressDialog();
				
				finish();

				break;
				
				

			default:
				break;
			}

		}
	};

	/** 检测注册界面的值 是否为null */
	private boolean checkDataOne() {
		// TODO Auto-generated method stub

		mView1_register_password = mEt_register_password.getText().toString();

		mView1_register_password_confirm = mEt_register_password_confirm
				.getText().toString();

		mView1_register_phone = mEt_register_phone.getText().toString();
		mView1_register_phone_verfity_num = mEt_register_phone_verfity_num
				.getText().toString();
		
		

		if ("".equals(mView1_register_phone)) {

			showShortToast(getString(R.string.phone_num)
					+ getString(R.string.can_not_null));

			return false;
		} else if ("".equals(mView1_register_phone_verfity_num)) {

			showShortToast(getString(R.string.verify_num)
					+ getString(R.string.can_not_null));

			return false;
		} else if ("".equals(mView1_register_password)) {

			showShortToast(getString(R.string.password1)
					+ getString(R.string.can_not_null));

			return false;
		} else if ("".equals(mView1_register_password_confirm)) {

			showShortToast(getString(R.string.confirm_password)
					+ getString(R.string.can_not_null));

			return false;
		} else if (!mView1_register_password
				.equals(mView1_register_password_confirm)) {

			showShortToast(getString(R.string.password_not_same));

			return false;

		}

		return true;
	}

	/** 检测设备号 */
	private boolean checkDataTwo() {

		mView2_device_information_ac_car_buy_time = mEt_device_information_ac_car_buy_time
				.getText().toString();
		mView2_device_information_ac_car_num = mEt_device_information_ac_car_num
				.getText().toString();
		mView2_device_information_ac_car_recommend = mEt_device_information_ac_car_recommend
				.getText().toString();
		mView2_device_information_ac_car_sort = mEt_device_information_ac_car_sort
				.getText().toString();
		mView2_device_information_ac_car_VIN = mEt_device_information_ac_car_VIN
				.getText().toString();
		mView2_device_information_ac_device_id = mEt_device_information_ac_device_id
				.getText().toString();
		
		mView2_device_information_ac_car_engine = mEt_device_information_ac_car_engine.getText().toString();

		if ("".equals(mView2_device_information_ac_device_id)) {

			showShortToast(getString(R.string.ac_car_device_num)
					+ getString(R.string.can_not_null));

			return false;

		}

		return true;

	}

}
