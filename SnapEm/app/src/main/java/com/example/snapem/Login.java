package com.example.snapem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    Button btn_sign,btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_sign = (Button) findViewById(R.id.btn_sign);
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Login.this,Signup.class);
//                startActivity(i);
            }
        });

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Context context = getApplicationContext();
//                Intent intent = new Intent(context, MyForegroundServices.class); // Build the intent for the service
//                context.startForegroundService(intent);

                Intent i = new Intent(Login.this,Home.class);
                startActivity(i);
            }
        });
    }
}