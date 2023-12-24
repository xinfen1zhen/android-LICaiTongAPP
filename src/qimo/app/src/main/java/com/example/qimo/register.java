package com.example.qimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(register.this,"account.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table account1(id integer primary key autoincrement,user text," +
                    "password text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    };

    boolean checkTrue(String pwd1, String pwd2)
    {
        if(pwd1.equals(pwd2)) {return true;}
        else {return false;}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db_read = helper.getReadableDatabase();
        db_write = helper.getWritableDatabase();

        final EditText txtUser=findViewById(R.id.txtuser);
        final EditText txtPwd=findViewById(R.id.txtpwd);
        final EditText txtPwd2=findViewById(R.id.txtpwd2);

        final Button btnLogin=findViewById(R.id.btlogin);
        final Button btnRegister=findViewById(R.id.btregister);
        final Button btnExit=findViewById(R.id.btexit);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(register.this,login.class);
                startActivity(intent1);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtUser.getText().toString();
                String pwd1 = txtPwd.getText().toString();
                String pwd2 = txtPwd2.getText().toString();
                if(checkTrue(pwd1,pwd2))
                {
                    Toast.makeText(register.this,"注册成功！",Toast.LENGTH_SHORT).show();

                    ContentValues contentValues=new ContentValues();
                    contentValues.put("user",user);
                    contentValues.put("password",pwd1);
                    db_write.insert("account1", null, contentValues);

                    Intent intent2=new Intent();
                    intent2.setClass(register.this,login.class);
                    intent2.putExtra("user",user);
                    startActivity(intent2);
                }
                else {Toast.makeText(register.this,"两次密码输入不一致！",Toast.LENGTH_SHORT).show();}
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register.this.finish();
            }
        });
    }
}
