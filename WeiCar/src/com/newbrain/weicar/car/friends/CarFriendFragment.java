package com.newbrain.weicar.car.friends;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.adapter.MyBaseAdapter_GetFriendList;
import com.newbrain.customview.CustomProgressDialog;
import com.newbrain.jsonthread.JsonThread;
import com.newbrain.model.Bean;
import com.newbrain.model.Model_GetFriendList;
import com.newbrain.model.Model_GetFriendList.Result;
import com.newbrain.user.User;
import com.newbrain.utils.Constant;
import com.newbrain.weicar.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CarFriendFragment extends Fragment {

	private Context context;

	@ViewInject(R.id.car_friends_listview)
	private ListView mListView;

	private List<Result> mList;
	private MyBaseAdapter_GetFriendList mMyAdapter;

	/** 添加车友 */
	@ViewInject(R.id.car_friends_cf_add_cf)
	private LinearLayout mll_addCF;

	/** 车友群 */
	@ViewInject(R.id.car_friends_cf_group)
	private LinearLayout mll_group;

	@OnClick({ R.id.car_friends_cf_add_cf, R.id.car_friends_cf_group })
	private void onClick_cf(View view) {

		switch (view.getId()) {
		case R.id.car_friends_cf_add_cf:
			
			
			startActivity(new Intent(context, AddCarFriendActivity.class));
			
			break;

		case R.id.car_friends_cf_group:
			
			
			startActivity(new Intent(context, CarFriendGroupActivity.class));
			
			break;

		default:
			break;
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.car_friend_fragment, null);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		ViewUtils.inject(this, view);
		context = getActivity();

		dataInit();

	}

	private void dataInit() {
		mList = new ArrayList<Model_GetFriendList.Result>();
		mMyAdapter = new MyBaseAdapter_GetFriendList(context, mList);
		mListView.setAdapter(mMyAdapter);

		requestHttp_getFriendList();

	}

	private void requestHttp_getFriendList() {

		List<Bean> list = new ArrayList<Bean>();
		list.add(new Bean("customerId", User.getInstance().getId()));

		JsonThread jsonThread = new JsonThread(context, list, mHandler,
				Constant.FLAG_GET_FRIEND_LIST);

		jsonThread.start();

		CustomProgressDialog.startProgressDialog(context);

	}

	private Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case Constant.FLAG_GET_FRIEND_LIST:

				Model_GetFriendList friendList = (Model_GetFriendList) msg.obj;

				mList = friendList.getResult();

				mMyAdapter.notify();

				CustomProgressDialog.stopProgressDialog();

				break;

			default:
				break;
			}

		};

	};

}
