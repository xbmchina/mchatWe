package net;



import com.project.chatwe.android.zero.chatwe.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/6.
 */

/*    上传联系人到服务器类    */
public class UpLoadContacts {

    public UpLoadContacts(String phone_md5,String token,String contacts, final SuccessCallback successCallback, final FailCallback failCallback){
        new ChatServerConnection(Config.SEVER_URL, HttpMethod.POST, new ChatServerConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jb=new JSONObject(result);
                    switch (jb.getInt(Config.STATUS_KEY)){//从连接的JSON数据中查看是否成功。
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
        },Config.ACTION_KEY,Config.ACTION_UPLOD_CONTACTS,Config.PHONE_MD5_KEY,phone_md5,Config.TOKEN_KEY,token,Config.CONTACTS_KEY,contacts);

    }


    public static interface SuccessCallback{
        void onSuccess();
    }
    public static interface FailCallback{
        void onFail(int status);//由于于有两种情况，第一种是失败，第二种是token过期
    }

}
