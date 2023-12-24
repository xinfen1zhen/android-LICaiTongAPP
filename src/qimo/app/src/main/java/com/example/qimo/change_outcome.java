package com.example.qimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class change_outcome extends AppCompatActivity {

    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(change_outcome.this,"outcome.db",null,1) {
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
        setContentView(R.layout.activity_change_outcome);

        Intent intent2 = this.getIntent();
        Bundle bundle = intent2.getExtras();
        if(bundle != null){tmpID=bundle.getInt("id");}

        db_read = helper.getReadableDatabase();
        db_write = helper.getWritableDatabase();

        final ImageButton btn_exit=findViewById(R.id.exit);
        final ImageButton btn_save=findViewById(R.id.btn_save);

        final EditText moneyTxt=findViewById(R.id.editText1);
        final EditText timeTxt=findViewById(R.id.editText2);
        final EditText categoryTxt=findViewById(R.id.editText3);
        final EditText personTxt=findViewById(R.id.editText4);
        final EditText noteTxt=findViewById(R.id.editText5);

        if(tmpID!=-1){
            Cursor cursor = db_read.query("outcome1", new String[]{"id", "money","time","category","person","note"},
                    "id="+tmpID, null, null, null, null);

            while (cursor.moveToNext())
            {
                moneyTxt.setText(cursor.getString(cursor.getColumnIndex("money")));
                timeTxt.setText(cursor.getString(cursor.getColumnIndex("time")));
                categoryTxt.setText(cursor.getString(cursor.getColumnIndex("category")));
                personTxt.setText(cursor.getString(cursor.getColumnIndex("person")));
                noteTxt.setText(cursor.getString(cursor.getColumnIndex("note")));
            }
        }


        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(change_outcome.this,outcome.class);
                startActivity(intent1);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(change_outcome.this,outcome.class);
                startActivity(intent1);

                ContentValues contentValues=new ContentValues();
                //contentValues.put("id",id.getText().toString());
                contentValues.put("time",timeTxt.getText().toString());
                contentValues.put("category",categoryTxt.getText().toString());
                contentValues.put("person",personTxt.getText().toString());
                contentValues.put("note",noteTxt.getText().toString());
                if(moneyTxt.length() != 0)
                {
                    contentValues.put("money",Float.valueOf(moneyTxt.getText().toString()));
                    //salary.setText("1");
                }
                else
                {
                    contentValues.put("money",0);
                    //salary.setText("-1");
                }

                db_write.update("outcome1",contentValues,"id="+tmpID,null);
            }
        });
    }
}
