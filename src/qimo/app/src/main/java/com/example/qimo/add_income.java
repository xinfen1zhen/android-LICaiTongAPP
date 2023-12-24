package com.example.qimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class add_income extends AppCompatActivity {

    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(add_income.this,"income.db",null,1) {

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
        setContentView(R.layout.activity_add_income);

        db_read = helper.getReadableDatabase();
        db_write = helper.getWritableDatabase();

        final ImageButton btn_exit=findViewById(R.id.exit);
        final ImageButton btn_save=findViewById(R.id.btn_save);

        final EditText moneyTxt=findViewById(R.id.editText1);
        final EditText timeTxt=findViewById(R.id.editText2);
        final EditText categoryTxt=findViewById(R.id.editText3);
        final EditText personTxt=findViewById(R.id.editText4);
        final EditText noteTxt=findViewById(R.id.editText5);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(add_income.this,income.class);
                startActivity(intent1);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                db_write.insert("income1", null, contentValues);

                moneyTxt.setText("");
                timeTxt.setText("");
                categoryTxt.setText("");
                personTxt.setText("");
                noteTxt.setText("");

                Toast.makeText(add_income.this,"保存成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
