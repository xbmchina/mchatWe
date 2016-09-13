package id;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;


import com.project.chatwe.android.zero.chatwe.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.MD5Tool;

/**
 * Created by Administrator on 2016/9/5.
 */
/*获取手机联系人电话号码，并转换成JSON数据，准备提交给服务器*/
public class MyContacts {

    public static String getJSONStringOfContacts(Context context){
        Cursor c=context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String phoneNum = null;
        JSONObject jsb = null;
        JSONArray jsArr=new JSONArray();

        while (c.moveToNext()){
            try {
                phoneNum=c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                jsb=new JSONObject();//必须在white循环内new一个新的实例，然而在外部会重复使用同一个内存（只会保持最新的数据），导致传入JSONArray数组的数据全是最后一个数据。
                if (phoneNum.charAt(0)=='+'&&phoneNum.charAt(1)=='8'&&phoneNum.charAt(2)=='6'){
                    phoneNum=phoneNum.substring(3);
                }
                if (phoneNum.length()>10){
                    jsb.put(Config.PHONE_MD5_KEY, MD5Tool.md5(phoneNum));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsArr.put(jsb);
//            System.out.println("dff" + jsArr.toString());
        }

        return jsArr.toString();
    }

}
