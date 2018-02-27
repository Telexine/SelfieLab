package com.tachid.a58121090_3.selfielab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {




    Button Login,Register;
    ImageView iv;
    TextView msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //hide bar
        getSupportActionBar().hide();

        //init element

        Login = (Button) findViewById(R.id.btnlogin) ;
        Register = (Button) findViewById(R.id.btnRegister) ;
        iv= (ImageView)  findViewById(R.id.avartar) ;
        msg= (TextView)  findViewById(R.id.tvMsg) ;


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ns = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(ns);
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ns = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(ns);

            }
        });

    }
}
