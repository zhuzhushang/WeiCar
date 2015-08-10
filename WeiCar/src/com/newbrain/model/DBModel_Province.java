package com.newbrain.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;


@Table(name = "province")
public class DBModel_Province {
	
	
	@Column(column = "id")
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	@Column(column = "province")
	private String province;
	
	
	@Column(column = "province_code")
	private String province_code;
	
	
	@Column(column = "province_attr")
	private String province_attr;


	public String getProvince() {
		return province;
	}


	public void setProvince(String province) {
		this.province = province;
	}


	public String getProvince_code() {
		return province_code;
	}


	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}


	public String getProvince_attr() {
		return province_attr;
	}


	public void setProvince_attr(String province_attr) {
		this.province_attr = province_attr;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return province;
	}
	
	
	
	
	
	
	
	
	
}
