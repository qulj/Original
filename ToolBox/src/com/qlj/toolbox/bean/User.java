package com.qlj.toolbox.bean;

import java.io.Serializable;

/**
 * 用户
 * 
 * @author
 * @date 2013-9-9
 */
public class User implements Serializable {

	private static final long serialVersionUID = 7991444547211162096L;

	private String name = "";
	private String userID = "";
	private String userType = "";
	private String telphone = "";
	private String enterpriseID = "";

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getEnterpriseID() {
		return enterpriseID;
	}

	public void setEnterpriseID(String enterpriseID) {
		this.enterpriseID = enterpriseID;
	}

	// public User(String userId, String nickName, String sex, String tel,
	// String headPhoto) {
	// super();
	// this.userId = userId;
	// this.nickName = nickName;
	// this.sex = sex;
	// this.tel = tel;
	// this.headPhoto = headPhoto;
	// }

}
