package com.example.qimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class manage_note extends AppCompatActivity {

    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(manage_note.this,"note.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    };

    int tmpID=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_note);

        Intent intent2 = this.getIntent();
        Bundle bundle = intent2.getExtras();
        if(bundle != null){tmpID=bundle.getInt("id");}

        db_read = helper.getReadableDatabase();
        db_write = helper.getWritableDatabase();

        final ImageButton btn_exit=findViewById(R.id.exit);
        final ImageButton btn_change=findViewById(R.id.btn_change);
        final ImageButton btn_delete=findViewById(R.id.btn_delete);
        final EditText noteTxt=findViewById(R.id.note);

        if(tmpID!=-1){
            Cursor cursor = db_read.query("note1", new String[]{"id","content"},
                    "id="+tmpID, null, null, null, null);

            while (cursor.moveToNext())
            {
                noteTxt.setText(cursor.getString(cursor.getColumnIndex("content")));
            }
        }

        noteTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return false;
                if (keyCode == event.KEYCODE_ENTER)
                {
                    KeyboardUtils.hideKeyboard(manage_note.this);
                }
                return false;
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(manage_note.this,note.class);
                startActivity(intent1);
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues=new ContentValues();
                contentValues.put("content",noteTxt.getText().toString());
                db_write.update("note1",contentValues,"id="+tmpID,null);

                Intent intent1=new Intent();
                intent1.setClass(manage_note.this,note.class);
                startActivity(intent1);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_write.delete("note1","id="+tmpID,null);

                Intent intent1=new Intent();
                intent1.setClass(manage_note.this,note.class);
                startActivity(intent1);
            }
        });
    }
}
