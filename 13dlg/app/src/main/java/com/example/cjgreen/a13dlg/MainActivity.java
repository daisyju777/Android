package com.example.cjgreen.a13dlg;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("내가 제목!!");
                dlg.setMessage("요거이 메시지 내용입니다.");
                dlg.setIcon(R.mipmap.ic_launcher);
                dlg.setNegativeButton("취소",null);
                //확인버튼을 눌렀을때 어떠한 동작이 구현되도록 함
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "확인을 누르셨네요", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
            }
        });
    }
}
