package net;



import com.project.chatwe.android.zero.chatwe.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/5.
 */
/*登录的通信类，往服务器提交手机的MD5值和验证码，让服务器判断两者是否正确*/
public class LoginNet {
    public LoginNet(String phone_md5,String code, final SuccessCallback successCallback, final FailCallback failCallback) {
        new ChatServerConnection(Config.SEVER_URL, HttpMethod.POST, new ChatServerConnection.SuccessCallback() {
            @Override
            public void onSuccess(String token) {
                try {
                    JSONObject jb=new JSONObject(token);
                    switch (jb.getInt(Config.STATUS_KEY)){
                        case 1:
                            if (successCallback!=null){
                                successCallback.onSuccess(jb.getString(Config.TOKEN_KEY));//如果成功，则返回安全标识码
                            }
                            break;
                        default:
                            if (failCallback!=null)
                                failCallback.onFail();
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if (failCallback!=null){
                        failCallback.onFail();
                    }
                }


            }
        }, new ChatServerConnection.FailCallback() {
            @Override
            public void onFail() {
                if (failCallback!=null){
                    failCallback.onFail();
                }

            }
        },Config.ACTION_KEY,Config.ACTION_LOGIN,Config.PHONE_MD5_KEY,phone_md5,Config.CODE_KEY,code);
    }


    public static interface SuccessCallback{
        void onSuccess(String token);
    }

    public static interface  FailCallback{
        void onFail();
    }
}
