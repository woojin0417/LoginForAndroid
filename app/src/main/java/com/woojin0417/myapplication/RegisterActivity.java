package com.woojin0417.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText idText, nameText,passwordText,passwordSignText;
    boolean validate=false;
    AlertDialog dialog;
    Button validateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        idText = (EditText) findViewById(R.id.idText);
        nameText = (EditText) findViewById(R.id.nameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        passwordSignText = (EditText) findViewById(R.id.passwordSignText);
        validateButton=(Button)findViewById(R.id.validateButton);
    }
    public void goValidate(View v)
    {
        String id = idText.getText().toString();
        boolean flag = true;
        Pattern idP = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
        if(!idP.matcher(id).find()){
            Toast.makeText(getApplicationContext(), "아이디 다시 입력하세요", Toast.LENGTH_LONG).show();
            flag = false;
            return;
        }
        if(validate)
            return;
        if(id.equals(""))
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(RegisterActivity.this);
            dialog=builder.setMessage("아이디는 빈칸 일 수 없습니다").setPositiveButton("확인",null).create();
            dialog.show();
            return;
        }
        if(BaseActivity.dbM.isValidate(id))
        {
            Toast.makeText(getApplicationContext(), "이미 사용중인 아이디 입니다. 다시 입력하세요", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "사용하실 수 있는 아이디 입니다", Toast.LENGTH_LONG).show();
            idText.setEnabled(false);
            validate=true;
            idText.setBackgroundColor(getResources().getColor(R.color.colorGray));
            validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
        }
    }
    public void join(View v){
        boolean flag = true;

        String name = nameText.getText().toString();
        String id = idText.getText().toString();
        String password = passwordText.getText().toString();
        String passSign = passwordSignText.getText().toString();


        Pattern nameP = Pattern.compile("^[a-zA-z가-힣]{2,6}$");
        Pattern passwordP = Pattern.compile("^[a-z0-9]{5,15}$");
        Pattern passSignP = Pattern.compile("^[a-z0-9]{5,15}$");

        if(id.equals(""))
        {
            Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_LONG).show();
            flag=false;
            return;
        }
        if(name.equals(""))
        {
            Toast.makeText(getApplicationContext(), "이름을 입력하세요", Toast.LENGTH_LONG).show();
            flag=false;
            return;
        }
        else if(!name.equals("")) {
            if (!nameP.matcher(name).find()) {
                Toast.makeText(getApplicationContext(), "이름 다시 입력하세요", Toast.LENGTH_LONG).show();
                flag = false;
                return;
            }
            else
            {
                nameText.setBackgroundColor(getResources().getColor(R.color.colorGray));
            }
        }
        if(password.equals(""))
        {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
            flag=false;
            return;
        }
        else {
            if (!passwordP.matcher(password).find()) {
                Toast.makeText(getApplicationContext(), "비밀번호는 영어소문자와 숫자 조합 입니다", Toast.LENGTH_LONG).show();
                flag = false;
                return;
            }
            else {
                if(passSign.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "비밀번호를 한번 더 입력하세요", Toast.LENGTH_LONG).show();
                    flag=false;
                    return;
                }
                else if (!passSign.equals(password)) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다", Toast.LENGTH_LONG).show();
                    flag = false;
                    return;
                }
                else
                {
                    passwordText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    passwordSignText.setBackgroundColor(getResources().getColor(R.color.colorGray));
                }
            }
        }
        if(validate==false)
        {
            Toast.makeText(getApplicationContext(), "아이디 중복 체크를 하세요", Toast.LENGTH_LONG).show();
            flag=false;
            return;
        }

        if(flag){
            BaseActivity.dbM.insert("INSERT INTO MEMBERS VALUES('"+name+"', '"+id+"', '"+password+"', '"+passSignP+"');");

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            Toast.makeText(getApplicationContext(), "회원가입을 축하합니다! 로그인화면으로 돌아갑니다", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
    }

    public  void goLogin(View v){

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        Toast.makeText(getApplicationContext(), "회원가입이 취소되었습니다.", Toast.LENGTH_LONG).show();
        startActivity(intent);

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if(dialog!=null)
        {
            dialog.dismiss();
            dialog=null;
        }
    }
}


