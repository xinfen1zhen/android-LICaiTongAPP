package com.example.qimo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText txtUser=findViewById(R.id.txtuser);
        final EditText txtPwd=findViewById(R.id.txtpwd);

        final Button btnLogin=findViewById(R.id.btlogin);
        final Button btnRegister=findViewById(R.id.btregister);
        final Button btnExit=findViewById(R.id.btexit);

        Intent intent2 = this.getIntent();
        Bundle bundle = intent2.getExtras();
        if(bundle != null){txtUser.setText(bundle.getString("user"));}

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(login.this,"登录成功！",Toast.LENGTH_SHORT).show();

                Intent intent1=new Intent();
                intent1.setClass(login.this,main_menu.class);
                startActivity(intent1);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(login.this,register.class);
                startActivity(intent1);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.this.finish();
            }
        });
    }
}
