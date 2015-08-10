package com.newbrain.weicar.location.service;



import com.lidroid.xutils.ViewUtils;
import com.newbrain.weicar.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RankFragment extends Fragment{
	
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		View view = inflater.inflate(R.layout.location_service_rank_fragment, null);
		
		context = getActivity();
		ViewUtils.inject(this,view);
		
		
		
		
		return view;
	}
	
	
	
	

}
