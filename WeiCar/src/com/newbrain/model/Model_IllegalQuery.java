package com.newbrain.model;

import java.io.Serializable;
import java.util.List;

public class Model_IllegalQuery implements Serializable{

	/*
	 * "resultcode":"200", "reason":"查询成功", "result":{ "province":"HB",
	 * "city":"HB_HD", "hphm":"冀DHL327", "hpzl":"02", "lists":[ {
	 * "date":"2013-12-29 11:57:29", "area":"316省道53KM+200M", "act":
	 * "16362 : 驾驶中型以上载客载货汽车、校车、危险物品运输车辆以外的其他机动车在高速公路以外的道路上行驶超过规定时速20%以上未达50%的",
	 * "code":"", "fen":"6", "money":"100", "handled":"0" } ] }
	 */

	private String resultcode;

	private String reason;

	private Result result;

	public class Result  implements Serializable{
		// private String ;

		private String province;
		private String city;
		private String hphm;
		private String hpzl;

		private List<IllegalList> lists;

		public class IllegalList implements Serializable{

			private String date;

			private String area;
			private String act;
			private String code;
			private String fen;
			private String money;
			private String handled;

			public String getDate() {
				return date;
			}

			public void setDate(String date) {
				this.date = date;
			}

			public String getArea() {
				return area;
			}

			public void setArea(String area) {
				this.area = area;
			}
			
			
			
			

			public String getAct() {
				return act;
			}

			public void setAct(String act) {
				this.act = act;
			}

			public String getCode() {
				return code;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public String getFen() {
				return fen;
			}

			public void setFen(String fen) {
				this.fen = fen;
			}

			public String getMoney() {
				return money;
			}

			public void setMoney(String money) {
				this.money = money;
			}

			public String getHandled() {
				return handled;
			}

			public void setHandled(String handled) {
				this.handled = handled;
			}

		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getHphm() {
			return hphm;
		}

		public void setHphm(String hphm) {
			this.hphm = hphm;
		}

		public String getHpzl() {
			return hpzl;
		}

		public void setHpzl(String hpzl) {
			this.hpzl = hpzl;
		}

		public List<IllegalList> getLists() {
			return lists;
		}

		public void setLists(List<IllegalList> lists) {
			this.lists = lists;
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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
