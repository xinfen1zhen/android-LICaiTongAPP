package com.example.qimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class setting extends AppCompatActivity {

    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(setting.this,"account.db",null,1) {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        db_read = helper.getReadableDatabase();
        db_write = helper.getWritableDatabase();

        final EditText txtOld_password=findViewById(R.id.old_password);
        final EditText txtPwd=findViewById(R.id.new_password1);
        final EditText txtPwd2=findViewById(R.id.new_password2);

        final ImageButton btn_exit=findViewById(R.id.exit);
        final ImageButton btn_save=findViewById(R.id.btn_save);

        txtPwd2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return false;
                if (keyCode == event.KEYCODE_ENTER)
                {
                    KeyboardUtils.hideKeyboard(setting.this);
                }
                return false;
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(setting.this,main_menu.class);
                startActivity(intent1);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = txtOld_password.getText().toString();
                String pwd1 = txtPwd.getText().toString();
                String pwd2 = txtPwd2.getText().toString();
                String token=null;
                Cursor cursor = db_read.query("account1",new String[]{"id", "user","password"},"id=1",null,null,null,null);
                while (cursor.moveToNext())
                {
                    token = cursor.getString(cursor.getColumnIndex("password"));
                }
                if(token!=null)
                {
                    if(token.equals(old_password)) ;
                    else {Toast.makeText(setting.this,"原密码输入错误！",Toast.LENGTH_SHORT).show(); return;}
                }
                if(checkTrue(pwd1,pwd2))
                {
                    Toast.makeText(setting.this,"修改密码成功！",Toast.LENGTH_SHORT).show();

                    ContentValues contentValues=new ContentValues();
                    contentValues.put("password",pwd1);
                    db_write.update("account1", contentValues,"id=1",null);

                    Intent intent1=new Intent();
                    intent1.setClass(setting.this,main_menu.class);
                    startActivity(intent1);
                }
                else {Toast.makeText(setting.this,"两次密码输入不一致！",Toast.LENGTH_SHORT).show();}
            }
        });
    }
}
