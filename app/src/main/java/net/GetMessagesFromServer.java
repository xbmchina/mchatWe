package net;



import com.project.chatwe.android.zero.chatwe.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.ChatMessge;


/**
 * Created by Administrator on 2016/9/7.
 */
public class GetMessagesFromServer {
    public GetMessagesFromServer(String phone_md5,String token,int page,int perpage,final SuccessCallback successCallback,final FailCallback failCallback) {
        new ChatServerConnection(Config.SEVER_URL, HttpMethod.POST, new ChatServerConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsob=new JSONObject(result);
                    switch (jsob.getInt(Config.STATUS_KEY)){
                        case Config.SUCCESS_STATUS:
                            if (successCallback!=null){
                            JSONArray jsonArray=jsob.getJSONArray(Config.TIMELINE_KEY);
                            JSONObject msgjsobj=new JSONObject();
                            List<ChatMessge> messges=new ArrayList<ChatMessge>();
                            for (int i=0;i<jsonArray.length();i++){
                                msgjsobj=jsonArray.getJSONObject(i);
                                messges.add(new ChatMessge(msgjsobj.getString(Config.MSG_ID_KEY),msgjsobj.getString(Config.MSG_KEY),msgjsobj.getString(Config.PHONE_MD5_KEY)));

                            }
                                successCallback.onSuccess(jsob.getInt(Config.PAGE_KEY), jsob.getInt(Config.PERPAGE_KEY),messges);
                            }
                        break;

                        case Config.INVAILD_STATUS:
                            if (failCallback!=null){
                                failCallback.onFail(Config.INVAILD_STATUS);
                            }

                            break;
                        default:
                            if (failCallback!=null){
                                failCallback.onFail(Config.FAIL_STATUS);
                            }
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback!=null){
                        failCallback.onFail(Config.FAIL_STATUS);
                    }
                }

            }
        }, new ChatServerConnection.FailCallback() {
            @Override
            public void onFail() {
                if (failCallback!=null){
                    failCallback.onFail(Config.FAIL_STATUS);
                }

            }
        },Config.ACTION_KEY,Config.ACTION_TIMELINE,
                Config.PHONE_MD5_KEY,phone_md5,
                Config.TOKEN_KEY,token,
                Config.PAGE_KEY,page+"",
                Config.PERPAGE_KEY,perpage+"");
    }


    public static interface SuccessCallback{
        void onSuccess(int page, int perpage, List<ChatMessge> timeline);
    }
    public static interface FailCallback{
        void onFail(int errorCode);
    }
}
