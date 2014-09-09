package com.qlj.toolbox.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 聊天内容
 * @comment ChatContent.java
 * @author XIER
 * @date 2014-6-23
 */
public class ChatContent implements Parcelable {

	private String id = "";
	private String chatId = "";
	private String contentId = "";
	private String contentTitle = "";
	private String contentImage = "";
	private String contentIntro = "";
	private String contentDate = "";
	private String url = "";
	private int typeOfBusiness = -1;//业务类型
	
	public ChatContent() {
		super();
	}

	public ChatContent(String id, String contentId, String contentTitle, String contentImage, String contentIntro, String contentDate, int typeOfBusiness, int typeOfContent) {
		super();
		this.id = id;
		this.contentId = contentId;
		this.contentTitle = contentTitle;
		this.contentImage = contentImage;
		this.contentIntro = contentIntro;
		this.contentDate = contentDate;
		this.typeOfBusiness = typeOfBusiness;
	}

	public static final Parcelable.Creator<ChatContent> CREATOR = new Creator<ChatContent>() {
		
		@Override
		public ChatContent[] newArray(int size) {
			return new ChatContent[size];
		}
		
		@Override
		public ChatContent createFromParcel(Parcel source) {
			ChatContent bean = new ChatContent();
			bean.id = source.readString();
			bean.chatId = source.readString();
			bean.contentId = source.readString();
			bean.contentTitle = source.readString();
			bean.contentImage = source.readString();
			bean.contentIntro = source.readString();
			bean.contentDate = source.readString();
			bean.url = source.readString();
			bean.typeOfBusiness = source.readInt();
			return bean;
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(chatId);
		dest.writeString(contentId);
		dest.writeString(contentTitle);
		dest.writeString(contentImage);
		dest.writeString(contentIntro);
		dest.writeString(contentDate);
		dest.writeString(url);
		dest.writeInt(typeOfBusiness);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentImage() {
		return contentImage;
	}

	public void setContentImage(String contentImage) {
		this.contentImage = contentImage;
	}

	public String getContentIntro() {
		return contentIntro;
	}

	public void setContentIntro(String contentIntro) {
		this.contentIntro = contentIntro;
	}

	public String getContentDate() {
		return contentDate;
	}

	public void setContentDate(String contentDate) {
		this.contentDate = contentDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTypeOfBusiness() {
		return typeOfBusiness;
	}

	public void setTypeOfBusiness(int typeOfBusiness) {
		this.typeOfBusiness = typeOfBusiness;
	}
	
}
