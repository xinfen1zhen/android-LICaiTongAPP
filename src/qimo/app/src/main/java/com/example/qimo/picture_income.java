
package com.example.qimo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

public class picture_income extends AppCompatActivity {

    SQLiteDatabase db_read,db_write;
    SQLiteOpenHelper helper=new SQLiteOpenHelper(picture_income.this,"income.db",null,1) {
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table income1(id integer primary key autoincrement,money float," +
                    "time text,category text,person text,note text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    };

    String time="[";
    String money="[";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_income);

        final ImageButton btn_exit=findViewById(R.id.exit);

        db_read = helper.getReadableDatabase();
        db_write = helper.getWritableDatabase();

        Cursor cursor = db_read.query("income1", new String[]{"id", "money","time","category","person","note"},
                null, null, null, null, null);

        //判断游标是否为空
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                time=time+"'"+cursor.getString(cursor.getColumnIndex("time"))+"',";
                money=money+"'"+cursor.getString(cursor.getColumnIndex("money"))+"',";
                cursor.moveToNext();
            }
        }

        time=time+"]";
        money=money+"]";
        final WebView webView=findViewById(R.id.webview);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);

        class MyWebviewClient extends WebViewClient
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println(time);
                System.out.println(money);
                webView.loadUrl("javascript:doCreatChart('bar'," +
                        money + "," + time + ",'收入图表一览'" +
                        ");");
            }
        }

        webView.loadUrl("file:///android_asset/picture.html");
        webView.setWebViewClient(new MyWebviewClient());

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(picture_income.this,income.class);
                startActivity(intent1);
            }
        });
    }
}
