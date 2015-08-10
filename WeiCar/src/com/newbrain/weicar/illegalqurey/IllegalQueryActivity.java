package com.newbrain.weicar.illegalqurey;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.HttpParams;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.DBModel_City;
import com.newbrain.model.DBModel_Province;
import com.newbrain.model.DBModel_SelectedCity;
import com.newbrain.model.Model_IllegalQuery;
import com.newbrain.model.Model_IllegalQueryCityQuery;
import com.newbrain.model.Model_IllegalQueryCityQuery.Result.City;
import com.newbrain.utils.Constant;
import com.newbrain.weicar.R;
import com.newbrain.xutils.Xutils_DBUtils;

public class IllegalQueryActivity extends BaseActivity {

	private Context context;

	@ViewInject(R.id.illegal_car_num)
	private EditText mEt_illegal_car_num;

	@ViewInject(R.id.illegal_engine)
	private EditText mEt_illegal_engine;

	@ViewInject(R.id.illegal_VIN)
	private EditText mEt_illegal_VIN;

	@ViewInject(R.id.illegal_query_city)
	private Button mEt_illegal_query_city;
	
	@ViewInject(R.id.illegal_car_num_head)
	private TextView mTvIllegalHead;

	private String illegal_car_num, illegal_engine, illegal_VIN,
			illegal_query_city;

	private DbUtils mDBUtils;

	private List<DBModel_Province> mProvince;
	private List<DBModel_City> mCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.illegal_query_activity);

		context = this;
		ViewUtils.inject(this);

		mDBUtils = Xutils_DBUtils.getDBUtils(context);

		actionbarInit();

//		dataInit();

		getPCA();
		
	
		
	}

	private void dataInit() {
		// TODO Auto-generated method stub

		mProvince = new ArrayList<DBModel_Province>();
		mCity = new ArrayList<DBModel_City>();

	}

	/** 获取省市区 没有就网络请求 有就到数据库里面取 */
	private void getPCA() {

		try {
			mProvince = mDBUtils.findAll(DBModel_Province.class);
//			mCity = mDBUtils.findAll(DBModel_City.class);

			if (null == mProvince ) {

				requestHttp_getPCA();

			} else {
				
				
				/**这里set保存的数据*/
				
				setData();

			}

		} catch (DbException e) {
			
			e.printStackTrace();
		}
	}
	
	
	/*city.setAbbr(mCurrentCity.getAbbr());
	city.setCity_code(mCurrentCity.getCity_code());
	city.setCity_name(mCurrentCity.getCity_name());
	
	city.setClassa(mCurrentCity.getClassa());
	city.setClassno(mCurrentCity.getClassno());
	city.setEngine(mCurrentCity.getEngine());
	city.setEngineno(mCurrentCity.getEngineno());
	city.setRegist(mCurrentCity.getRegist());
	city.setRegistno(mCurrentCity.getRegistno());;
	
	city.setProvince(mCurrentProvince.getProvince());
	city.setProvince_code(mCurrentProvince.getProvince_code());*/
	
	/**set数据*/
	private void setData()
	{
		try {
			
			DBModel_SelectedCity city = mDBUtils.findById(DBModel_SelectedCity.class, 1);
			
			if(city == null)
			{
				LogUtils.e("---->没有储存信息");
				return;
			}
			
			mTvIllegalHead.setText(city.getAbbr());
			mEt_illegal_car_num.setText(city.getCar_num());
			mEt_illegal_engine.setText(city.getEngine_num());
			mEt_illegal_VIN.setText(city.getClassa_num());
			mEt_illegal_query_city.setText(city.getProvince()+city.getCity_name());
			
			mCurrentCity = new DBModel_City();
			mCurrentProvince = new DBModel_Province();
			
			mCurrentProvince.setProvince(city.getProvince());
			mCurrentProvince.setProvince_code(city.getProvince_code());
			mCurrentProvince.setProvince_attr(city.getAbbr());
			
			mCurrentCity.setAbbr(city.getAbbr());
			mCurrentCity.setCity_code(city.getCity_code());
			mCurrentCity.setCity_name(city.getCity_name());
			mCurrentCity.setClassa(city.getClassa());
			mCurrentCity.setClassno(city.getClassno());
			mCurrentCity.setEngine(city.getEngine());
			mCurrentCity.setEngineno(city.getEngineno());
//			mCurrentCity.setId(id);
//			mCurrentCity.set
			
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	/**当前选择的省*//*
	private String mCurrentProvinceName;
	*//**省的简称*//*
	private String mCurrentProvinceAbbr;
	*//**选择的城市*//*
	private String mCurrentCityName;
	*//**选择的城市简称*//*
	private String mCurrentCityAbbr;*/
	
	/**当前选中的省对象*/
	private DBModel_Province mCurrentProvince;
	
	
	/**当前选中的城市的对象*/
	private DBModel_City mCurrentCity;
	

	private void showPCAFromDB() {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder build = new AlertDialog.Builder(context);
		build.setTitle(getString(R.string.illegal_choice_province));
//		ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, android.R.id.text1, mProvince);
		
		ArrayAdapter<DBModel_Province> adapter = new ArrayAdapter<DBModel_Province>(context, android.R.layout.simple_list_item_1, android.R.id.text1, mProvince);
		
		build.setAdapter(adapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				
				try {
					
					mCurrentProvince = mProvince.get(which);
					
					mCity = mDBUtils.findAll(Selector.from(DBModel_City.class).where("abbr", "=", mProvince.get(which).getProvince_attr()));
				
					if(mCity != null)
					{
						
						if(mCity.size() == 1)
						{
							mCurrentCity = mCity.get(0);
							
							
							mEt_illegal_query_city.setText(mCurrentProvince.getProvince());
							
							mTvIllegalHead.setText(mCurrentCity.getAbbr());
							
							return;
						}
						
						
						AlertDialog.Builder  bCity = new AlertDialog.Builder(context);
						bCity.setTitle(getString(R.string.illegal_choice_city));
						
						ArrayAdapter<DBModel_City> adapter = new ArrayAdapter<DBModel_City>(context, android.R.layout.simple_list_item_1, mCity);
						
					
						bCity.setAdapter(adapter, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
								mCurrentCity = mCity.get(which);
								
								
								mEt_illegal_query_city.setText(mCurrentProvince.getProvince()+mCurrentCity.getCity_name());
								
								mTvIllegalHead.setText(mCurrentCity.getAbbr());
								
								
								
								
							}
						});
						
						bCity.show();
					}
				
					
				
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		
		
		build.show();
		
		
	}
	
	
	@OnClick({R.id.illegal_query_city,R.id.illegel_confirm_qurey})
	private void onClick_city(View view)
	{
		
		switch (view.getId()) {
		case R.id.illegal_query_city:
			
			
			showPCAFromDB();
			
			
			break;
			
		case R.id.illegel_confirm_qurey:
			
			/**保存并查询*/
			
			
			saveAndQuery();
			
			
			
			break;
		default:
			break;
		}
		
		
		
	}
	
	
	
	/**保存并查询*/
	private void saveAndQuery()
	{
		/**
		 * 保存数据
		 * 
		 * */
		
		etInit();
		
		
	}
	
	
	private void save()
	{
		
		DBModel_SelectedCity city = new DBModel_SelectedCity();
		
		city.setId(1);
		city.setAbbr(mCurrentCity.getAbbr());
		city.setCity_code(mCurrentCity.getCity_code());
		city.setCity_name(mCurrentCity.getCity_name());
		
		city.setClassa(mCurrentCity.getClassa());
		city.setClassno(mCurrentCity.getClassno());
		city.setEngine(mCurrentCity.getEngine());
		city.setEngineno(mCurrentCity.getEngineno());
		city.setRegist(mCurrentCity.getRegist());
		city.setRegistno(mCurrentCity.getRegistno());;
		
		city.setProvince(mCurrentProvince.getProvince());
		city.setProvince_code(mCurrentProvince.getProvince_code());
		city.setCar_num(illegal_car_num);
		city.setEngine_num(illegal_engine);
		city.setClassa_num(illegal_VIN);
		
		try {
			mDBUtils.saveOrUpdate(city);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	private void requestHttp_getPCA() {
		
		
		List<Bean> list = new ArrayList<Bean>();

		list.add(new Bean("format", "2"));
		list.add(new Bean("key", Constant.KEY_ILLEGALQUERY));

		JsonThread jsonThread = new JsonThread(context, list, mHandler,
				Constant.FLAG_GETPAC);

		jsonThread.start();

		CustomProgressDialog.startProgressDialog(context);

	}

	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {

			/** 得到省市区 */
			case Constant.FLAG_GETPAC:

				
				mProvince = new ArrayList<DBModel_Province>();
				mCity = new ArrayList<DBModel_City>();
				
				Model_IllegalQueryCityQuery model = (Model_IllegalQueryCityQuery) msg.obj;

				if (model.getResultcode().equals(Constant.REQUEST_SUCCESS)) {
					List<com.newbrain.model.Model_IllegalQueryCityQuery.Result> list = model
							.getResult();

					if (list == null) {

						return;

					}

					for (int i = 0; i < list.size(); i++) {

						try {
							com.newbrain.model.Model_IllegalQueryCityQuery.Result result = list
									.get(i);

							DBModel_Province pro = new DBModel_Province();
							pro.setProvince(result.getProvince());
							pro.setProvince_attr(result.getCitys().get(0)
									.getAbbr());
							pro.setProvince_code(result.getProvince_code());
							 mProvince.add(pro);

							mDBUtils.save(pro);

							List<City> citys = result.getCitys();

							for (int j = 0; j < citys.size(); j++) {

								DBModel_City model_city = new DBModel_City();

								City city = citys.get(j);
								
								
								
								model_city.setAbbr(city.getAbbr());
								model_city.setCity_code(city.getCity_code());
								model_city.setCity_name(city.getCity_name());
								model_city.setClassa(city.getClassa());
								model_city.setClassno(city.getClassno());
								model_city.setEngine(city.getEngine());
								model_city.setEngineno(city.getEngineno());
								model_city.setRegist(city.getRegist());
								model_city.setRegistno(city.getRegistno());
								
//								mCity.add(model_city);
								mDBUtils.save(model_city);

							}
						} catch (DbException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
							LogUtils.e("--------->保存城市数据失败");
							
						}

					}

				}

				CustomProgressDialog.stopProgressDialog();
				break;
				
			case Constant.Flag_ILLEGALQUERY:
				
				CustomProgressDialog.stopProgressDialog();
				resultIllegal( msg);
				
				
				
				break;

			default:
				break;
			}

		}

	};
	
	
	
	
	/*对违章查询结果进行处理**/
	private void resultIllegal(Message msg)
	{
		
		Model_IllegalQuery model_iq = (Model_IllegalQuery) msg.obj;
		
		if(model_iq.getResultcode().equals(Constant.REQUEST_SUCCESS))
		{
			if(model_iq.getResult().getLists() != null &&model_iq.getResult().getLists().size() == 1)
			{
				
				Intent intent = new Intent(context, IllegalQueryDetailActivity.class);
				intent.putExtra(Constant.FLAG_ACTIVITY_RESULT, model_iq.getResult().getLists().get(0));
				intent.putExtra(Constant.FLAG_ACTIVITY_INDEX_FRIST, model_iq.getResult().getHphm());
				
				startActivity(intent);
				
				
			}else if(model_iq.getResult().getLists() != null &&model_iq.getResult().getLists().size() > 1)
			{
				Intent intent = new Intent(context, IllegalListsActivity.class);
				intent.putExtra(Constant.FLAG_ACTIVITY_RESULT, model_iq.getResult());
				
				startActivity(intent);
				
				
				
			}else if(model_iq.getResult().getLists() != null)
			{
				
				showLongToast(model_iq.getReason());
				
				
			}
			
		}else if(model_iq.getResultcode().equals("112"))
		{
			
			
			showShortToast("暂时不提供查询，请稍后再试");
			
		}else
		{
			
			
			showShortToast(model_iq.getReason());
			
		}
		
		
	}
	

	private void etInit() {
		// TODO Auto-generated method stub

		if (checkData()) {
			
			save();
			requestHttp_illegalQuery();
			
			
		}

	}
	
	/*key	string	是	你申请的key
 	city	String	是	城市代码 *
 	hphm	String	是	号牌号码 完整7位 ,需要utf8 urlencode*
 	hpzl	String	是	号牌类型，默认02
 	engineno	String	否	发动机号 (根据城市接口中的参数填写)
 	classno	String	否	车架号 (根据城市接口中的参数填写)*/
	
	/*engine	Int	是否需要发动机号0,不需要 1,需要
 	engineno	Int	需要几位发动机号0,全部 1-9 ,需要发动机号后N位
 	class	Int	是否需要车架号0,不需要 1,需要
 	classa	Int	同上,（解决java中class关键字无法映射）
 	classno	Int	需要几位车架号0,全部 1-9 需要车架号后N位*/

	private void requestHttp_illegalQuery() {
		// TODO Auto-generated method stub
		
		List<Bean> list = new ArrayList<Bean>();
		
		list.add(new Bean("key", Constant.KEY_ILLEGALQUERY));
		list.add(new Bean("city", mCurrentCity.getCity_code()));
		list.add(new Bean("hphm", mTvIllegalHead.getText().toString()+illegal_car_num));
		
		String isEngine = mCurrentCity.getEngine();
		
//		if(Integer.parseInt(isEngine) == )
		
		
		
		list.add(new Bean("engineno", illegal_engine));
		list.add(new Bean("classno", illegal_VIN));
		
		JsonThread jsonThread = new JsonThread(context, list, mHandler, Constant.Flag_ILLEGALQUERY);
		
		jsonThread.start();
		
		CustomProgressDialog.startProgressDialog(context);
		
	}

	private boolean checkData() {
		// TODO Auto-generated method stub

		illegal_car_num = mEt_illegal_car_num.getText().toString();
		illegal_engine = mEt_illegal_engine.getText().toString();
		illegal_query_city = mEt_illegal_query_city.getText().toString();
		illegal_VIN = mEt_illegal_VIN.getText().toString();

		if ("".equals(illegal_car_num)) {

			showShortToast(getString(R.string.illegal_car_num)
					+ getString(R.string.can_not_null));

			return false;

		} else if ("".equals(illegal_engine)) {

			showShortToast(getString(R.string.illegal_engine)
					+ getString(R.string.can_not_null));

			return false;

		} else if ("".equals(illegal_query_city)) {

			showShortToast(getString(R.string.illegal_query_city)
					+ getString(R.string.can_not_null));

			return false;

		} else if ("".equals(illegal_VIN)) {

			showShortToast(getString(R.string.illegal_VIN)
					+ getString(R.string.can_not_null));

			return false;
		}

		return true;
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
		actionbar_tv_name.setText(getString(R.string.illegal_query_));
		// actionbar_btn_right.setText(getString(R.string.comfirm1));

		actionbar_ll_left.setVisibility(View.VISIBLE);
		actionbar_btn_right.setVisibility(View.GONE);

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

			break;

		default:
			break;
		}

	}

}
