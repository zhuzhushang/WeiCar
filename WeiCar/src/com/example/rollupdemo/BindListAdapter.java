package com.example.rollupdemo;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.newbrain.weicar.R;

public class BindListAdapter extends BaseAdapter {
//	private static final String TAG = "DoorListAdapter";
	private LayoutInflater inflater;
	private List<HashMap<String, String>> list;

	public BindListAdapter(Context context, List<HashMap<String, String>> list) {
		inflater = LayoutInflater.from(context);
		this.list = list;
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
		Holder holder = null;
		if (convertView == null) {			
			convertView = inflater.inflate(R.layout.binddoor_items, null);
			holder = new Holder();
			holder.mTextView = (TextView) convertView
					.findViewById(R.id.tv_doorbell_name);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.mTextView.setText(list.get(position).get("device_name"));
		return convertView;
	}

	class Holder {
		public TextView mTextView;
	}
}