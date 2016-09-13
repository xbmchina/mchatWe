package net;



import com.project.chatwe.android.zero.chatwe.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.Comment;


/**
 * Created by Administrator on 2016/9/9.
 */
public class GetComment {
    public GetComment(String phone_md5,String token,int page,int perpage,String msgId, final SuccessCallback successCallback, final FailCallback failCallback) {
        new ChatServerConnection(Config.SEVER_URL, HttpMethod.POST, new ChatServerConnection.SuccessCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                   switch (jsonObject.getInt(Config.STATUS_KEY)){
                       case Config.SUCCESS_STATUS:
                           JSONArray jsonArray=jsonObject.getJSONArray(Config.COMMENT_KEY);
                           List<Comment> comments=new ArrayList<>();
                           for (int i=0;i<jsonArray.length();i++){
                               JSONObject jsonComment=jsonArray.getJSONObject(i);
                               comments.add(new Comment(jsonComment.getString(Config.CONTENT_KEY),jsonComment.getString(Config.PHONE_MD5_KEY)));
                           }
                           if (successCallback!=null){
                               successCallback.onSuccess(jsonObject.getString(Config.MSG_ID_KEY),
                                       jsonObject.getInt(Config.PAGE_KEY),
                                       jsonObject.getInt(Config.PERPAGE_KEY),
                                       comments
                                       );
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
        },Config.ACTION_KEY,Config.ACTION_GET_COMMENT,
                Config.PHONE_MD5_KEY,phone_md5,
                Config.TOKEN_KEY,token,
                Config.PAGE_KEY,page+"",
                Config.PERPAGE_KEY,perpage+"",
                Config.MSG_ID_KEY,msgId);
    }

    public  static interface SuccessCallback{
        void onSuccess(String msgId, int page, int perpage, List<Comment> comment);
    }
    public static interface FailCallback{
        void onFail(int errorCode);
    }
}
