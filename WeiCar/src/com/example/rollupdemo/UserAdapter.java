package com.example.rollupdemo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anychat.ControlCenter;
import com.example.anychat.UserItem;
import com.newbrain.weicar.R;

public class UserAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mLayoutInflater;
	private ArrayList<UserItem> mUserDatas;

	public UserAdapter(Context context) {
		
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mUserDatas = ControlCenter.mOnlineFriendItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mUserDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;

		if (convertView == null) {
			View view;
			view = mLayoutInflater.inflate(R.layout.user_item, null);

			
			viewHolder = new ViewHolder();
			viewHolder.textStatus = (TextView) view
					.findViewById(R.id.textview_status);
			viewHolder.textName = (TextView) view
					.findViewById(R.id.txt_username);
			viewHolder.textNameOther = (TextView) view.findViewById(R.id.txt_usernameother);
			convertView = view;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		UserItem userItem = mUserDatas.get(position);
		if (userItem != null) {
			int userId = userItem.getUserId();
			viewHolder.textNameOther.setText(userItem.getUserNameOther());
			viewHolder.textName.setText(userItem.getUserName());
			
			if (userItem.getIsOnline() == 1){
				viewHolder.textStatus.setText(mContext.getString(R.string.string_online));
			}else if (userItem.getIsOnline() == 2){
				viewHolder.textStatus.setText("");
			}else{
				viewHolder.textStatus.setText(mContext.getString(R.string.string_underline));
			}
		}
		return convertView;
	}

	class ViewHolder {
		public TextView textName;
		public TextView textNameOther;
		public TextView textStatus;
	}

}
