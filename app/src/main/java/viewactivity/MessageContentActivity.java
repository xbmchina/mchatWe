package viewactivity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.project.chatwe.android.zero.chatwe.Config;
import com.project.chatwe.android.zero.chatwe.R;

import net.GetComment;
import net.PublishComment;

import java.util.List;

import model.Comment;
import model.MessageCommentAdapterListener;
import tools.MD5Tool;

/**
 * Created by Administrator on 2016/9/4.
 */
public class MessageContentActivity extends ListActivity{
    private TextView tvmessage;
    private EditText etcomment;

    private String msg,msgId,phone_md5,token;
    private MessageCommentAdapterListener adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagecontent);
        msg=getIntent().getStringExtra(Config.MSG_KEY);
        msgId=getIntent().getStringExtra(Config.MSG_ID_KEY);
        phone_md5=getIntent().getStringExtra(Config.PHONE_MD5_KEY);
        token=getIntent().getStringExtra(Config.TOKEN_KEY);

        adapter=new MessageCommentAdapterListener(this);
        setListAdapter(adapter);

        tvmessage = (TextView) findViewById(R.id.tvmessage);
        etcomment= (EditText) findViewById(R.id.etcomment);
        tvmessage.setText(msg);
        showMessage();


        findViewById(R.id.sendcomment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd=ProgressDialog.show(MessageContentActivity.this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_sever));
                pd.dismiss();
                /*特别注意此处的MD5应该是接收方的*/
                if (TextUtils.isEmpty(etcomment.getText())){
                    Toast.makeText(MessageContentActivity.this, R.string.comment_can_not_empty, Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    new PublishComment(MD5Tool.md5(Config.getCachePhone(MessageContentActivity.this)), token, etcomment.getText().toString(), msgId, new PublishComment.SuccessCallback() {
                        @Override
                        public void onSuccess() {
                            showMessage();
                            etcomment.setText("");

                        }
                    }, new PublishComment.FailCallback() {
                        @Override
                        public void onFail(int errorCode) {
                            switch (errorCode){
                                case Config.INVAILD_STATUS:
                                    startActivity(new Intent(MessageContentActivity.this,LoginActivity.class));
                                    finish();
                                    break;
                                case Config.FAIL_STATUS:
                                    Toast.makeText(MessageContentActivity.this, R.string.fail_to_publish_comment, Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        }
                    });

                }


            }
        });

    }





    private void showMessage(){
        final ProgressDialog pd=ProgressDialog.show(this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_sever));
        new GetComment(phone_md5, token, 1, 20, msgId, new GetComment.SuccessCallback() {
            @Override
            public void onSuccess(String msgId, int page, int perpage, List<Comment> comment) {
                pd.dismiss();
                adapter.clear();
                adapter.addAll(comment);

            }
        }, new GetComment.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if (errorCode==Config.INVAILD_STATUS){
                    startActivity(new Intent(MessageContentActivity.this,LoginActivity.class));
                    finish();
                }else {
                    Toast.makeText(MessageContentActivity.this, R.string.comment_load_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
