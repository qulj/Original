package com.qlj.toolbox.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.qlj.toolbox.bean.Chat;
import com.qlj.toolbox.bean.ChatContent;
import com.qlj.toolbox.bean.ChatMenu;
import com.qlj.toolbox.util.CommonUtil;
import com.qlj.toolbox.util.Constants;
import com.qlj.toolbox.util.MJSONArray;
import com.qlj.toolbox.util.MJSONObject;
import com.qlj.toolbox.util.UUIDUtil;

import android.text.TextUtils;

public class ChatApi extends BaseApi {

	public static final String MENU_VIEW = "view";// 连接类型
	public static final String MENU_CLICK = "click";// 事件类型
	public static final String MSG_TYPE_NEWS = "news";// 图文类型
	public static final String MSG_TYPE_TEXT = "text";// 文本类型
	public static final String MSG_TYPE_SUBSCRIBE = "subscribe";// 订阅-关注类型
	public static final String MENUS_CMD = "/enterprise/createMenu.do";
	public static final String CHATS_CMD = "/enterprise/processRequest.do";

	/**
	 * 获取微信菜单
	 * 
	 * @param enterpriseID
	 *            商家id
	 * @param listener
	 */
	public static void getMenus(String enterpriseID, final IResponseListener listener) {
		final List<ChatMenu> list = new ArrayList<ChatMenu>();
		String[] keys = { "enterpriseID" };
		String[] values = { enterpriseID };

		try {
			post(MENUS_CMD, keys, values, new IRequestListener() {

				@Override
				public void onNetworkBusy(String msg) {
					listener.doFail(-1, msg);
				}

				@Override
				public void onComplete(JSONObject response, int code) throws JSONException {
					if (response != null) {
						MJSONObject json = new MJSONObject(response);

						if (json.getInt("status") == SUCCESS_RET) {
							MJSONArray jsonArr = json.getJSONArray("button");

							list.clear();
							for (int i = 0; i < jsonArr.length(); i++) {
								MJSONObject json1 = (MJSONObject) jsonArr.opt(i);
								ChatMenu chatMenu = new ChatMenu();
								chatMenu.setMenuId(json1.getString("key"));
								chatMenu.setMenuName(json1.getString("name"));
								chatMenu.setMenuType(json1.getString("type"));
								chatMenu.setUrl(json1.getString("url"));

								MJSONArray jsonArrSub = json1.getJSONArray("sub_button");
								if (jsonArrSub != null) {
									ArrayList<ChatMenu> listSub = new ArrayList<ChatMenu>();
									for (int j = 0; j < jsonArrSub.length(); j++) {
										MJSONObject jsonSub = (MJSONObject) jsonArrSub.opt(j);
										ChatMenu chatMenuSub = new ChatMenu();
										chatMenuSub.setMenuId(jsonSub.getString("key"));
										chatMenuSub.setMenuName(jsonSub.getString("name"));
										chatMenuSub.setMenuType(jsonSub.getString("type"));
										chatMenuSub.setUrl(jsonSub.getString("url"));
										listSub.add(chatMenuSub);
									}
									chatMenu.setMenus(listSub);
									if (listSub.size() > 0) {
										chatMenu.setLevelSize(2);
									}
								} else {
									chatMenu.setLevelSize(1);
								}
								list.add(chatMenu);
							}
							listener.doComplete(list, 0, "success");
						} else {
							listener.doFail(-2, json.getString("msg"));
						}
					}
				}
			});
		} catch (Exception e) {
			listener.doFail(-1, "Network busy!");
			e.printStackTrace();
		}
	}

	/**
	 * 获取回复内容
	 * 
	 * @param customerID
	 *            用户id
	 * @param enterpriseID
	 *            商家id
	 * @param eventKey
	 *            微信菜单key 可为空
	 * @param chatContent
	 *            用户发送内容 可为空
	 * @param listener
	 */
	public static void getChats(String customerID, final String enterpriseID, String eventKey, String eventType, String chatContent, final IResponseListener listener) {
		String[] keys = { "customerID", "enterpriseID", "eventKey", "eventType", "chatContent" };
		String[] values = { customerID, enterpriseID, eventKey, eventType, chatContent };

		try {
			post(CHATS_CMD, keys, values, new IRequestListener() {

				@Override
				public void onNetworkBusy(String msg) {
					listener.doFail(-1, msg);
				}

				@Override
				public void onComplete(JSONObject response, int code) throws JSONException {
					if (response != null) {
						MJSONObject json = new MJSONObject(response);
						if (json.getInt("status") == SUCCESS_RET) {
							Chat chat = null;
							
							String enterpriseImg = json.getString("enterpriseImg");
							long createTime = json.getLong("createTime");

							if (json.getString("msgType").equals(MSG_TYPE_NEWS)) {// 多图文和单图文
								MJSONArray jsonArr = json.getJSONArray("articles");
								if (jsonArr != null && jsonArr.length() > 0) {
									chat = new Chat();
									chat.setChatId(UUIDUtil.getUuid());
									chat.setChatTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
									chat.setUserId(CommonUtil.getUserId());
									chat.setMerchantId(enterpriseID);
									chat.setLogo(enterpriseImg);
									chat.setReqOrRes(Constants.TypeOfChat.RES.getValue());
									
									if (jsonArr.length() == 1) {
										chat.setTypeOfContent(Constants.TypeOfContent.TEXT_IMAGE.getValue());
									}
									if (jsonArr.length() > 1) {
										chat.setTypeOfContent(Constants.TypeOfContent.MORE_TEXT_IMAGE.getValue());
									}
									
									ArrayList<ChatContent> list = new ArrayList<ChatContent>();
									for (int i = 0; i < jsonArr.length(); i++) {
										MJSONObject json1 = (MJSONObject) jsonArr.opt(i);

										ChatContent content = new ChatContent();
										content.setChatId(chat.getChatId());
										content.setContentId(UUIDUtil.getUuid());
										content.setContentTitle(json1.getString("title"));
										content.setContentImage(json1.getString("picUrl"));
										content.setContentIntro(json1.getString("description"));
										content.setUrl(json1.getString("url"));
										content.setContentDate(new SimpleDateFormat("MM月dd日").format(new Date(createTime)));

										list.add(content);
									}
									chat.setChatContents(list);
									chat.setResSize(list.size());
								}
							} else if (json.getString("msgType").equals(MSG_TYPE_TEXT)) {// 纯文本
								if (!TextUtils.isEmpty(json.getString("content"))) {
									chat = new Chat();
									chat.setChatId(UUIDUtil.getUuid());
									chat.setChatTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
									chat.setUserId(CommonUtil.getUserId());
									chat.setMerchantId(enterpriseID);
									chat.setLogo(enterpriseImg);
									chat.setReqOrRes(Constants.TypeOfChat.RES.getValue());
									chat.setTypeOfContent(Constants.TypeOfContent.ONLY_TEXT.getValue());
									
									ArrayList<ChatContent> list = new ArrayList<ChatContent>();
									ChatContent content = new ChatContent();
									content.setChatId(chat.getChatId());
									content.setContentId(UUIDUtil.getUuid());
									content.setContentTitle(json.getString("content"));
									content.setContentDate(new SimpleDateFormat("MM月dd日").format(new Date(createTime)));
									list.add(content);
									
									chat.setChatContents(list);
									chat.setResSize(list.size());
								}
							}

							listener.doComplete(chat, 0, "success");
						} else {
							listener.doFail(-1, json.getString("msg"));
						}
					}
				}
			});
		} catch (Exception e) {
			listener.doFail(-1, "Network busy!");
			e.printStackTrace();
		}
	}

}
