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
public class ChatMenu implements Parcelable {
	private String menuId = "";
	private String menuName = "";
	
	private String userId = "";
	private String merchantId = "";
	private String url = "";
	private int levelSize = 0;//回复消息数
	private String menuType = "";//view or click
	
	private ArrayList<ChatMenu> menus = new ArrayList<ChatMenu>();
	
	public ChatMenu() {
		super();
	}
	
	public ChatMenu(Parcel source) {
		menuId = source.readString();
		menuName = source.readString();
		userId = source.readString();
		merchantId = source.readString();
		url = source.readString();
		levelSize = source.readInt();
		menuType = source.readString();
		menus = new ArrayList<ChatMenu>();
		source.readTypedList(menus, ChatMenu.CREATOR);
	}

	public static final Parcelable.Creator<ChatMenu> CREATOR = new Creator<ChatMenu>() {
		
		@Override
		public ChatMenu[] newArray(int size) {
			return new ChatMenu[size];
		}
		
		@Override
		public ChatMenu createFromParcel(Parcel source) {
//			Chat bean = new Chat();
//			bean.menuId = source.readString();
//			bean.menuName = source.readString();
//			bean.chatContent = source.readParcelable(ChatContent.class.getClassLoader());
//			bean.userId = source.readString();
//			bean.merchantId = source.readString();
//			bean.reqOrRes = source.readInt();
//			bean.levelSize = source.readInt();
			return new ChatMenu(source);
		}
	};
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(menuId);
		dest.writeString(menuName);
		dest.writeString(userId);
		dest.writeString(merchantId);
		dest.writeString(url);
		dest.writeInt(levelSize);
		dest.writeString(menuType);
		dest.writeTypedList(menus);
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLevelSize() {
		return levelSize;
	}

	public void setLevelSize(int levelSize) {
		this.levelSize = levelSize;
	}

	public ArrayList<ChatMenu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<ChatMenu> menus) {
		this.menus = menus;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
	
}
