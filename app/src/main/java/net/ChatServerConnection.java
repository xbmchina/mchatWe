package net;

import android.os.AsyncTask;
import android.util.Log;


import com.project.chatwe.android.zero.chatwe.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/9/4.
 */
/*就是一个String类型的可变长度的数组,固定长度的数组是String[] str={};这样写,可变的就String... str.*/
    /*连接服务器，提交数据，读取数据类
    * */
public class ChatServerConnection {
    public ChatServerConnection(final String url,final HttpMethod method, final SuccessCallback successCallback, final FailCallback failCallback, final String...kvs) {
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {
                StringBuffer keyAndParam=new StringBuffer();
                for (int i=0;i<kvs.length;i+=2){
                    /* 设置好传入数据的格式：key=value&.....*/
                    keyAndParam.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
                }
                /* 写数据到服务器 */
                try {
                    URLConnection uc;
                    switch (method){

                        case POST:
                            /* POST方式写到服务器 */
                            uc = new URL(url).openConnection();
                            uc.setDoOutput(true);//允许往服务器写数据
                            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Config.CHARSET));
                            bw.write(keyAndParam.toString());
                            bw.flush();//刷新提交数据到目的地
                            break;
                        default:
                            uc=new URL(url+"?"+keyAndParam).openConnection();

                            break;
                    }

                    Log.d("ChatServerConnection","data:"+keyAndParam.toString());
                    Log.d("ChatServerConnection", "url:" + uc.getURL().toString());
                    /*读取服务器返回的数据,并且返回给其他类或Activity使用*/
                    BufferedReader br=new BufferedReader(new InputStreamReader(uc.getInputStream(),Config.CHARSET));
                    String line=null;
                    StringBuffer result=new StringBuffer();
                    while ((line=br.readLine())!=null){
                        result.append(line);
                    }
                    Log.d("ChatServerConnection","result:"+result.toString());
                    return result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
            /*在doInBackground执行完之后， return 语句进行返回时，这个方法就很快会被调用
            * 返回的数据 result.toString()会作为参数传递到此方法中
            * */
            @Override
            protected void onPostExecute(String s) {
                if (s!=null){
                    if (successCallback!=null){
                        successCallback.onSuccess(s);
                    }
                }else {
                    if (failCallback!=null){
                        failCallback.onFail();
                    }
                }

            }

        }.execute();

    }

    public static interface SuccessCallback{
        void onSuccess(String result);
    }

    public static interface FailCallback{
        void onFail();
    }

}
