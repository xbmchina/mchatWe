package net;



import com.project.chatwe.android.zero.chatwe.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/9.
 */
public class PublishComment {
    public PublishComment(String phone_md5,String token,String content,String msgId , final SuccessCallback successCallback, final FailCallback failCallback) {
        new ChatServerConnection(Config.SEVER_URL, HttpMethod.POST, new ChatServerConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonobj=new JSONObject(result);
                    switch (jsonobj.getInt(Config.STATUS_KEY)){
                        case Config.SUCCESS_STATUS:
                            if (successCallback!=null){
                                successCallback.onSuccess();
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
        },Config.ACTION_KEY,Config.ACTION_PUB_COMMENT,
                Config.PHONE_MD5_KEY,phone_md5,
                Config.TOKEN_KEY,token,
                Config.CONTENT_KEY,content,
                Config.MSG_ID_KEY,msgId);
    }


    public  static interface SuccessCallback{
        void onSuccess();
    }
    public static interface FailCallback{
        void onFail(int errorCode);
    }
}
