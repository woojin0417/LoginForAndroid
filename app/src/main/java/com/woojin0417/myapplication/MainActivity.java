package com.woojin0417.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button logoutButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String str = getIntent().getStringExtra("currentName");
        Toast.makeText(getApplicationContext(), str + "님 환영합니다.", Toast.LENGTH_LONG).show();
        logoutButton=(Button)findViewById(R.id.logoutButton);
    }
    public void logOut(View v)
    {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
