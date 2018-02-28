package com.tachid.a58121090_3.selfielab;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button Register;
    EditText Email, PW1, PW2, Name;
    RequestQueue queue;



    HttpClient hc;
    HttpPost hp;
    HttpResponse hr;
    String url = "", data = "";
    BufferedReader br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        //hide bar
        getSupportActionBar().hide();



        //init element
        Register = (Button) findViewById(R.id.btnRegister);
        Email = (EditText) findViewById(R.id.edtEmail);
        PW1 = (EditText) findViewById(R.id.edtPass1);
        PW2 = (EditText) findViewById(R.id.edtPass2);
        Name = (EditText) findViewById(R.id.edtNickName);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (!(PW1.getText().toString().equals(PW1.getText().toString())) || PW1.getText().toString() == "") {
                    Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }


                HttpClient hc;
                HttpPost hp;
                HttpResponse hr;
                String url = "", data = "";
                BufferedReader br;


                hc = new DefaultHttpClient();

                hp = new HttpPost("http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/register");


                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("name", Name.getText().toString());
                    jsonObject.accumulate("EMAIL", Email.getText().toString());
                    jsonObject.accumulate("PW", PW1.getText().toString());
                    String json = jsonObject.toString();
                    StringEntity se = new StringEntity(json);
                    hp.setEntity(se);

                    hp.addHeader("content-type", "application/json");
                    hp.addHeader("cache-control", "no-cache");
                    hr = hc.execute(hp);
                    br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                    data = br.readLine();

                    if (data.equals("CREATED_USER")) {
                        // go to home
                        Toast.makeText(RegisterActivity.this,"Welcome "+Name.getText().toString(),Toast.LENGTH_LONG).show();
                        Intent ns = new Intent(RegisterActivity.this,HomeActivity.class);
                        ns.putExtra("name",Name.getText().toString());
                        ns.putExtra("Email",Email.getText().toString());
                        //save to pref

                        ns.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ns);
                    } else {
                        Toast.makeText(RegisterActivity.this,"This Email already In use",Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {

                }


            }

        });
    }
}

