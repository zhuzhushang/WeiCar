package com.newbrain.model;

import java.io.Serializable;
import java.util.List;

public class Model_GetAllCar implements Serializable{
	
	
	/*{
		“code”:”0/1”，
		"message":"数据为空/数据不为空",
		“result”:[{
		“id”:”id”,
		“equipmentNo”:”设备号”,
		“models”:”车型”,
		“frameNo”:”车架号”,
		“licensePlate”:”车牌号”,
		“engineNo”:”发动机号”,
		“buyTime”:”购买时间”,
		“recommend”:”推荐人”,
		“customerId”:”会员id”,
		“brandId”:”品牌id”,
		“image”:”图片”,
		“sim”:”sim卡号”
		}]
		}*/
	
	
private String code;
	
	private String message;
	
	private List<Result> result;
	
	
	public class Result  implements Serializable
	{
		
		private String  id;
		private String  equipmentNo;
		private String  models;
		private String  frameNo;
		private String  licensePlate;
		private String  engineNo;
		private String  buyTime;
		private String  recommend;
		private String  customerId;
		private String  brandId;
		private String  image;
		private String  sim;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getEquipmentNo() {
			return equipmentNo;
		}
		public void setEquipmentNo(String equipmentNo) {
			this.equipmentNo = equipmentNo;
		}
		public String getModels() {
			return models;
		}
		public void setModels(String models) {
			this.models = models;
		}
		public String getFrameNo() {
			return frameNo;
		}
		public void setFrameNo(String frameNo) {
			this.frameNo = frameNo;
		}
		public String getLicensePlate() {
			return licensePlate;
		}
		public void setLicensePlate(String licensePlate) {
			this.licensePlate = licensePlate;
		}
		public String getEngineNo() {
			return engineNo;
		}
		public void setEngineNo(String engineNo) {
			this.engineNo = engineNo;
		}
		public String getBuyTime() {
			return buyTime;
		}
		public void setBuyTime(String buyTime) {
			this.buyTime = buyTime;
		}
		public String getRecommend() {
			return recommend;
		}
		public void setRecommend(String recommend) {
			this.recommend = recommend;
		}
		public String getCustomerId() {
			return customerId;
		}
		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}
		public String getBrandId() {
			return brandId;
		}
		public void setBrandId(String brandId) {
			this.brandId = brandId;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
		public String getSim() {
			return sim;
		}
		public void setSim(String sim) {
			this.sim = sim;
		}
		
		
		
		
		
		
		
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public List<Result> getResult() {
		return result;
	}


	public void setResult(List<Result> result) {
		this.result = result;
	}


	
	
	
	

}
