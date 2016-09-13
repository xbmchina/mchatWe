package net;



import com.project.chatwe.android.zero.chatwe.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/4.
 */
/*获取手机验证码类*/
public class GetPhoneToken {
    public GetPhoneToken(String phoneNum, final SuccessCallback successCallback, final FailCallback failCallback) {

        new ChatServerConnection(Config.SEVER_URL, HttpMethod.POST, new ChatServerConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                JSONObject jb= null;
                try {
                    jb = new JSONObject(result);
                    switch (jb.getInt(Config.STATUS_KEY)){
                        case Config.SUCCESS_STATUS:
                            if (successCallback!=null){
                                successCallback.onSuccess();
                            }
                            break;
                        default:
                            if (failCallback!=null){
                                failCallback.onFail();
                            }
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
        },Config.ACTION_KEY, Config.ACTION_GET_TOKEN, Config.PHONE_NUM_KEY, phoneNum);//此处输入的数据为action:send_pass,phone:phoneNum
    }


    public  static interface  SuccessCallback{
        void onSuccess();
    }

    public  static interface  FailCallback{
        void onFail();
    }



}
