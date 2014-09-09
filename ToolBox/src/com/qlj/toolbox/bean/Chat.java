package com.qlj.toolbox.bean;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 聊天
 * @comment Chat.java
 * @author XIER
 * @date 2014-6-23
 */
public class Chat implements Parcelable {
	private String chatId = "";
	private String chatTime = "";
	private ChatContent chatContent = new ChatContent();
	
	private String userId = "";
	private String merchantId = "";
	private String logo = "";
	private int reqOrRes = 0;//0-请求 1-回复
	private int resSize = 0;//回复消息数
	private int typeOfContent = -1;//类型 0-纯文字 1-单图文 2-多图文
	
	private ArrayList<ChatContent> chatContents = new ArrayList<ChatContent>();
	
	public Chat() {
		super();
	}
	
	public Chat(Parcel source) {
		chatId = source.readString();
		chatTime = source.readString();
		chatContent = source.readParcelable(ChatContent.class.getClassLoader());
		userId = source.readString();
		merchantId = source.readString();
		logo = source.readString();
		reqOrRes = source.readInt();
		resSize = source.readInt();
		typeOfContent = source.readInt();
		chatContents = new ArrayList<ChatContent>();
		source.readTypedList(chatContents, ChatContent.CREATOR);
	}

	public static final Parcelable.Creator<Chat> CREATOR = new Creator<Chat>() {
		
		@Override
		public Chat[] newArray(int size) {
			return new Chat[size];
		}
		
		@Override
		public Chat createFromParcel(Parcel source) {
//			Chat bean = new Chat();
//			bean.chatId = source.readString();
//			bean.chatTime = source.readString();
//			bean.chatContent = source.readParcelable(ChatContent.class.getClassLoader());
//			bean.userId = source.readString();
//			bean.merchantId = source.readString();
//			bean.reqOrRes = source.readInt();
//			bean.resSize = source.readInt();
			return new Chat(source);
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(chatId);
		dest.writeString(chatTime);
		dest.writeParcelable(chatContent, PARCELABLE_WRITE_RETURN_VALUE);
		dest.writeString(userId);
		dest.writeString(merchantId);
		dest.writeString(logo);
		dest.writeInt(reqOrRes);
		dest.writeInt(resSize);
		dest.writeInt(typeOfContent);
		dest.writeTypedList(chatContents);
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getChatTime() {
		return chatTime;
	}

	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}

	public ChatContent getChatContent() {
		return chatContent;
	}

	public void setChatContent(ChatContent chatContent) {
		this.chatContent = chatContent;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getReqOrRes() {
		return reqOrRes;
	}

	public void setReqOrRes(int reqOrRes) {
		this.reqOrRes = reqOrRes;
	}

	public int getResSize() {
		return resSize;
	}

	public void setResSize(int resSize) {
		this.resSize = resSize;
	}

	public int getTypeOfContent() {
		return typeOfContent;
	}

	public void setTypeOfContent(int typeOfContent) {
		this.typeOfContent = typeOfContent;
	}

	public ArrayList<ChatContent> getChatContents() {
		return chatContents;
	}

	public void setChatContents(ArrayList<ChatContent> chatContents) {
		this.chatContents = chatContents;
	}
	
}
