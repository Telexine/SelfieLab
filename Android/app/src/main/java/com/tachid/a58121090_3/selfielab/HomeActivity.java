package com.tachid.a58121090_3.selfielab;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {


    ViewStub vH, vP, vS;
    ImageView Photo;

    Button btnHcam,Cal;

    String _IMG,_Email,_Name;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vH.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Bundle extras = getIntent().getExtras();

        _Email = (String) extras.get("Email");
        _Name = (String) extras.get("name");

        //home
        vH = (ViewStub) findViewById(R.id.vsHome);
        vH.setLayoutResource(R.layout.layout_home);
        vH.inflate();

        btnHcam = (Button) findViewById(R.id.btnCam);
        Cal = (Button) findViewById(R.id.btnCalculate);
        Photo = (ImageView) findViewById(R.id.ivResult);

        Cal.setVisibility(View.INVISIBLE);
        btnHcam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();
            }
        });
        Cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String cal_URL= "http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/facelab";



                uploaduserimage();














/*
                HttpClient hc;
                HttpPost hp;
                HttpResponse hr;
                String url = "", data = "";
                BufferedReader br;


                hc = new DefaultHttpClient();

                hp = new HttpPost("http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/facelab");
                String sURL = "http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/facelab";

                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("image",_IMG);
                    jsonObject.accumulate("email", _Email);
                    String json = jsonObject.toString();
                    StringEntity se = new StringEntity(json);
                    hp.setEntity(se);

                    hp.addHeader("content-type", "application/json");
                    hp.addHeader("cache-control", "no-cache");
                    hr = hc.execute(hp);
                    br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                    data = br.readLine();



                } catch (Exception e) {

                }


*/

            }
        });





        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            _IMG =_function.encodeImage(imageBitmap);
            Cal.setVisibility(View.VISIBLE);
            Photo.setImageBitmap(imageBitmap);
        }
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }




    public void uploaduserimage(){

        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/facelab", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("NO_FACE_FOUND")){
                    Toast.makeText(HomeActivity.this, "No Face Found on this picture", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(HomeActivity.this, ""+response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRRRRR", error.toString());
                Toast.makeText(HomeActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();

                String images = _IMG;

                param.put("image",images);
                param.put("Email",_Email);
                return param;
            }
        };

        requestQueue.add(stringRequest);


    }

}
