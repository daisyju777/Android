package com.example.cjgreen.a17dbhandling;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;

    TextView textView;

    Button button;
    Button button2;
    Button button3;
    Button button4;

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String databaseName = editText.getText().toString();
                createDatabase(databaseName);
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = editText2.getText().toString();
                createTable(tableName);

            }
        });

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = editText2.getText().toString();
                String name = editText3.getText().toString().trim();
                String ageStr = editText4.getText().toString().trim();
                String mobile = editText5.getText().toString().trim();
                int age = Integer.parseInt(ageStr);
                insertData(tableName, name, age, mobile);
            }
        });

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableName = editText2.getText().toString();
                selectData(tableName);
            }
        });

    }// onCreate()

    public void createDatabase(String databaseName){
        //데이터베이스를 생성하거나 열어주는 기능
        //다른 앱과 공유하는 경우를 제외하고는 프라이빗 모드를 사용
        println("데이터베이스생성 중...");
        database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        if(database != null) {
            println("데이터베이스가 오픈되었습니다.");
        }
    }

    public void createTable(String tableName){
        //테이블 생성
        println("테이블생성 호출");

        //데이터베이스 오픈
        if(database != null){
            String sql = "create table " + tableName + "( _id integer PRIMARY KEY autoincrement, name text, age integer, mobile text)";
            database.execSQL(sql);
            println("테이블 생성되었습니다.");
        }else {
            println("데이터베이스를 먼저 오픈해주세요.");
        }
    }

    public void insertData(String tableName, String name, int age, String mobile){
        if(database != null) {
            //String sql = "insert into " + tableName + "values(" + name + ", " + age + ", " + mobile + " )";
            String sql = "insert into " + tableName + "values (?, ?, ?)";
            Object[] params = {name, age, mobile};
            database.execSQL(sql, params);
        } else {
            println("먼저 데이터베이스를 오픈하세요");
        }

    }

    public void selectData(String tableName){
        if(database != null){
            String sql = "select name, age, mobile from " + tableName;
            //자료를 검색하여 읽어오기만 할때는 rawQuery 사용. 데이터베이스에 새로운 값을 저장할때는 execSQL 사용
            Cursor cursor = database.rawQuery(sql, null);
            //getCount()는 cursor 로 가져온 데이터의 수를 카운트해줌
            for(int i=0; i < cursor.getCount(); i++){
                cursor.moveToNext();
                //자료 물려온것 중에서 name은 0번 인덱스이므로 name값이 물려들어옴
                String name = cursor.getString(0);
                int age = cursor.getInt(1);
                String mobile = cursor.getString(2);
                println("#" + i + " : " + name+ "," + age + "," + mobile);
            }
            //커서 반납해줌
            cursor.close();
        }else {
            println("먼저 데이터베이스를 오픈하세요");
        }
    }

    //메시지를 띄우는 메소드 생성하기
    public void println(String data){
        textView.append(data + "\n");
    }
}// end of class MainActivity
