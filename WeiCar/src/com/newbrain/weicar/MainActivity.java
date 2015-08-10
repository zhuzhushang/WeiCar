package com.newbrain.weicar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.rollupdemo.MainActivityRoll;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.Model_Weather;
import com.newbrain.utils.Constant;
import com.newbrain.utils.SharedPreferencesHelp;
import com.newbrain.weicar.carmanager.AddCarActivity;
import com.newbrain.weicar.gridview.CarFirendsActivity;
import com.newbrain.weicar.gridview.CarManagerActivity;
import com.newbrain.weicar.gridview.IntegralActivity;
import com.newbrain.weicar.gridview.LocationServiceActivity;
import com.newbrain.weicar.gridview.ProductSetActivity;
import com.newbrain.weicar.illegalqurey.IllegalQueryActivity;
import com.newbrain.weicar.personal.center.PersonalCenterActivity;

public class MainActivity extends BaseActivity {

	private Context context;

	@ViewInject(R.id.homepager_traffic)
	private TextView tv_traffic;

	/** 行驶的里程+ */
	@ViewInject(R.id.homepager_Mileage)
	private TextView tv_mileage;

	private LocationClient mLocationClient;

	private MyBDLocation mMyBDLocation;

	public String mCity;

	@ViewInject(R.id.homepager_address)
	private TextView mTv_address;

	@ViewInject(R.id.homepager_weather_temperature_description)
	private TextView tv_weather_des;

	@ViewInject(R.id.homepager_weather_current_temperature)
	private TextView tv_current_temperature;

	@ViewInject(R.id.homepager_weather_temperature)
	private TextView tv_temperature;

	@ViewInject(R.id.homepager_imageview_weather)
	private ImageView mImg_weather;

	// private LocationClientOption mLocationClientOption;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = this;

		ViewUtils.inject(this);

		locationInit();
		tv_traffic.setText("限行\r\n堵车，不易出行");
		setMileage(123456);
	}

	private void locationInit() {
		// TODO Auto-generated method stub

		mMyBDLocation = new MyBDLocation();
		mLocationClient = new LocationClient(context);

		locationClientOption();
		mLocationClient.registerLocationListener(mMyBDLocation);
		mLocationClient.start();

	}

	private void locationClientOption() {
		LocationClientOption mLocationClientOption = new LocationClientOption();
		mLocationClientOption.setIsNeedAddress(true);
		mLocationClientOption.setNeedDeviceDirect(true);
		mLocationClientOption.setCoorType("bd09ll");
		mLocationClientOption.setLocationMode(LocationMode.Hight_Accuracy);

		mLocationClient.setLocOption(mLocationClientOption);

	}

	private void setMileage(int mileage) {

		String mi = "今日行驶\n" + mileage + "公里";

		SpannableString spannableString = new SpannableString(mi);

		ForegroundColorSpan span = new ForegroundColorSpan(context
				.getResources().getColor(R.color.theme_text_normal_color_blue));

		spannableString.setSpan(span, 4, mi.length() - 2,
				SpannableString.SPAN_INCLUSIVE_INCLUSIVE);

		tv_mileage.setText(spannableString);

	}

	@OnClick({ R.id.homepager_gridview_car_manager,
			R.id.homepager_gridview_address, R.id.homepager_gridview_friends,
			R.id.homepager_gridview_integral,
			R.id.homepager_gridview_product_set,
			R.id.homepager_gridview_record, R.id.homepager_user,
			R.id.homepager_safe_open, R.id.homepager_email,
			R.id.homepager_dianya, R.id.homepager_my_car_none,
			R.id.homepager_error_query_rela

	})
	private void onClick_gridview(View view) {

		switch (view.getId()) {
		case R.id.homepager_gridview_address:

			openActivity(LocationServiceActivity.class);

			break;

		case R.id.homepager_gridview_car_manager:

			openActivity(CarManagerActivity.class);

			break;

		case R.id.homepager_gridview_friends:

			openActivity(CarFirendsActivity.class);

			break;

		case R.id.homepager_gridview_integral:

			openActivity(IntegralActivity.class);

			break;

		case R.id.homepager_gridview_product_set:

			openActivity(ProductSetActivity.class);

			break;

		case R.id.homepager_gridview_record:

			openActivity(MainActivityRoll.class);

			break;
		case R.id.homepager_user:

			openActivity(PersonalCenterActivity.class);

			break;

		case R.id.homepager_safe_open:

			break;

		case R.id.homepager_email:

			break;

		case R.id.homepager_dianya:

			break;

		case R.id.homepager_my_car_none:

			openActivity(AddCarActivity.class);

			break;

		case R.id.homepager_error_query_rela:

			openActivity(IllegalQueryActivity.class);

			break;

		default:
			break;
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		mLocationClient.unRegisterLocationListener(mMyBDLocation);

	}

	public class MyBDLocation implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation bdLocation) {
			// TODO Auto-generated method stub

			mCity = bdLocation.getCity();

			LogUtils.e("---城市----->" + mCity);

			String city = getCutName(mCity);

			;
			bdLocation.getLongitude();

			SharedPreferencesHelp.saveFloat(context,
					Constant.LOCATION_LATITUDE,
					(float) bdLocation.getLatitude());
			SharedPreferencesHelp.saveFloat(context,
					Constant.LOCATION_LONGITUDE,
					(float) bdLocation.getLongitude());

			mTv_address.setText(city);

			isRequestWeather(mCity);
		}

	}

	private void isRequestWeather(String city) {
		// TODO Auto-generated method stub

		String weather_date = SharedPreferencesHelp.getString(context,
				Constant.WEATHER_DATE);

		LogUtils.e("----->" + getDateFormat() + "----->" + weather_date);

		if (getDateFormat().equals(weather_date)) {

			/** 进入此方法 表示今天已经请求过 直接给控件赋值 */

			LogUtils.e("-----今日已经请求过天气接口----》");

			setWeacher();

		} else {

			requestHttpWeather(city);
		}

	}

	private void requestHttpWeather(String city) {
		// TODO Auto-generated method stub

		List<Bean> list = new ArrayList<Bean>();

		list.add(new Bean("cityname", city));
		list.add(new Bean("key", Constant.KEY_WEATHER));

		JsonThread jsonThread = new JsonThread(context, list, mHandler,
				Constant.FLAG_WEATHER_JUHE);

		jsonThread.start();

	}

	private String getDateFormat() {
		// TODO Auto-generated method stub

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

		return dateFormat.format(new Date());

	}

	private void setWeacher() {

		String weaCurrentTemp = SharedPreferencesHelp.getString(context,
				Constant.WEATHER_CURRENT_TEMP);
		String weaTemperature = SharedPreferencesHelp.getString(context,
				Constant.WEATHER_TEMPERATURE);
		String weaWeather = SharedPreferencesHelp.getString(context,
				Constant.WEATHER_WEATHER);
		String weaFa = SharedPreferencesHelp.getString(context,
				Constant.WEATHER_FA);
		String weaFb = SharedPreferencesHelp.getString(context,
				Constant.WEATHER_FB);

		tv_current_temperature.setText(weaCurrentTemp);
		tv_temperature.setText(weaTemperature);
		tv_weather_des.setText(weaWeather);

		setWeatherPic(weaFa, weaFb);

	}

	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			case Constant.FLAG_WEATHER_JUHE:

				weatherSet(msg);

				break;

			default:
				break;
			}

		}

	};

	/** 再按一次退出的boolean值 */
	boolean isExit;


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();

		Button3State0_back();

	}

	public void Button3State0_back() {

		if (isExit == false) {
			isExit = true;

			Toast.makeText(context, "再按一次退出！", 0).show();

			Timer timer = new Timer();

			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					isExit = false;

				}
			};

			timer.schedule(task, 2000);

		} else {

			this.finish();
		}

	}

	/** 对获取到天气消息的处理 此接口一天之请求一次 */
	private void weatherSet(Message msg) {
		// TODO Auto-generated method stub

		Model_Weather model_Weather = (Model_Weather) msg.obj;

		tv_current_temperature.setText(model_Weather.getResult().getSk()
				.getTemp()
				+ "℃");
		tv_temperature.setText(model_Weather.getResult().getToday()
				.getTemperature());
		tv_weather_des.setText(model_Weather.getResult().getToday()
				.getWeather());

		String fa = model_Weather.getResult().getToday().getWeather_id()
				.getFa();
		String fb = model_Weather.getResult().getToday().getWeather_id()
				.getFb();

		setWeatherPic(fa, fb);

		SharedPreferencesHelp.saveString(context,
				Constant.WEATHER_CURRENT_TEMP, model_Weather.getResult()
						.getSk().getTemp()
						+ "℃");

		SharedPreferencesHelp.saveString(context, Constant.WEATHER_WEATHER,
				model_Weather.getResult().getToday().getWeather());
		SharedPreferencesHelp.saveString(context, Constant.WEATHER_TEMPERATURE,
				model_Weather.getResult().getToday().getTemperature());
		SharedPreferencesHelp.saveString(context, Constant.WEATHER_FA,
				model_Weather.getResult().getToday().getWeather_id().getFa());
		SharedPreferencesHelp.saveString(context, Constant.WEATHER_FB,
				model_Weather.getResult().getToday().getWeather_id().getFb());
		SharedPreferencesHelp.saveString(context, Constant.WEATHER_DATE,
				model_Weather.getResult().getToday().getDate_y());

	}

	/** set天气的图片 */
	private void setWeatherPic(String fa, String fb) {

		int weather = Integer.parseInt(fa);

		switch (weather) {
		case 0:

			mImg_weather.setImageResource(R.drawable.weather_sunny);

			break;
		case 1:

			mImg_weather.setImageResource(R.drawable.weather_more_cloudy_);

			break;
		case 2:

			mImg_weather.setImageResource(R.drawable.weather_cloudy_day);

			break;
		case 3:
			mImg_weather.setImageResource(R.drawable.weather_zhenyu);
			break;
		case 4:
			mImg_weather.setImageResource(R.drawable.weather_thunder_shower);
			break;
		case 5:
			mImg_weather.setImageResource(R.drawable.weather_thunder_shower);
			break;
		case 6:
			mImg_weather.setImageResource(R.drawable.weather_rain_and_snow);
			break;
		case 7:
			mImg_weather.setImageResource(R.drawable.weather_small_rain);
			break;

		case 8:

		case 9:

		case 10:

		case 11:

		case 12:
			mImg_weather.setImageResource(R.drawable.weather_larger_rain);
			break;
		case 13:

			mImg_weather.setImageResource(R.drawable.weather_small_snow);

			break;
		case 14:
			mImg_weather.setImageResource(R.drawable.weather_small_snow);
			break;
		case 15:

		case 16:

		case 17:

			mImg_weather.setImageResource(R.drawable.weather_lager_snow);

			break;
		case 18:

			mImg_weather.setImageResource(R.drawable.weather_fog);
			break;
		case 19:

			mImg_weather.setImageResource(R.drawable.weather_small_rain);
			break;
		case 20:
			mImg_weather.setImageResource(R.drawable.weather_fog);
			break;
		case 21:
			mImg_weather.setImageResource(R.drawable.weather_small_rain);
			break;
		case 22:

		case 23:

		case 24:

		case 25:
			mImg_weather.setImageResource(R.drawable.weather_larger_rain);
			break;
		case 26:

			mImg_weather.setImageResource(R.drawable.weather_small_snow);
			break;
		case 27:

		case 28:
			mImg_weather.setImageResource(R.drawable.weather_lager_snow);
			break;
		case 29:

			mImg_weather.setImageResource(R.drawable.weather_fog);
			break;
		case 30:
			mImg_weather.setImageResource(R.drawable.weather_fog);
			break;
		case 31:

			break;
		case 53:
			mImg_weather.setImageResource(R.drawable.weather_haze);
			break;

		default:
			mImg_weather.setImageResource(R.drawable.weather_haze);

			break;
		}

	}

	private String getCutName(String city) {
		if (city != null && !city.isEmpty()) {

			return city.substring(0, city.length() - 1);

		}

		return "北京";

	}

}
