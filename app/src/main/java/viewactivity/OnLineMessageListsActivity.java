package viewactivity;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.project.chatwe.android.zero.chatwe.Config;
import com.project.chatwe.android.zero.chatwe.R;

import net.GetMessagesFromServer;
import net.UpLoadContacts;

import java.util.List;

import id.MyContacts;
import model.ChatMessge;
import model.OnLineMessageListAdapter;
import tools.MD5Tool;

/**
 * Created by Administrator on 2016/9/4.
 */
/*登录后，显示所有信息的Activity */
/*问题：xml文件中 id 为@android:id/list的作用，以及padding的作用*/
public class OnLineMessageListsActivity extends ListActivity {

    private String phone_num,token,phone_md5;
    private OnLineMessageListAdapter adapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagelists);
        adapter=new OnLineMessageListAdapter(this);
        setListAdapter(adapter);

        phone_num=getIntent().getStringExtra(Config.PHONE_NUM_KEY);
        token=getIntent().getStringExtra(Config.TOKEN_KEY);
        phone_md5= MD5Tool.md5(phone_num);

        final ProgressDialog pd=ProgressDialog.show(this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_sever));

        if ( MyContacts.getJSONStringOfContacts(this)!=null){
            new UpLoadContacts(phone_md5, token, MyContacts.getJSONStringOfContacts(this), new UpLoadContacts.SuccessCallback() {
                @Override
                public void onSuccess() {
                    LoadMessages();
                    pd.dismiss();


                }
            }, new UpLoadContacts.FailCallback() {
                @Override
                public void onFail(int status) {
                    pd.dismiss();
                    switch (status){
                        case Config.INVAILD_STATUS:
                            Toast.makeText(OnLineMessageListsActivity.this, R.string.token_invaild,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(OnLineMessageListsActivity.this, LoginActivity.class));//token无效后跳转到登录界面重新登录
                            finish();
                            break;

                        default:
                            Toast.makeText(OnLineMessageListsActivity.this, R.string.contacts_up_fail,Toast.LENGTH_SHORT).show();

                            break;
                    }
                }
            });

        }

        /*跳转到发布消息界面*/
        findViewById(R.id.btn_add_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(OnLineMessageListsActivity.this,PublishMessageActivity.class);
                i.putExtra(Config.PHONE_MD5_KEY,phone_md5);
                i.putExtra(Config.TOKEN_KEY,token);
                startActivityForResult(i,Config.ACTIVITY_REQUEST_CODE);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case Config.ACTIVITY_RESULTCODE_CODE:
                LoadMessages();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ChatMessge messge=adapter.getItem(position);
        Intent i=new Intent(OnLineMessageListsActivity.this, MessageContentActivity.class);
        i.putExtra(Config.MSG_KEY,messge.getMsg());
        i.putExtra(Config.PHONE_MD5_KEY,messge.getPhone_md5());
        i.putExtra(Config.MSG_ID_KEY,messge.getMsgId());
        i.putExtra(Config.TOKEN_KEY,token);
        startActivity(i);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.add_message_menu,menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onMenuItemSelected(int featureId, MenuItem item) {
//
//        return true;
//    }

    /* 下载相关信息  */
    private void LoadMessages(){
        final ProgressDialog pd=ProgressDialog.show(this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_sever));
        System.out.println("success,load message........>>>");
        new GetMessagesFromServer(phone_md5, token, 1, 20, new GetMessagesFromServer.SuccessCallback() {
            @Override
            public void onSuccess(int page, int perpage, List<ChatMessge> timeline) {
                pd.dismiss();
                adapter.clear();
                adapter.addAll(timeline);

            }
        }, new GetMessagesFromServer.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();

                if (errorCode==Config.INVAILD_STATUS){
                    startActivity(new Intent(OnLineMessageListsActivity.this,LoginActivity.class));
                    finish();
                }else
                {
                    Toast.makeText(OnLineMessageListsActivity.this, R.string.can_not_get_messages,Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
