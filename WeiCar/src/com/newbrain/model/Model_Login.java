package com.newbrain.model;

public class Model_Login {

	/*
	 * “code”:”0/1/2”, “message”:”用户不存在/登录成功/密码错误”, “result”:{ “phoneNo”:”手机号码”,
	 * “password”:”密码”, “image”:”图片地址”, “nickName”:”昵称”, “signature”:”签名”,
	 * “sex”:”性别”, “age”:”年龄” }
	 */
	
//	{"code":"1","message":"登录成功","result":{"address":"","age":22,"hxId":"0","id":"402880ed4e8b6fc7014e8b76f86f0000","image":"",
//	"nickName":"未命名","phoneNo":"13823214300","sex":0,"signature":""}}

	private String code;

	private String message;

	private Result result;

	public class Result {

		
		private String hxId;
		private String id;
		
		private String phoneNo;

		private String password;
		private String image;

		private String nickName;

		private String signature;

		private String sex;

		private String age;
		
		
		
		public String getHxId() {
			return hxId;
		}

		public void setHxId(String hxId) {
			this.hxId = hxId;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPhoneNo() {
			return phoneNo;
		}

		public void setPhoneNo(String phoneNo) {
			this.phoneNo = phoneNo;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public String getSignature() {
			return signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
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

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}
