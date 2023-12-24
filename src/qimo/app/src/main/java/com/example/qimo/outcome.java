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

public class outcome extends AppCompatActivity {
    LinearLayout layout;
    EditText editText;

    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(outcome.this,"outcome.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table outcome1(id integer primary key autoincrement,money float," +
                    "time text,category text,person text,note text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        Cursor cursor = db_read.query("outcome1", new String[]{"id", "money","time","category","person","note"},
                null, null, null, null, null);

        //判断游标是否为空
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                TextView textView=new TextView(this);
                textView.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
                textView.setText("|  "+cursor.getString(cursor.getColumnIndex("id"))+"  |  "+
                        cursor.getInt(cursor.getColumnIndex("money"))+"  |  "+
                        cursor.getString(cursor.getColumnIndex("time"))+"  |  "+
                        cursor.getString(cursor.getColumnIndex("category"))+"  |  "+
                        cursor.getString(cursor.getColumnIndex("person"))+"  |  "+
                        cursor.getString(cursor.getColumnIndex("note"))+"  |");
                textView.setGravity(Gravity.CENTER);
                layout.addView(textView);

                cursor.moveToNext();
            }
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcome);

        db_read = helper.getReadableDatabase();
        db_write = helper.getWritableDatabase();

        layout=findViewById(R.id.linearLayout);

        final ImageButton btn_exit=findViewById(R.id.exit);
        final ImageButton btn_add=findViewById(R.id.btn_add);
        final ImageButton btn_delete=findViewById(R.id.btn_delete);
        final ImageButton btn_change=findViewById(R.id.btn_change);
        final ImageButton btn_picture=findViewById(R.id.btn_picture);

        editText=findViewById(R.id.searchText);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return false;
                if (keyCode == event.KEYCODE_ENTER)
                {
                    int searchTxt=Integer.valueOf(editText.getText().toString());
                    TextView textView=new TextView(outcome.this);
                    TextView oldtextView=findViewById(searchTxt);
                    if(oldtextView != null)
                    {
                        textView.setText(oldtextView.getText());
                        textView.setGravity(Gravity.CENTER);
                        layout.removeAllViews();
                        layout.addView(textView);
                    }
                    else {
                        Toast.makeText(outcome.this,"错误的序号！",Toast.LENGTH_SHORT).show();}
                    KeyboardUtils.hideKeyboard(outcome.this);
                }
                return false;
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(outcome.this,main_menu.class);
                startActivity(intent1);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(outcome.this,add_outcome.class);
                startActivity(intent1);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int searchTxt=Integer.valueOf(editText.getText().toString());
                db_write.delete("outcome1","id="+searchTxt,null);
                layout.removeAllViews();
                onResume();
                editText.setText("");
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int searchTxt=Integer.valueOf(editText.getText().toString());
                Intent intent1=new Intent();
                intent1.setClass(outcome.this,change_outcome.class);
                intent1.putExtra("id",searchTxt);
                startActivity(intent1);
            }
        });

        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(outcome.this,picture_outcome.class);
                startActivity(intent1);
            }
        });
    }
}