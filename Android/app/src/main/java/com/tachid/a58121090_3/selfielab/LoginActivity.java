package com.tachid.a58121090_3.selfielab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {


    Button Login;
    EditText Email,PW;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init element
        Login = (Button) findViewById(R.id.btnlogin) ;
        Email= (EditText) findViewById(R.id.edtEmail) ;
        PW= (EditText) findViewById(R.id.edtPass) ;

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ns = new Intent( LoginActivity.this,HomeActivity.class );
                startActivity(ns);

            }
        });








    }
}
