package com.project.chatwe.android.zero.chatwe;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/9/4.
 */
public class Config {

//    public static final String SEVER_URL="http://demo.eoeschool.com/api/v1/nimings/io";
    public static final String SEVER_URL="http://172.25.178.1:8080/TestChatApplication/ChatApi.jsp";
    public static final String APP_ID="chatapplication";

    public static final String TOKEN_KEY="token";
    public static final String PHONE_NUM_KEY="phone";
    public static final String STATUS_KEY="status";
    public static final String PHONE_MD5_KEY ="phone_md5";
    public static final String CODE_KEY ="code" ;
    public static final String ACTION_KEY="action";
    public static final String PERPAGE_KEY="perpage";
    public static final String CONTACTS_KEY="contacts";
    public static final String PAGE_KEY="page";
    public static final String COMMENT_KEY="comment";


    public static final String TIMELINE_KEY="timeline";
    public static final String CONTENT_KEY="content";
    public static final String ACTION_GET_TOKEN="send_pass";
    public static final String  ACTION_TIMELINE="timeline";


    public static final String ACTION_UPLOD_CONTACTS="upload_contacts";
    public static final String ACTION_LOGIN="login";
    public static final String ACTION_GET_COMMENT="get_comment";
    public static final int SUCCESS_STATUS=1;
    public static final int FAIL_STATUS=0;
    public static final int INVAILD_STATUS=2;
    public static final String CHARSET="UTF-8";
    public static final String MSG_ID_KEY="msgId";
    public static final String MSG_KEY="msg";
    public static final String ACTION_PUB_COMMENT="pub_comment";
    public static final String PUBLISH_ACTION="publish"
            ;
    public static final int  ACTIVITY_RESULTCODE_CODE=45;
    public static final int ACTIVITY_REQUEST_CODE=445;

    //获取缓存中的token，以及缓存token
    public static String getCacheToken(Context context){
        SharedPreferences sp=context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE);
        return  sp.getString(TOKEN_KEY,null);

    }

    public static  void cacheToken(Context context,String token){
        SharedPreferences.Editor e=context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(TOKEN_KEY,token);
        e.commit();
    }

//获取缓存中的phonenumber，以及缓存phone
    public static String getCachePhone(Context context){
        SharedPreferences sp=context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE);
        return  sp.getString(PHONE_NUM_KEY,null);

    }

    public static  void cachePhone(Context context,String phoneNum){
        SharedPreferences.Editor e=context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(PHONE_NUM_KEY,phoneNum);
        e.commit();
    }



}
