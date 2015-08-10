package com.newbrain.model;

import java.util.List;

import com.baidu.mapapi.search.core.CityInfo;

public class Model_IllegalQueryCityQuery {

	/*
	 * "resultcode": "200", "reason": "成功的返回", "result": [ { "province": "北京",
	 * "province_code": "BJ", "citys": [ { "city_name": "北京", "city_code": "BJ",
	 * "abbr": "京", "engine": "1", "engineno": "0", "classa": "0", "class": "0",
	 * "classno": "0", "regist": "0", "registno": "0" } ] },
	 * 
	 * { "province": "浙江", "province_code": "ZJ", "citys": [ { "city_name":
	 * "杭州", "city_code": "ZJ_HZ", "abbr": "浙", "engine": "0", "engineno": "0",
	 * "classa": "1", "class": "1", "classno": "6", "regist": "0", "registno":
	 * "0" }, { "city_name": "宁波", "city_code": "ZJ_NB", "abbr": "浙", "engine":
	 * "0", "engineno": "0", "classa": "1", "class": "1", "classno": "6",
	 * "regist": "0", "registno": "0" },
	 * 
	 * }
	 */

	private String resultcode;

	private String reason;

	private List<Result> result;

	public class Result {
		private String province;

		// private String ;

		private String province_code;

		private List<City> citys;

		public class City {

			private String city_name;
			private String city_code;
			private String abbr;
			private String engine;
			private String engineno;
			private String classa;
			private String classno;
			private String regist;
			private String registno;

			public String getCity_name() {
				return city_name;
			}

			public void setCity_name(String city_name) {
				this.city_name = city_name;
			}

			public String getCity_code() {
				return city_code;
			}

			public void setCity_code(String city_code) {
				this.city_code = city_code;
			}

			public String getAbbr() {
				return abbr;
			}

			public void setAbbr(String abbr) {
				this.abbr = abbr;
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

		public List<City> getCitys() {
			return citys;
		}

		public void setCitys(List<City> citys) {
			this.citys = citys;
		}

	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}

}
