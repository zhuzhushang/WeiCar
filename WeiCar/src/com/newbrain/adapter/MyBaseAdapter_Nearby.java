package com.newbrain.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.utils.DistanceUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.model.Model_NearbyCF.Result;
import com.newbrain.weicar.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyBaseAdapter_Nearby extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<Result> list;

	public MyBaseAdapter_Nearby(Context context, List<Result> list) {
		this.context = context;
		this.list = list;

		inflater = LayoutInflater.from(this.context);

	}
	
	
	

	public List<Result> getList() {
		return list;
	}




	public void setList(List<Result> list) {
		this.list = list;
		
		notifyDataSetChanged();
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

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		ViewHolder viewHolder;
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.nearby_car_friend_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			
			
			
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Result result = list.get(position);
		
		if(!result.getImage().equals(""))
		{
			ImageLoader.getInstance().displayImage(result.getImage(), viewHolder.ivHead);
		}
		
//		viewHolder.user_distance.setText(result.get);
		viewHolder.user_name.setText(result.getNickName());
		
		viewHolder.user_sign.setText(result.getSignature());
		
		if(result.getSex() != null&&!result.getSex().equals(""))
		{
			
			int sexId = Integer.parseInt(result.getSex());
					
					switch (sexId) {
					case 0:
						
						viewHolder.user_name.setCompoundDrawables(null, null,context.getResources().getDrawable(R.drawable.boy), null);
						
						break;
						
					
					case 1:
						
						viewHolder.user_name.setCompoundDrawables(null, null,context.getResources().getDrawable(R.drawable.girl), null);
						
						break;
						

					default:
						
						
						viewHolder.user_name.setCompoundDrawables(null, null,null, null);
						
						break;
					}
			
		}else
		{
			
			viewHolder.user_name.setCompoundDrawables(null, null,null, null);
			
		}
		
		
		
		return convertView;
	}

	public class ViewHolder {

		@ViewInject(R.id.nearby_imageview)
		private ImageView ivHead;

		@ViewInject(R.id.user_name)
		private TextView user_name;

		@ViewInject(R.id.user_sign)
		private TextView user_sign;

		@ViewInject(R.id.user_distance)
		private TextView user_distance;

	}

}
