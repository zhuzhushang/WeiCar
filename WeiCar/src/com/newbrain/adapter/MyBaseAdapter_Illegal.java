package com.newbrain.adapter;



import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.model.Model_IllegalQuery.Result.IllegalList;
import com.newbrain.weicar.R;



public class MyBaseAdapter_Illegal  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<IllegalList> list;
	
	public  MyBaseAdapter_Illegal(Context context,List<IllegalList> list)
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
		
		IllegalList mmo = list.get(position);
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.illegal_query_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		viewHolder.mTv_illegal_content.setText(mmo.getAct());
		viewHolder.mTv_illegal_date.setText(mmo.getDate());
		
		if(mmo.getHandled().equals("0"))
		{
			
			viewHolder.mTv_illegal_handle.setText("未处理");
			viewHolder.mTv_illegal_handle.setTextColor(context.getResources().getColor(R.color.theme_text_normal_color_red));
			
		}else
		{
			viewHolder.mTv_illegal_handle.setText("已处理");
			viewHolder.mTv_illegal_handle.setTextColor(context.getResources().getColor(R.color.theme_text_normal_color_black));
			
		}
		
		viewHolder.mTv_illegal_title.setText(mmo.getAct());
		
		
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.illegal_title)
		private TextView mTv_illegal_title;
		
		@ViewInject(R.id.illegal_content)
		private TextView mTv_illegal_content;
		
		
		@ViewInject(R.id.illegal_handle)
		private TextView mTv_illegal_handle;
		
		
		@ViewInject(R.id.illegal_date)
		private TextView mTv_illegal_date;
		
		
		
	}

}
