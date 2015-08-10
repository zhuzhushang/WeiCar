package com.newbrain.adapter;



import java.util.List;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newbrain.model.Model_GetAllCar.Result;
import com.newbrain.weicar.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class MyBaseAdapter_CarManager  extends BaseAdapter{
	
	
	private Context context;
	private LayoutInflater inflater;
	private List<Result>  list;
	
	
	
	
	public MyBaseAdapter_CarManager() {
		super();
		// TODO Auto-generated constructor stub
	}


	public  MyBaseAdapter_CarManager(Context context,List<Result> list)
	{
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(context);
		
	}
	
	
	public void  setList( List<Result> list)
	{
		
		
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

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		ViewHolder viewHolder;
		
		
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.car_manager_listview_item, null);
			viewHolder = new ViewHolder();
			ViewUtils.inject(viewHolder, convertView);
			
			
			convertView.setTag(viewHolder);			
			
		}else
		{
			
			
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		
		viewHolder.tvCarNum.setText(list.get(position).getLicensePlate());
		
		
		
		return convertView;
	}
	
	
	
	public class ViewHolder
	{
		
		@ViewInject(R.id.tv_car_num)
		private TextView tvCarNum;
		
		
	}

}
