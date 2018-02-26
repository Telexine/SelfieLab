package com.tachid.a58121090_3.selfielab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    Button Register;
    EditText Email,PW1,PW2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //init element
        Register = (Button) findViewById(R.id.btnRegister) ;
        Email= (EditText) findViewById(R.id.edtEmail) ;
        PW1= (EditText) findViewById(R.id.edtPass1) ;
        PW2= (EditText) findViewById(R.id.edtPass2) ;
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ns = new Intent( RegisterActivity.this,HomeActivity.class );
                startActivity(ns);

            }
        });

    }
}
