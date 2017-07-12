package com.woojin0417.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    //final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MEMBERS.db", null, 1);
    EditText idText, passwordText;
    Button lgButton, registerButton;
    LoginButton loginButton; //페북
    //CallbackManager callbackManager;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext()); //페북 초기화
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        lgButton = (Button) findViewById(R.id.lgButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (LoginButton) findViewById(R.id.loginButton); //페북버튼

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    //페북 버튼 클릭시
    public void fbLogin(View v) {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("토큰", loginResult.getAccessToken().getToken());
                        Log.e("유저아이디", loginResult.getAccessToken().getUserId());
                        Log.e("퍼미션 리스트", loginResult.getAccessToken().getPermissions() + "");

                        loginResult.getAccessToken(); //정보를 가지고 유저 정보를 가져올수 있습니다.
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            Log.e("user profile", object.toString());
                                            setResult(RESULT_OK);

                                            finish();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                }
        );


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void login(View v) {

        String id = idText.getText().toString();
        String password = passwordText.getText().toString();
        if (id.equals("")) {
            Toast.makeText(getApplicationContext(), "ID 를 입력하세요.", Toast.LENGTH_LONG).show();
            return;
        } else if (password.equals("")) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
            return;
        }
        if (BaseActivity.dbM.isMatch(id, password)) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("currentName", BaseActivity.dbM.currentName);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "ID 또는 Password를 확인해주세요.", Toast.LENGTH_LONG).show();
        }
    }

    public void goRegister(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onStop() {
        super.onStop();
    }
}
