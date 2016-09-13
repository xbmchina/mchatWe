package viewactivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.chatwe.android.zero.chatwe.Config;
import com.project.chatwe.android.zero.chatwe.R;

import net.PublishMessage;


/**
 * Created by Administrator on 2016/9/4.
 */
public class PublishMessageActivity extends Activity {
    private EditText etContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publishmessage);
        etContent= (EditText) findViewById(R.id.etpublish);


        findViewById(R.id.btnpublish).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etContent.getText())){
                    Toast.makeText(PublishMessageActivity.this, R.string.message_can_not_empty, Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    final ProgressDialog pd=ProgressDialog.show(PublishMessageActivity.this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_sever));
                    new PublishMessage(getIntent().getStringExtra(Config.PHONE_MD5_KEY), getIntent().getStringExtra(Config.TOKEN_KEY),
                            etContent.getText().toString(), new PublishMessage.SuccessCallback() {
                        @Override
                        public void onSuccess() {
                            pd.dismiss();
                            setResult(Config.ACTIVITY_RESULTCODE_CODE);
                            Toast.makeText(PublishMessageActivity.this, R.string.success_to_publish_message, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }, new PublishMessage.FailCallback() {
                        @Override
                        public void onFail(int errorCode) {
                                pd.dismiss();
                            if (errorCode==Config.INVAILD_STATUS){
                                startActivity(new Intent(PublishMessageActivity.this, LoginActivity.class));
                                finish();
                            }else{
                                Toast.makeText(PublishMessageActivity.this, R.string.fail_to_publish_message, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }


            }
        });
    }

}
