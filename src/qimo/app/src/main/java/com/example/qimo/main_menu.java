package com.example.qimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class main_menu extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final ImageButton btn_income=findViewById(R.id.income);
        final ImageButton btn_outcome=findViewById(R.id.outcome);
        final ImageButton btn_help=findViewById(R.id.help);
        final ImageButton btn_exit=findViewById(R.id.exit);
        final ImageButton btn_note=findViewById(R.id.note);
        final ImageButton btn_setting=findViewById(R.id.setting);

        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(main_menu.this,help.class);
                startActivity(intent1);
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(main_menu.this,login.class);
                startActivity(intent1);
            }
        });

        btn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(main_menu.this,income.class);
                startActivity(intent1);
            }
        });

        btn_outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(main_menu.this,outcome.class);
                startActivity(intent1);
            }
        });

        btn_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(main_menu.this,note.class);
                startActivity(intent1);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(main_menu.this,setting.class);
                startActivity(intent1);
            }
        });
    }
}
