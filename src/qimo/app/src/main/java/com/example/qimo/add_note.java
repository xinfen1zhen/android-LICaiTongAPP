package com.example.qimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

public class add_note extends AppCompatActivity {

    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(add_note.this,"note.db",null,1) {

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        db_read = helper.getReadableDatabase();
        db_write = helper.getWritableDatabase();

        final ImageButton btn_exit=findViewById(R.id.exit);
        final ImageButton btn_save=findViewById(R.id.btn_save);
        final EditText noteTxt=findViewById(R.id.note);

        noteTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return false;
                if (keyCode == event.KEYCODE_ENTER)
                {
                    KeyboardUtils.hideKeyboard(add_note.this);
                }
                return false;
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(add_note.this,note.class);
                startActivity(intent1);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues=new ContentValues();
                contentValues.put("content",noteTxt.getText().toString());
                db_write.insert("note1", null, contentValues);

                noteTxt.setText("");

                Toast.makeText(add_note.this,"保存成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
