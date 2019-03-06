package com.yundingweibo.utils;

import com.yundingweibo.restDemo.client.AbsRestClient;
import com.yundingweibo.restDemo.client.JsonReqClient;

/**
 * Description:
 *
 * @author 关栋伟
 * @date 2019/03/03
 */
public class SandSmsUtils {
    static AbsRestClient InstantiationRestAPI() {
	   return new JsonReqClient();
    }

    public static void testSendSms(String sid, String token, String appid, String templateid, String param, String mobile, String uid){
	   try {
		  String result=InstantiationRestAPI().sendSms(sid, token, appid, templateid, param, mobile, uid);
		  System.out.println("Response content is: " + result);
	   } catch (Exception e) {
		  e.printStackTrace();
	   }
    }

    public static void testSendSmsBatch(String sid, String token, String appid, String templateid, String param, String mobile, String uid){
	   try {
		  String result=InstantiationRestAPI().sendSmsBatch(sid, token, appid, templateid, param, mobile, uid);
		  System.out.println("Response content is: " + result);
	   } catch (Exception e) {
		  e.printStackTrace();
	   }
    }

    public static void testAddSmsTemplate(String sid, String token, String appid, String type, String template_name, String autograph, String content){
	   try {
		  String result=InstantiationRestAPI().addSmsTemplate(sid, token, appid, type, template_name, autograph, content);
		  System.out.println("Response content is: " + result);
	   } catch (Exception e) {
		  e.printStackTrace();
	   }
    }


    public static void testGetSmsTemplate(String sid, String token, String appid, String templateid, String page_num, String page_size){
	   try {
		  String result=InstantiationRestAPI().getSmsTemplate(sid, token, appid, templateid, page_num, page_size);
		  System.out.println("Response content is: " + result);
	   } catch (Exception e) {
		  e.printStackTrace();
	   }
    }


    public static void testEditSmsTemplate(String sid, String token, String appid, String templateid, String type, String template_name, String autograph, String content){
	   try {
		  String result=InstantiationRestAPI().editSmsTemplate(sid, token, appid, templateid, type, template_name, autograph, content);
		  System.out.println("Response content is: " + result);
	   } catch (Exception e) {
		  e.printStackTrace();
	   }
    }


    public static void testDeleterSmsTemplate(String sid, String token, String appid, String templateid){
	   try {
		  String result=InstantiationRestAPI().deleterSmsTemplate(sid, token, appid, templateid);
		  System.out.println("Response content is: " + result);
	   } catch (Exception e) {
		  e.printStackTrace();
	   }
    }


}
