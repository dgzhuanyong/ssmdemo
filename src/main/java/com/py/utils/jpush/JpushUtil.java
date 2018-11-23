package com.py.utils.jpush;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.WinphoneNotification;

/**
 * 
 * @content 极光推送工具类
 *
 * @author cl
 *
 * 2018年11月20日
 */
public class JpushUtil {
	
	
	/**
	 * 应用信息
	 * AppKey 
	 * Master Secret 
	 */
	private static  String AppKey = "68eba99dd947275f5e5789db";
	private static  String MasterSecret = "0e8979d5f59d5d7c9d89149d";
	protected static final Logger logger = LoggerFactory.getLogger(JpushUtil.class);
	
	/*****************************************alert通知封装**********************************************************/
	
	
	/**
	 * 全平台所有设备alert通知（未开启WinPhone）
	 */
	public static void pushToAllAlert(String message) {
		JPushClient client = new JPushClient(MasterSecret, AppKey, null, ClientConfig.getInstance());
		PushPayload payload = buildPushObject_all_all_alert(message);
		try {
			PushResult result = client.sendPush(payload);
			client.close();
			logger.info("Got result - " + result);

		} catch (APIConnectionException e) {
			logger.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
		}
	}
	
	
	/**
	 * 全平台别名alert通知（未开启WinPhone） 
	 */
	public static void pushToAliasAlert(String message, String... alias) {
		JPushClient client = new JPushClient(MasterSecret, AppKey, null, ClientConfig.getInstance());
		// 所有平台别名推送
		PushPayload payload = buildPushObject_all_alias_alert(message, alias);
		try {
			PushResult result = client.sendPush(payload);
			client.close();
			logger.info("Got result - " + result);

		} catch (APIConnectionException e) {
			logger.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
		}
	}
	
	/**
	 * 全平台别名alert通知,附加Map类型参数（未开启WinPhone）
	 */
	public static void pushToAliasExtrasAlert(String message,Map<String, String> extras,String... alias) {
		JPushClient client = new JPushClient(MasterSecret, AppKey, null, ClientConfig.getInstance());
		// 所有平台别名推送
		PushPayload payload = buildPushObject_all_alias_Extras_alert(message, extras, alias);
		try {
			PushResult result = client.sendPush(payload);
			client.close();
			logger.info("Got result - " + result);

		} catch (APIConnectionException e) {
			logger.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
		}
	}
	
	

	/*****************************************message消息封装**********************************************************/
	
	/**
	 * 全平台所有设备message自定义消息（未开启WinPhone）
	 */
	public static void pushToAllMessage(String message) {
		JPushClient client = new JPushClient(MasterSecret, AppKey, null, ClientConfig.getInstance());
		PushPayload payload = buildPushObject_all_all_message(message);
		try {
			PushResult result = client.sendPush(payload);
			client.close();
			logger.info("Got result - " + result);

		} catch (APIConnectionException e) {
			logger.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
		}
	}
	
	
	/**
	 * 全平台别名message自定义消息（未开启WinPhone） 
	 */
	public static void pushToAliasMessage(String message, String... alias) {
		JPushClient client = new JPushClient(MasterSecret, AppKey, null, ClientConfig.getInstance());
		// 所有平台别名推送
		PushPayload payload = buildPushObject_all_alias_message(message, alias);
		try {
			PushResult result = client.sendPush(payload);
			client.close();
			logger.info("Got result - " + result);

		} catch (APIConnectionException e) {
			logger.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
		}
	}
	
	/**
	 * 全平台别名message自定义消息,附加Map类型参数（未开启WinPhone）
	 */
	public static void pushToAliasExtrasMessage(String message,Map<String, String> extras,String... alias) {
		JPushClient client = new JPushClient(MasterSecret, AppKey, null, ClientConfig.getInstance());
		// 所有平台别名推送
		PushPayload payload = buildPushObject_all_alias_Extras_message(message, extras, alias);
		try {
			PushResult result = client.sendPush(payload);
			client.close();
			logger.info("Got result - " + result);

		} catch (APIConnectionException e) {
			logger.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
		}
	}

	/***************************************jpush推送对象alert封装******************************************/
	
	/***
	 * 快捷地构建推送对象：所有平台，所有设备，内容为  message的通知
	 */
	public static PushPayload buildPushObject_all_all_alert(String message) {
		return PushPayload.alertAll(message);
	}
	
	
	/**
	 * 构建推送对象：所有平台，推送目标是别名为 alias，内容为 message的通知。
	 */
	public static PushPayload buildPushObject_all_alias_alert(String message, String... alias) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(message)).build();
	}
	
	
	/**
	 * 构建推送对象：所有平台，推送目标是别名为 alias ，内容为 message ，附加参数 Map<String, String> extras的通知
	 */
	public static PushPayload buildPushObject_all_alias_Extras_alert(String message,Map<String, String> extras,String... alias){
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.alias(alias))
				.setNotification(
						Notification.newBuilder()
							.addPlatformNotification(
								IosNotification.newBuilder()  
                                .setAlert(message)
                                .setSound("happy.caf")
                                .addExtras(extras).build())
							.addPlatformNotification(  
	                            AndroidNotification.newBuilder()  
                                .setAlert(message)  
                                .addExtras(extras) 
                                .build())  
	                        .addPlatformNotification(  
	                                WinphoneNotification.newBuilder()  
	                                        .setAlert(message)  
	                                        .addExtras(extras).build())
	                        .build()
	             ).setOptions(Options.newBuilder().setApnsProduction(true).setApnsProduction(false).build()).build();
	}
	

	/***************************************jpush推送对象message封装******************************************/
	
	
	/***
	 * 快捷地构建推送对象：所有平台，所有设备，内容为  message的自定义消息
	 */
	public static PushPayload buildPushObject_all_all_message(String message) {
		return PushPayload.messageAll(message);
	}
	
	
	/**
	 * 构建推送对象：所有平台，推送目标是别名为 alias，内容为 message的自定义消息。
	 */
	public static PushPayload buildPushObject_all_alias_message(String message, String... alias) {
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.alias(alias))
				.setMessage(Message.content(message))
				.build();
	}
	
	
	/**
	 * 构建推送对象：所有平台，推送目标是别名为 alias ，自内容为 message ，附加参数 Map<String, String> extras 的自定义消息。
	 */
	public static PushPayload buildPushObject_all_alias_Extras_message(String message,Map<String, String> extras,String... alias){
		return PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.alias(alias))
				.setMessage(
					 Message.newBuilder()
	                .setMsgContent(message)
	                .addExtras(extras)
	                .build()
                ).setOptions(Options.newBuilder().setApnsProduction(true).setApnsProduction(false).build()).build();
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		/*****************alert通知*******************/
		
		//全部alert通知 
		//pushToAllAlert("这里是需要填写的内容");
		
		//别名alert通知
		//pushToAliasAlert("这里是需要填写的内容", "A747EB968671CB1554AF026821A3F535");
		
		//别名 带参数推送
		/*String[] alias = {"A747EB968671CB1554AF026821A3F535"};
		Map<String, String> extras = Maps.newHashMap();
		extras.put("type", "4");
		extras.put("pushCode", 1+"");
		pushToAliasExtrasAlert("这里是需要填写的内容", extras, alias);*/
		
		/*****************自定义消息*******************/
		
		//全部自定义消息
		//pushToAllMessage("这里是需要填写的内容");
		
		//别名自定义消息
		//pushToAliasMessage("这里是需要填写的内容", "CF731649D654CFFBAC28EA44E4DD0379");
		
		//别名自定义消息带参数
		/*String[] alias = {"80E39A719C0CFCE3B84304125A0BCCDE"};
		Map<String, String> extras = Maps.newHashMap();
		extras.put("type", "4");
		extras.put("pushCode", 1+"");
		pushToAliasExtrasMessage("这里是需要填写的内容111", extras, alias);*/

	}

}
