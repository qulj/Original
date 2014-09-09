package com.qlj.toolbox.util;

/**
 * 枚举常量类
 * @author qlj
 * @time 2014年9月3日下午2:33:04
 */
public class Constants {

	/**
	 * 聊天类型
	 */
	public enum TypeOfChat {
		REQ(0) { public String getState () { return "请求"; } },
		RES(1) { public String getState () { return "回复"; } };
		
		public abstract String getState();
		
		private int value = 0;
		TypeOfChat(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 聊天消息内容
	 */
	public enum TypeOfContent {
		ONLY_TEXT(0) { public String getState () { return "纯文字"; } },
		TEXT_IMAGE(1) { public String getState () { return "单图文"; } },
		MORE_TEXT_IMAGE(2) { public String getState () { return "多图文"; } };
		
		public abstract String getState();
		
		private int value = 0;
		TypeOfContent(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 预约状态
	 */
	public enum ReservationState {
		ON_HOLD(0) { public String getState () { return "待处理"; } },
		CONFIRMED(1) { public String getState () { return "已确认"; } },
		CANCELED(2) { public String getState () { return "已取消"; } },
		EXPIRED(3) { public String getState () { return "已过期"; } };
		
		public abstract String getState();
		
		private int value = 0;
		ReservationState(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 团购状态
	 */
	public enum GrouponState {
		WAIT_PAYMENT(0) { public String getState () { return "待付款"; } },
		UNUSED(1) { public String getState () { return "未使用"; } },
		USED(2) { public String getState () { return "已使用"; } },
		EXPIRED(3) { public String getState () { return "已过期"; } };
		
		public abstract String getState();
		
		private int value = 0;
		GrouponState(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 礼品卡状态
	 */
	public enum GiftCardState {
		WAIT_PAYMENT(0) { public String getState () { return "待付款"; } },
		UNUSED(1) { public String getState () { return "可使用"; } },
		USED(2) { public String getState () { return "已用完"; } },
		EXPIRED(3) { public String getState () { return "已过期"; } };
		
		public abstract String getState();
		
		private int value = 0;
		GiftCardState(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 活动状态
	 */
	public enum ActivityState {
		PARTICIPATED(0) { public String getState () { return "已参加"; } },
		UNRECEIVED(1) { public String getState () { return "待领取"; } },
		RECEIVED(2) { public String getState () { return "已领取"; } };
		
		public abstract String getState();
		
		private int value = 0;
		ActivityState(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 订单业务类型
	 */
	public enum OrderBusinessState {
		PRODUCT_ORDER(0) { public String getName () { return "产品订单"; } },
		GROUPON_ORDER(1) { public String getName () { return "团购订单"; } },
		GIFTCARD_ORDER(2) { public String getName () { return "礼品卡订单"; } };
		
		public abstract String getName();
		
		private int value = 0;
		OrderBusinessState(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 分享
	 */
	public enum ShareType {
		SHARE_SINO(1) { public String getName () { return "分享到新浪微博"; } },
		SHARE_QQ(2) { public String getName () { return "分享到qq"; } },
		SHARE_TENCENT_WEIBO(3) { public String getName () { return "分享到腾讯微博"; } },
		SHARE_WEBCHAT_SESSION(4) { public String getName () { return "分享到微信会话"; } },
		SHARE_WEBCHAT_ONLINE(5) { public String getName () { return "分享到微信朋友圈"; } },
		SHARE_OTHER(6) { public String getName () { return "分享到其它"; } };
		
		public abstract String getName();
		
		private int value = 0;
		ShareType(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
}
