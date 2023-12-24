package com.example.qimo;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class note extends AppCompatActivity {
    LinearLayout layout;
    EditText editText;

    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(note.this,"note.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table note1(id integer primary key autoincrement," +
                    "content text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        Cursor cursor = db_read.query("note1", new String[]{"id", "content"},
                null, null, null, null, null);

        //判断游标是否为空
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                TextView textView=new TextView(this);
                textView.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
                textView.setText("|  "+cursor.getString(cursor.getColumnIndex("id"))+"  |  "+
                        cursor.getString(cursor.getColumnIndex("content"))+"  |");
                textView.setGravity(Gravity.CENTER);
                layout.addView(textView);

                cursor.moveToNext();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        db_read = helper.getReadableDatabase();
        db_write = helper.getWritableDatabase();

        layout=findViewById(R.id.linearLayout);

        final ImageButton btn_exit=findViewById(R.id.exit);
        final ImageButton btn_add_income=findViewById(R.id.add_note);
        final ImageButton btn_manager=findViewById(R.id.manager);

        editText=findViewById(R.id.searchText);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return false;
                if (keyCode == event.KEYCODE_ENTER)
                {
                    int searchTxt=Integer.valueOf(editText.getText().toString());
                    TextView textView=new TextView(note.this);
                    TextView oldtextView=findViewById(searchTxt);
                    if(oldtextView != null)
                    {
                        textView.setText(oldtextView.getText());
                        textView.setGravity(Gravity.CENTER);
                        layout.removeAllViews();
                        layout.addView(textView);
                    }
                    else {
                        Toast.makeText(note.this,"错误的序号！",Toast.LENGTH_SHORT).show();}
                    KeyboardUtils.hideKeyboard(note.this);
                }
                return false;
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(note.this,main_menu.class);
                startActivity(intent1);
            }
        });

        btn_add_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(note.this,add_note.class);
                startActivity(intent1);
            }
        });

        btn_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int searchTxt=Integer.valueOf(editText.getText().toString());
                Intent intent1=new Intent();
                intent1.setClass(note.this,manage_note.class);
                intent1.putExtra("id",searchTxt);
                startActivity(intent1);
            }
        });
    }
}
