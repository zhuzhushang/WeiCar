package com.newbrain.user;

public class User {

	/*
	 * “phoneNo”:”手机号码”, “password”:”密码”, “image”:”图片地址”, “nickName”:”昵称”,
	 * “signature”:”签名”, “sex”:”性别”, “age”:”年龄”
	 */
	
	private static User user;
	
	public static User getInstance()
	{
		
		if(user == null)
		{
			
			user = new User();
			
		}
		
		
		return user;
		
		
	}
	
	
	private  User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	private String phoneNo;

	private String password;
	private String image;

	private String nickName;
	private String signature;
	private String sex;
	private String age;
	
	private String id;
	private String hxId;
	
	
	
	private String address;
	
	

	public String getHxId() {
		return hxId;
	}


	public void setHxId(String hxId) {
		this.hxId = hxId;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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
