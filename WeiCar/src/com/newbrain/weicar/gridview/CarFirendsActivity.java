package com.newbrain.weicar.gridview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.newbrain.baseactivity.BaseActivity;
import com.newbrain.baseactivity.BaseActivity_FA;
import com.newbrain.weicar.R;
import com.newbrain.weicar.car.friends.CarFriendChatFragment;
import com.newbrain.weicar.car.friends.CarFriendFragment;

public class CarFirendsActivity extends BaseActivity_FA {

	private Context context;

	@ViewInject(R.id.car_friend_back)
	private TextView tv_back;

	@ViewInject(R.id.car_friend_radiogroup)
	private RadioGroup mRadioGroup;

	private Fragment mFragment_array[];

	private CarFriendChatFragment mCarFriendChatFragment;

	private CarFriendFragment mCarFriendFragment;

	private int mCurrentFramentIndex;

	private int mIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.car_friends_activity);

		context = this;
		ViewUtils.inject(this);

		fragmentInit();
		
		mRadioGroup.setOnCheckedChangeListener(mCheckedChangeListener);
	}

	private void fragmentInit() {
		// TODO Auto-generated method stub

		mCarFriendFragment = new CarFriendFragment();

		mCarFriendChatFragment = new CarFriendChatFragment();

		mFragment_array = new Fragment[] { mCarFriendChatFragment,
				mCarFriendFragment };

		getSupportFragmentManager().beginTransaction()
				.add(R.id.car_friend_contains, mCarFriendChatFragment)
				.add(R.id.car_friend_contains, mCarFriendFragment).hide(mCarFriendFragment)
				.show(mCarFriendChatFragment).commit();

	}

	@OnClick({ R.id.car_friend_back })
	private void onClick_CarFriend(View view) {

		switch (view.getId()) {
		case R.id.car_friend_back:

			finish();

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
