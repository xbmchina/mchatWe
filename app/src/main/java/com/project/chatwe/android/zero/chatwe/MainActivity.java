package com.project.chatwe.android.zero.chatwe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import viewactivity.LoginActivity;
import viewactivity.OnLineMessageListsActivity;

public class MainActivity extends AppCompatActivity {

    private String token,phone_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token=Config.getCacheToken(this);
        phone_num=Config.getCachePhone(this);
        if (token!=null&&phone_num!=null){
            Intent i=new Intent(this, OnLineMessageListsActivity.class);
            i.putExtra(Config.PHONE_NUM_KEY,phone_num);
            i.putExtra(Config.TOKEN_KEY,token);
            startActivity(i);
        }else {
            Intent i=new Intent(this, LoginActivity.class);
            startActivity(i);
        }
        finish();
    }
}





