package com.qlj.toolbox.request;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.qlj.toolbox.util.CommonUtil;
import com.qlj.toolbox.util.Logger;
import com.qlj.toolbox.util.NetworkUtil;

/**
 * 接口类
 * @author qlj
 * @time 2014年9月11日下午3:19:20
 */
public class ImplRequest extends BaseRequest {

	/**
	 * 登录接口
	 * 
	 * @param loginName
	 * @param loginPassword
	 * @param app_flag
	 *            APP标识 1:商家app，包括商家、商家职员可登陆 2:配送员app，配送员登陆
	 * @return
	 * @throws Exception
	 */
	public static JSONObject enterpriseLogin(String loginName, String loginPassword) throws Exception {
		return post("/user/enterpriseLogin.do", new String[] { "loginName", "loginPassword", "app_flag" }, new String[] { loginName, loginPassword, "1" });
	}

	public static JSONObject enterpriseLogin(String telphone, String pass, String flag) throws Exception {
		return post("/customer/customerRegist.do", new String[] { "telphone", "pass", "flag", "regSource" }, new String[] { telphone, pass, "1", "1" });
	}

	/**
	 * 重置密码
	 * 
	 * @param telphone
	 * @param newLoginPassword
	 * @param confirmPassword
	 * @return
	 * @throws Exception
	 */
	public static JSONObject resetLoginPassword(String telphone, String newLoginPassword, String confirmPassword) throws Exception {
		return post("/user/resetLoginPassword.do", new String[] { "telphone", "newLoginPassword", "confirmPassword" }, new String[] { telphone, newLoginPassword, confirmPassword });
	}

	/**
	 * 修改密码
	 * 
	 * @param oldLoginPassword
	 * @param newLoginPassword
	 * @param confirmPassword
	 * @return
	 * @throws Exception
	 */
	public static JSONObject modifyLoginPassword(String userID, String oldLoginPassword, String newLoginPassword, String confirmPassword) throws Exception {
		return post("/user/modifyLoginPassword.do", new String[] { "userID", "oldLoginPassword", "newLoginPassword", "confirmPassword" }, new String[] { userID, oldLoginPassword, newLoginPassword, confirmPassword });
	}

	/**
	 * 基本账户余额收银
	 * 
	 * @param userID
	 * @param pass
	 * @param consumeMoney
	 * @return
	 * @throws Exception
	 */
	public static JSONObject baseAccountPay(String userID, String pass, String consumeMoney) throws Exception {
		return post("/user/baseAccountPay.do", new String[] { "userID", "pass", "consumeMoney" }, new String[] { userID, pass, consumeMoney });
	}

	/**
	 * 根据验证码获取该用户的手机号
	 * 
	 * @param userID
	 * @param pass
	 * @return
	 * @throws Exception
	 */
	public static JSONObject checkWeiCardPass(String userID, String pass) throws Exception {
		return post("/user/checkWeiCardPass.do", new String[] { "userID", "pass" }, new String[] { userID, pass });
	}

	/**
	 * 基本账户余额收银明细
	 * 
	 * @param userID
	 * @param telphone
	 * @param consumeStartDate
	 * @param consumeEndDate
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	public static JSONObject weiCardCashDatail(String userID, String telphone, String consumeStartDate, String consumeEndDate, String pageSize, String currentPage) throws Exception {
		return post("/user/weiCardCashDatail.do", new String[] { "userID", "telphone", "consumeStartDate", "consumeEndDate", "pageSize", "currentPage" }, new String[] { userID, telphone, consumeStartDate, consumeEndDate, pageSize, currentPage });
	}

	/**
	 * 收银时验证 优惠券、团购、微会员
	 * 
	 * @param userID
	 *            用户id
	 * @param pass
	 *            验证码
	 * @return
	 * @throws Exception
	 */
	public static JSONObject checkOfCash(String userID, String pass) throws Exception {
		return post("/user/checkOfCash.do", new String[] { "userID", "pass" }, new String[] { userID, pass });
	}

	/**
	 * 优惠券、团购、微会员验证通过后使用
	 * 
	 * @param userID
	 *            用户id
	 * @param pass
	 *            验证码
	 * @param orderId
	 *            订单id
	 * @param consumeMoney
	 *            微会员使用金额，（优惠和团购传空）
	 * @return
	 * @throws Exception
	 */
	public static JSONObject useOfCash(String userID, String pass, String orderId, String consumeMoney) throws Exception {
		return post("/user/useOfCash.do", new String[] { "userID", "pass", "orderId", "consumeMoney" }, new String[] { userID, pass, orderId, consumeMoney });
	}

	/**
	 * 收银明细
	 * 
	 * @param userID
	 *            用户id
	 * @param passOrTelphone
	 *            验证码 或者手机号
	 * @param useStartDate
	 *            使用日期
	 * @param useEndDate
	 *            结束日期
	 * @param newsType
	 *            消息类型 会员卡是16， 优惠券是2， 团购券是3
	 * @param dayOrWeek
	 *            今日或者本周
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	public static JSONObject cassDatail(String userID, String passOrTelphone, String useStartDate, String useEndDate, String newsType, String dayOrWeek, String pageSize, String currentPage) throws Exception {
		return post("/user/cassDatail.do", new String[] { "userID", "passOrTelphone", "useStartDate", "useEndDate", "newsType", "dayOrWeek", "pageSize", "currentPage" }, new String[] { userID, passOrTelphone, useStartDate, useEndDate, newsType, dayOrWeek, pageSize, currentPage });
	}

	/**
	 * 商家再次广播订单时查找门店
	 * 
	 * @param userID
	 *            用户id
	 * @param storeName
	 *            店铺名称
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	public static JSONObject queryStoreList(String userID, String storeName, String productOrderId, String pageSize, String currentPage) throws Exception {
		return post("/user/queryStoreList.do", new String[] { "userID", "storeName", "productOrderId", "pageSize", "currentPage" }, new String[] { userID, storeName, productOrderId, pageSize, currentPage });
	}

	/**
	 * 商家订单
	 * 
	 * @param userID
	 *            用户id
	 * @param dayOrWeek
	 *            今日/本周
	 * @param orderNumber
	 *            订单号、发货方查询
	 * @param deliverStatus
	 *            抢单 、已接收、配送中、已完成
	 * @param startDate
	 *            日期查询：开始日期
	 * @param endDate
	 *            日期查询：结束日期
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	public static JSONObject enterpriseOrderList(String userID, String dayOrWeek, String orderNumber, String productOrderStatus, String startDate, String endDate, String pageSize, String currentPage, String colorNum) throws Exception {
		return post("/user/enterpriseOrderList.do", new String[] { "userID", "dayOrWeek", "orderNumber", "productOrderStatus", "startDate", "endDate", "pageSize", "currentPage", "colorNum" }, new String[] { userID, dayOrWeek, orderNumber, productOrderStatus, startDate, endDate, pageSize, currentPage, colorNum });
	}

	/**
	 * 最新消息数
	 * 
	 * @param userID
	 *            用户id
	 * @param lastRefreshTime
	 *            最后一次刷新时间
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getNewEnterpriseOrderCount(String userID, String lastRefreshTime) throws Exception {
		return post("/user/new_enterpriseOrderCount.do", new String[] { "userID", "lastRefreshTime" }, new String[] { userID, lastRefreshTime });
	}

	/**
	 * 取消订单
	 * 
	 * @param userID
	 * @param productOrderId
	 * @return
	 * @throws Exception
	 */
	public static JSONObject cancleOrder(String userID, String productOrderId) throws Exception {
		return post("/user/cancleOrder.do", new String[] { "userID", "productOrderId" }, new String[] { userID, productOrderId });
	}

	/**
	 * 自提 -- 提货
	 * 
	 * @param userID
	 *            用户id
	 * @param productOrderId
	 *            订单id
	 * @param pass
	 *            提货码
	 * @param pickUpUser
	 *            提货人
	 * @return
	 * @throws Exception
	 */
	public static JSONObject orderBySelf(String userID, String productOrderId, String pass, String pickUpUser) throws Exception {
		return post("/user/orderBySelf.do", new String[] { "userID", "productOrderId", "pass", "pickUpUser" }, new String[] { userID, productOrderId, pass, pickUpUser });
	}

	/**
	 * 发布订单
	 * 
	 * @param storeId
	 *            店铺id
	 * @param productNumber
	 *            订单编号
	 * @return
	 * @throws Exception
	 */
	public static JSONObject goPublish(String userID, String storeId, String productNumber) throws Exception {
		return post("/user/operationBroadcast.do", new String[] { "userID", "storeId", "productNumber" }, new String[] { userID, storeId, productNumber });
	}

	/**
	 * 商家订单详情分录信息
	 * 
	 * @param orderBillID
	 *            订单id
	 * @return
	 * @throws Exception
	 */
	public static JSONObject findProductOrderEntry(String orderBillID) throws Exception {
		return post("/productorder/findProductOrderEntry.do", new String[] { "orderBillID" }, new String[] { orderBillID });
	}

	/**
	 * 商品订单分录信息修改颜色块
	 * 
	 * @param userID
	 * @param entryID
	 * @param colorNum
	 * @return
	 * @throws Exception
	 */
	public static JSONObject modifyOrderEntryColor(String userID, String entryID, String colorNum) throws Exception {
		return post("/user/modifyOrderEntryColor.do", new String[] { "userID", "entryID", "colorNum" }, new String[] { userID, entryID, colorNum });
	}

	/**
	 * 商家订单详情
	 * 
	 * @param orderBillID
	 * @return
	 * @throws Exception
	 */
	public static JSONObject findMyProductOrderByID(String orderBillID) throws Exception {
		return post("/productorder/findMyProductOrderByID.do", new String[] { "orderBillID" }, new String[] { orderBillID });
	}

	/**
	 * 抢订单
	 * 
	 * @param userID
	 *            用户id
	 * @param productOrderId
	 * @return
	 * @throws Exception
	 */
	public static JSONObject acceptProductOrder(String userID, String productOrderId) throws Exception {
		return post("/user/acceptProductOrder.do", new String[] { "userID", "productOrderId" }, new String[] { userID, productOrderId });
	}

	/**
	 * 修改订单颜色值
	 * 
	 * @param userID
	 *            用户id
	 * @param productOrderId
	 *            订单id
	 * @param colorNum
	 *            颜色值
	 * @return
	 * @throws Exception
	 */
	public static JSONObject modifyOrderColor(String userID, String productOrderId, String colorNum) throws Exception {
		return post("/user/modifyOrderColor.do", new String[] { "userID", "productOrderId", "colorNum" }, new String[] { userID, productOrderId, colorNum });
	}

	/**
	 * 找配送
	 * 
	 * @param userID
	 *            用户端id
	 * @param productOrderId
	 *            商品订单id
	 * @param deliveryType
	 *            配送方式 0:广播配送 1:指定配送
	 * @param deliveryTelphone
	 *            配送电话
	 * @return
	 * @throws Exception
	 */
	public static JSONObject findDelivery(String userID, String productOrderId, String deliveryType, String deliveryTelphone) throws Exception {
		return post("/user/findDelivery.do", new String[] { "userID", "productOrderId", "deliveryType", "deliveryTelphone" }, new String[] { userID, productOrderId, deliveryType, deliveryTelphone });
	}

	/**
	 * 直接发货
	 * 
	 * @param userID
	 *            用户id
	 * @param productOrderId
	 *            商品订单id
	 * @return
	 * @throws Exception
	 */
	public static JSONObject deliverGoods(String userID, String productOrderId, String expressNumber) throws Exception {
		return post("/user/deliverGoods.do", new String[] { "userID", "productOrderId", "expressNumber" }, new String[] { userID, productOrderId, expressNumber });
	}

	/**
	 * 更新推送信息
	 * 
	 * @param customerID
	 * @param enterpriseID
	 * @param channelId
	 *            推送的通道id
	 * @param phoneUserId
	 *            推送的用户id
	 * @param deviceType
	 *            //发送类型 1: web 2: pc 3:android 4:ios 5:wp
	 * @return
	 * @throws Exception
	 */
	public static JSONObject setYunPushChannel(String userID, String channelId, String phoneUserId, String deviceType) throws Exception {
		return post("/user/setYunPushChannel.do", new String[] { "userID", "channelId", "phoneUserId", "deviceType" }, new String[] { userID, channelId, phoneUserId, deviceType });
	}

	/**
	 * 提交请求
	 * 
	 * @param cmd
	 * @param keys
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public static JSONObject post(String cmd, String[] keys, String[] values) throws Exception {
		String response = "";
		JSONObject jsonObject = null;

		if (!NetworkUtil.checkNetWork()) {
			response = "{\"status\":0,\"msg\":\"请检查网络连接\"}";
			jsonObject = new JSONObject(response);
			return jsonObject;
		}

		String param = "";
		for (int i = 0; i < values.length; i++) {
			param += keys[i] + "=" + values[i];
			if (i != values.length - 1)
				param += "&";
		}
		Logger.i("post params", CommonUtil.getApiUrl() + cmd + "?" + param);

		try {
			response = NetworkUtil.post(CommonUtil.getApiUrl() + cmd, keys, values);
			Logger.i(response);
		} catch (ConnectTimeoutException e) {
			response = "{\"status\":0,\"msg\":\"连接超时\"}";
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!TextUtils.isEmpty(response)) {
			jsonObject = new JSONObject(response);
		}
		return jsonObject;
	}

	public static String postOrder(String cmd, String[] keys, String[] values) throws Exception {
		String response = "";
		String param = "";
		for (int i = 0; i < values.length; i++) {
			param += keys[i] + "=" + values[i];
			if (i != values.length - 1)
				param += "&";
		}
		Logger.i("post params", CommonUtil.getApiUrl() + cmd + "?" + param);

		try {
			response = NetworkUtil.post(CommonUtil.getApiUrl() + cmd, keys, values);
			Logger.i(response);
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public interface CallbackListener {
		public void callback(int code, Object result);
	}
}
