package com.qlj.toolbox.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Follow implements Parcelable {
	private String enterpriseID = "";// 关注ID
	private String enterpriseName = "";// 商家名称
	private String followTime = "";// 关注时间
	private String enterpriseImg = "";// logo

	private String newMsgTitle = "";// 最新消息标题
	private String newMsgTime = "";// 最新消息时间

	public Follow() {
		super();
	}

	public Follow(String followId, String followTime) {
		super();
		this.enterpriseID = followId;
		this.followTime = followTime;
	}

	public static final Parcelable.Creator<Follow> CREATOR = new Creator<Follow>() {

		@Override
		public Follow[] newArray(int size) {
			return new Follow[size];
		}

		@Override
		public Follow createFromParcel(Parcel source) {
			Follow bean = new Follow();
			bean.enterpriseID = source.readString();
			bean.followTime = source.readString();
			bean.newMsgTitle = source.readString();
			bean.newMsgTime = source.readString();
			bean.enterpriseImg = source.readString();
			bean.enterpriseName = source.readString();
			return bean;
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(enterpriseID);
		dest.writeString(followTime);
		dest.writeString(newMsgTitle);
		dest.writeString(newMsgTime);
		dest.writeString(enterpriseImg);
		dest.writeString(enterpriseName);
	}

	public String getFollowTime() {
		return followTime;
	}

	public void setFollowTime(String followTime) {
		this.followTime = followTime;
	}

	public String getNewMsgTitle() {
		return newMsgTitle;
	}

	public void setNewMsgTitle(String newMsgTitle) {
		this.newMsgTitle = newMsgTitle;
	}

	public String getNewMsgTime() {
		return newMsgTime;
	}

	public void setNewMsgTime(String newMsgTime) {
		this.newMsgTime = newMsgTime;
	}

	public String getEnterpriseID() {
		return enterpriseID;
	}

	public void setEnterpriseID(String enterpriseID) {
		this.enterpriseID = enterpriseID;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getEnterpriseImg() {
		return enterpriseImg;
	}

	public void setEnterpriseImg(String enterpriseImg) {
		this.enterpriseImg = enterpriseImg;
	}

}
