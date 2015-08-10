package com.newbrain.adapter;



import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.model.Model_GetFriendList.Result;
import com.newbrain.weicar.R;
import com.nostra13.universalimageloader.core.ImageLoader;



public class MyBaseAdapter_GetFriendList  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<Result>  list;
	
	public  MyBaseAdapter_GetFriendList(Context context,List<Result> list)
	{
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(this.context);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		ViewHolder viewHolder;
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.car_friends_fra_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			
			
			
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		ImageLoader.getInstance().displayImage(list.get(position).getImage(), viewHolder.car_friend_head_picture);
		
		viewHolder.car_friend_nicename.setText(list.get(position).getNickName());
		
		
		
		
		
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.car_friend_nicename)
		private TextView car_friend_nicename;
		
		
		@ViewInject(R.id.car_friend_head_picture)
		private ImageView car_friend_head_picture;
		
		
	}

}
