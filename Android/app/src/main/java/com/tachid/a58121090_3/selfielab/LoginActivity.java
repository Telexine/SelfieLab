package com.tachid.a58121090_3.selfielab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;


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



                HttpClient hc;
                HttpPost hp;
                HttpResponse hr;
                String url = "", data = "";
                BufferedReader br;


                hc = new DefaultHttpClient();

                hp = new HttpPost("http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/login");


                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("EMAIL", Email.getText().toString());
                    jsonObject.accumulate("PW", PW.getText().toString());
                    String json = jsonObject.toString();
                    StringEntity se = new StringEntity(json);
                    hp.setEntity(se);

                    hp.addHeader("content-type", "application/json");
                    hp.addHeader("cache-control", "no-cache");
                    hr = hc.execute(hp);
                    br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                    data = br.readLine();

                    if (!data.equals("LOGIN_FAIL")) {
                        // pass
                        Toast.makeText(LoginActivity.this,"Welcome "+data,Toast.LENGTH_LONG).show();
                        Intent ns = new Intent(LoginActivity.this,HomeActivity.class);
                        ns.putExtra("Email",Email.getText().toString());
                        ns.putExtra("name", data);

                        startActivity(ns);
                    } else {
                        Toast.makeText(LoginActivity.this,"Email or Password not match",Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {

                }




            }
        });








    }
}
