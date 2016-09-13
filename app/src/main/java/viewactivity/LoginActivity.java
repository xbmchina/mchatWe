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

import net.GetPhoneToken;
import net.LoginNet;

import tools.MD5Tool;

/**
 * Created by Administrator on 2016/9/4.
 */

/*   登录界面以及相关验证码处理   */
public class LoginActivity extends Activity{
    private EditText etPhoneNum;
    private EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        etPhoneNum= (EditText) findViewById(R.id.etphonenumber);
        etPassword= (EditText) findViewById(R.id.etpassword);

        findViewById(R.id.btngetToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPhoneNum.getText())){
                    Toast.makeText(LoginActivity.this,R.string.phone_num_can_not_empty,Toast.LENGTH_LONG).show();
                    return;
                }
                final ProgressDialog pd=ProgressDialog.show(LoginActivity.this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_sever));

                new GetPhoneToken(etPhoneNum.getText().toString(), new GetPhoneToken.SuccessCallback() {
                    @Override
                    public void onSuccess() {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this,R.string.success_get_token,Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(LoginActivity.this,OnLineMessageListsActivity.class));
                    }
                }, new GetPhoneToken.FailCallback() {
                    @Override
                    public void onFail() {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this,R.string.fail_to_get_token,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



        findViewById(R.id.btnlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etPassword.getText())){
                    Toast.makeText(LoginActivity.this,R.string.token_can_not_empty,Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(etPhoneNum.getText())){
                    Toast.makeText(LoginActivity.this,R.string.phone_num_can_not_empty,Toast.LENGTH_LONG).show();
                    return;
                }

                /*还要判断验证码是否正确。*/

                new LoginNet(MD5Tool.md5(etPhoneNum.getText().toString()), etPassword.getText().toString(), new LoginNet.SuccessCallback() {
                    @Override
                    public void onSuccess(String token) {
                        //缓存token和phone
                        Config.cacheToken(LoginActivity.this, token);
                        Config.cachePhone(LoginActivity.this, etPhoneNum.getText().toString());

                        Intent i=new Intent(LoginActivity.this, OnLineMessageListsActivity.class);
                        i.putExtra(Config.TOKEN_KEY,token);
                        i.putExtra(Config.PHONE_NUM_KEY,etPhoneNum.getText().toString());
                        startActivity(i);
                        finish();

                    }
                }, new LoginNet.FailCallback() {
                    @Override
                    public void onFail() {
                        Toast.makeText(LoginActivity.this,R.string.can_not_login,Toast.LENGTH_LONG).show();

//                        Config.cacheToken(LoginActivity.this, "mmmsd");
//                        Config.cachePhone(LoginActivity.this, etPhoneNum.getText().toString());
//
//                        Intent i=new Intent(LoginActivity.this, OnLineMessageListsActivity.class);
//                        i.putExtra(Config.TOKEN_KEY,"mmmsd");
//                        i.putExtra(Config.PHONE_NUM_KEY,etPhoneNum.getText().toString());
//                        startActivity(i);
//                        finish();

                    }
                });

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
