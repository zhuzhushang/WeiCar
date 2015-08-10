package com.newbrain.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;


@Table(name = "selected_city")
public class DBModel_SelectedCity {
	
	
	/*"city_name": "北京",
    "city_code": "BJ",
    "abbr": "京",
    "engine": "1",
    "engineno": "0",
    "classa": "0",
    "class": "0",
    "classno": "0",
    "regist": "0",
    "registno": "0"*/
	
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
	
	@Column(column = "city_name")
	private String city_name;
	
	@Column(column = "abbr")
	private String abbr;
	
	@Column(column = "city_code")
	private String city_code;
	
	@Column(column = "engine")
	private String engine;
	
	@Column(column = "engineno")
	private String engineno;
	
	@Column(column = "classa")
	private String classa;
	
	@Column(column = "classno")
	private String classno;
	
	@Column(column = "regist")
	private String regist;
	
	@Column(column = "registno")
	private String registno;
	
	@Column(column = "car_num")
	private String car_num;
	
	/**发动机号*/
	@Column(column = "engine_num")
	private String engine_num;
	
	
	/**车架号*/
	@Column(column = "classa")
	private String classa_num;
	
	
	
	

	public String getCar_num() {
		return car_num;
	}

	public void setCar_num(String car_num) {
		this.car_num = car_num;
	}

	public String getEngine_num() {
		return engine_num;
	}

	public void setEngine_num(String engine_num) {
		this.engine_num = engine_num;
	}

	public String getClassa_num() {
		return classa_num;
	}

	public void setClassa_num(String classa_num) {
		this.classa_num = classa_num;
	}

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

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getCity_code() {
		return city_code;
	}

	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getEngineno() {
		return engineno;
	}

	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}

	public String getClassa() {
		return classa;
	}

	public void setClassa(String classa) {
		this.classa = classa;
	}

	public String getClassno() {
		return classno;
	}

	public void setClassno(String classno) {
		this.classno = classno;
	}

	public String getRegist() {
		return regist;
	}

	public void setRegist(String regist) {
		this.regist = regist;
	}

	public String getRegistno() {
		return registno;
	}

	public void setRegistno(String registno) {
		this.registno = registno;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return city_name;
	}
	
	
	
	
	

}
