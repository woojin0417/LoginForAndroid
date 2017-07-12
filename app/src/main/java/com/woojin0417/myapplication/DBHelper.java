package com.woojin0417.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과*/
        db.execSQL("CREATE TABLE MEMBERS1(name TEXT, ID TEXT, PassWord TEXT, PassWordSign TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String query) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL(query);
        db.close();
    }

    public void update(String email, String name) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE MEMBERS SET email=" + email + " WHERE name='" + name + "';");
        db.close();
    }

    public void delete(String query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        db.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MEMBERS", null);
        while (cursor.moveToNext()) {
            result += "name :"
                    + cursor.getString(0)
                    + "ID :"
                    + cursor.getString(1)
                    + "PassWord "
                    + cursor.getInt(2)
                    + "PassWordSign "
                    + cursor.getString(3)
                    + "\n";
        }

        return result;
    }

    public String currentName;

    public boolean isMatch(String id, String pw) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cur = db.rawQuery("SELECT * FROM MEMBERS", null);
        while (cur.moveToNext()) {
            if (id.equals(cur.getString(1))) {
                if (pw.equals(cur.getString(2))) {
                    currentName = cur.getString(0);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidate(String id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM MEMBERS", null);
        while (cursor.moveToNext())
        {
            if (id.equals(cursor.getString(1)))
            {
                return true; //아이디 중복
            }
        }
        return false;

    }

}





