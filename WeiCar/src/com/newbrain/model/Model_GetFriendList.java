package com.newbrain.model;

import java.util.List;

public class Model_GetFriendList {

	/*
	 * { “code”:”0/1/2”， "message":"成功/接口异常/失败", result:[{ “id”:”好友id”,
	 * “image”:”图片”, “nickName”:”昵称” }] }
	 */

	private String code;

	private String message;

	private List<Result> result;

	public class Result {

		private String id;
		private String image;
		private String nickName;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
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
