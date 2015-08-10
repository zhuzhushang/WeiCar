package com.newbrain.weicar.car.friends;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity_FA;
import com.newbrain.weicar.R;

public class AddCarFriendActivity extends BaseActivity_FA {

	private Context context;

	@ViewInject(R.id.car_friend_back)
	private TextView tv_back;

	@ViewInject(R.id.car_friend_radiogroup)
	private RadioGroup mRadioGroup;

	

	private int mCurrentFramentIndex;

	private int mIndex;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.add_car_friend_activity);

		context = this;
		ViewUtils.inject(this);

		
		mRadioGroup.setOnCheckedChangeListener(mCheckedChangeListener);
	}

	
	
	@OnClick({ R.id.car_friend_back,R.id.car_friends_nearby_cf })
	private void onClick_CarFriend(View view) {

		switch (view.getId()) {
		case R.id.car_friend_back:

			finish();

			break;
			
		case R.id.car_friends_nearby_cf:

			openActivity(NearByCarFriendActivity.class);
			
			

			break;

		default:
			break;
		}

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

//			if (mIndex != mCurrentFramentIndex) {
//
//				FragmentTransaction ft = getSupportFragmentManager()
//						.beginTransaction();
//
//				ft.hide(mFragment_array[mCurrentFramentIndex]);
//
//				if (!mFragment_array[mIndex].isAdded()) {
//
//					ft.add(R.id.car_friend_contains, mFragment_array[mIndex]);
//
//				}
//
//				ft.show(mFragment_array[mIndex]);
//				
//				ft.commit();
//				
//				mCurrentFramentIndex = mIndex;
//
//			}
			
			
			

		}
	};

}
