package com.example.cjgreen.a16simpledb;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {
    // coding 1. 변수 선언
    String databaseName;// 데이터베이스이름
    String tableName; // 테이블이름
    TextView status;//데이터베이스상태
    boolean databaseCreated = false;//데이터베이스생성 체크를 위한 부울변수
    boolean tableCreated = false;

    SQLiteDatabase db;//SQLiteDatabase 객체 선언


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //coding2.
        final EditText databaseNameInput = (EditText) findViewById(R.id.databaseNameInput);
        final EditText tableNameInput = (EditText) findViewById(R.id.tableNameInput);

        Button createDatabaseBtn = (Button) findViewById(R.id.createDatabaseBtn); //데이터베이스 생성버튼 만들기
        createDatabaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseName = databaseNameInput.getText().toString();
                createDatabase(databaseName); //데이터베이스 생성
            }
        });

        Button createTableBtn = (Button) findViewById(R.id.createTableBtn);
        createTableBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tableName = tableNameInput.getText().toString();
                createTable(tableName);
                int count = insertRecord(tableName);

                println(count + " records inserted.");
            }
        });

        status = (TextView) findViewById(R.id.status);
    }//end of onCreate

    private void createDatabase(String name) {
        println("creating database [" + name + "].");

        //openOrCreateDatabase 메소드는 DB를 생성하거나 열어주는 메소드
        //1번째 패러미터는 데이터베이스 이름
        //2번째 패러미터는 사용모드 MODE_PRIVATE, MODE_WORLD_READABLE, MODE_WORLD_WRITABLE
        // MODE_WORLD_READABLE, MODE_WORLD_WRITABLE : 다른 애플리케이션에서도 이 데이터베이스에 대한 접근을 허용하겠다는 의미
        // 특별한 상황을 제외하고는 PRIVATE 으로 가는것이 맞음
        // 3번째 패러미터는 선택사항임. null이 아닌 객체를 지정할 경우에는 쿼리의 결과값으로 리턴되는 커서를 만들 객체가 전달된다.
        try {
            db = openOrCreateDatabase(
                    name,
                    Activity.MODE_PRIVATE,
                    null);

            databaseCreated = true;
            println("database is created.");
        } catch(Exception ex) {
            ex.printStackTrace();
            println("database is not created.");
        }
    }

    private void createTable(String name) {   //매개변수는 테이블 이름이 됨
        println("creating table [" + name + "].");

        db.execSQL("create table if not exists " + name + "("
                + " _id integer PRIMARY KEY autoincrement, "
                + " name text, "
                + " age integer, "
                + " phone text);" );

        tableCreated = true;
    }

    private int insertRecord(String name) {
        println("inserting records into table " + name + ".");

        int count = 3;
        db.execSQL( "insert into " + name + "(name, age, phone) values ('John', 20, '010-7788-1234');" );
        db.execSQL( "insert into " + name + "(name, age, phone) values ('Mike', 35, '010-8888-1111');" );
        db.execSQL( "insert into " + name + "(name, age, phone) values ('Sean', 26, '010-6677-4321');" );

        return count;
    }

    /**
     * insert records using parameters
     */
    private int insertRecordParam(String name) {
        println("inserting records using parameters.");

        int count = 1;
        ContentValues recordValues = new ContentValues();

        recordValues.put("name", "Rice");
        recordValues.put("age", 43);
        recordValues.put("phone", "010-3322-9811");
        int rowPosition = (int) db.insert(name, null, recordValues);

        return count;
    }

    /**
     * update records using parameters
     */
    private int updateRecordParam(String name) {
        println("updating records using parameters.");

        ContentValues recordValues = new ContentValues();
        recordValues.put("age", 43);
        String[] whereArgs = {"Rice"};

        int rowAffected = db.update(name,
                recordValues,
                "name = ?",
                whereArgs);

        return rowAffected;
    }

    /**
     * delete records using parameters
     */
    private int deleteRecordParam(String name) {
        println("deleting records using parameters.");

        String[] whereArgs = {"Rice"};

        int rowAffected = db.delete(name,
                "name = ?",
                whereArgs);

        return rowAffected;
    }

    private void println(String msg) {
        Log.d("MainActivity", msg);
        status.append("\n" + msg);

    }

}// end of class MainActivity
