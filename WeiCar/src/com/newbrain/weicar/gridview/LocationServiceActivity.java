package com.newbrain.weicar.gridview;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;




import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity_FA;
import com.newbrain.weicar.R;
import com.newbrain.weicar.location.service.MapFragment;
import com.newbrain.weicar.location.service.NavigationActivity;
import com.newbrain.weicar.location.service.RankFragment;

public class LocationServiceActivity extends BaseActivity_FA {

	private Context context;

	@ViewInject(R.id.car_friend_back)
	private TextView tv_back;

	@ViewInject(R.id.car_friend_radiogroup)
	private RadioGroup mRadioGroup;
	
	@ViewInject(R.id.location_service_navigation)
	private Button mBtnNavigation;

	private Fragment mFragment_array[];

	private MapFragment mMapFragment;

	private RankFragment mRankFragment;

	private int mCurrentFramentIndex;

	private int mIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.gridview_location_service);

		context = this;
		ViewUtils.inject(this);
		
		
		
		fragmentInit();
		
		mRadioGroup.setOnCheckedChangeListener(mCheckedChangeListener);
	}

	private void fragmentInit() {
		// TODO Auto-generated method stub

		mMapFragment = new MapFragment();

		mRankFragment = new RankFragment();

		mFragment_array = new Fragment[] { 
				mMapFragment,mRankFragment };

		getSupportFragmentManager().beginTransaction()
				.add(R.id.car_friend_contains, mRankFragment)
				.add(R.id.car_friend_contains, mMapFragment).hide(mRankFragment)
				.show(mMapFragment).commit();

	}

	@OnClick({ R.id.car_friend_back,R.id.location_service_navigation })
	private void onClick_CarFriend(View view) {

		switch (view.getId()) {
		case R.id.car_friend_back:

			finish();

			break;
			
		case R.id.location_service_navigation:
			
			navigation();
			
			break;

		default:
			break;
		}

	}
	

	private void navigation() {
		// TODO Auto-generated method stub
		
		
		openActivity(NavigationActivity.class);
		
		
	}




	private OnCheckedChangeListener mCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub

			if (checkedId == R.id.car_friend_chat) {

				group.setSelected(false);

				mIndex = 0;

			} else {

				group.setSelected(true);

				mIndex = 1;
			}

			if (mIndex != mCurrentFramentIndex) {

				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();

				ft.hide(mFragment_array[mCurrentFramentIndex]);

				if (!mFragment_array[mIndex].isAdded()) {

					ft.add(R.id.car_friend_contains, mFragment_array[mIndex]);

				}

				ft.show(mFragment_array[mIndex]);
				
				ft.commit();
				
				mCurrentFramentIndex = mIndex;

			}
		}
	};

}
