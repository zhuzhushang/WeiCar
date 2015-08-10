package com.example.rollupdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.newbrain.weicar.R;

public class MainActivityRoll extends Activity implements OnClickListener{

	private Button loginButton = null;
	private Button registButton = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_rollup_demo);
		initView();
	}
	
	private void initView(){
		loginButton = (Button) this.findViewById(R.id.btn_login);
		registButton = (Button) this.findViewById(R.id.btn_regist);
		
		loginButton.setOnClickListener(this);
		registButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login:
			Intent intent1 = new Intent(this, LoginActivity.class);
			startActivity(intent1);
			break;
		case R.id.btn_regist:
			Intent intent2 = new Intent(this, RegistActivity.class);
			startActivity(intent2);
			break;

		default:
			break;
		}
	}
}
