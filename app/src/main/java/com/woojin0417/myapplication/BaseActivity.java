package com.woojin0417.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {
    public static DBHelper dbM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        dbM = new DBHelper(getApplicationContext(), "woojin.db", null, 1);
        dbM.delete("DELETE FROM MEMBERS");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);


    }

}

