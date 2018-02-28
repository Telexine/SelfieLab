package com.tachid.a58121090_3.selfielab;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {


    ViewStub vH, vl, vS;
    ImageView Photo,peek;

    Button btnHcam,Cal,btnweb;
    ProgressDialog loadingDialog;
    Bitmap imgBMG;
    String _IMG,_Email,_Name;

    Dialog Imbox;

    //leader board
    ListView Display;
    SimpleAdapter myAdapter;
    List<HashMap<String, String>> fill_data;
    HashMap<String, String> myMap;


    ArrayList IDlst;

    int _i_temp;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vH.setVisibility(View.VISIBLE);
                    vl.setVisibility(View.INVISIBLE);
                    vS.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    Toast.makeText(HomeActivity.this,"Click List To Peek the Selfie",Toast.LENGTH_LONG).show();
                    fill();
                    loadingDialog.dismiss();
                    vH.setVisibility(View.INVISIBLE);
                    vl.setVisibility(View.VISIBLE);
                    vS.setVisibility(View.INVISIBLE);
                    return true;

                case R.id.navigation_notifications:
                    vH.setVisibility(View.INVISIBLE);
                    vl.setVisibility(View.INVISIBLE);
                    vS.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);


        //add logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.selfielab);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f6eeba")));




            Bundle extras = getIntent().getExtras();

            _Email = (String) extras.get("Email");
            _Name = (String) extras.get("name");


            //setting

            vS = (ViewStub) findViewById(R.id.vsSetting);
                vS.setLayoutResource(R.layout.layout_setting);
                vS.inflate();

            //lst
            vl = (ViewStub) findViewById(R.id.vsList);
            vl.setLayoutResource(R.layout.layout_list);
            vl.inflate();

            Display = (ListView) findViewById(R.id.display);

            Imbox = new Dialog(HomeActivity.this);
            Imbox.setContentView(R.layout.imshow_layout);
            Imbox.setTitle("Image");
            Imbox.setCancelable(true);

            peek = (ImageView) Imbox.findViewById(R.id.ivPeek);


        btnweb = (Button)findViewById(R.id.btnweb);
        btnweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Telexine/SelfieLab"));
                startActivity(browserIntent);
            }
        });

            Display.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if(position==0){return;}

                    _i_temp = position-1;


                    RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/peek", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equals("ERROR")){
                                loadingDialog.dismiss();
                                Toast.makeText(HomeActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                                return;
                            }
                                Bitmap temp = _function.convert(response);
                              peek.setImageBitmap(temp);
                            Imbox.show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loadingDialog.dismiss();
                            Toast.makeText(HomeActivity.this, ""+error, Toast.LENGTH_SHORT).show();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> param = new HashMap<>();


                            param.put("id",IDlst.get(_i_temp)+"");
                            param.put("name",_Name);
                            return param;
                        }
                    };

                    requestQueue.add(stringRequest);



                }
            });


            //home
            vH = (ViewStub) findViewById(R.id.vsHome);
            vH.setLayoutResource(R.layout.layout_home);
            vH.inflate();




            vH.setVisibility(View.VISIBLE);
            vl.setVisibility(View.INVISIBLE);
            vS.setVisibility(View.INVISIBLE);

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


                    loadingDialog = ProgressDialog.show(HomeActivity.this, "Uploading photos", "Analyzing...", true, false);
                    String cal_URL= "http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/facelab";
                    uploaduserimage();

                }
            });



            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgBMG = imageBitmap;
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

    public void fill(){

        loadingDialog = ProgressDialog.show(HomeActivity.this, "Fetching from Server", "Loading...", true, false);

        myAdapter = null;


        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("ERROR")){
                    loadingDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "No Face Found on this picture", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Toast.makeText(HomeActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                IDlst = new ArrayList();


                String[] from = new String[]{"txt1", "txt2","txt3","txt4","txt5","txt6"};
                int[] to = new int[]{R.id.txt1, R.id.txt2,R.id.txt3,R.id.txt4,R.id.txt5,R.id.txt6};
                fill_data = new ArrayList<HashMap<String, String>>();


                myMap = new HashMap<String, String>();
                myMap.put("txt1", "NO.");
                myMap.put("txt2", "NAME");
                myMap.put("txt3", "AGE");
                myMap.put("txt4", "ETHNIC");
                myMap.put("txt5", "GENDER");
                myMap.put("txt6", "HOT SCORE");


                fill_data.add(myMap);


                String[] row = response.toString().split("-");
                for(int i = 0;i<row.length;i++){
                    String col[] = row[i].split(",");
                    myMap = new HashMap<String, String>();
                    myMap.put("txt1", (i+1)+".");
                    myMap.put("txt2", col[1]);
                    myMap.put("txt3", col[3]);
                    myMap.put("txt4",  col[4]);
                    myMap.put("txt5", col[5]);
                    myMap.put("txt6",  col[2]+"%");
                    fill_data.add(myMap);
                    IDlst.add(col[0]);
;                }





                myAdapter = new SimpleAdapter(HomeActivity.this, fill_data, R.layout.display, from, to);

                Display.setAdapter(myAdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Toast.makeText(HomeActivity.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);

    }


    public void uploaduserimage(){

        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/facelab", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("NO_FACE_FOUND")){
                    loadingDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "No Face Found on this picture", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Toast.makeText(HomeActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                Intent ns = new Intent(HomeActivity.this,result.class);
                String arr[] = response.split(",");
                ns.putExtra("gender",arr[0]);
                ns.putExtra("age",arr[1]);
                ns.putExtra("bf",arr[2]);//femscore
                ns.putExtra("bm",arr[3]);//mscore
                ns.putExtra("eth",arr[4]);// ethnic
                ns.putExtra("name",_Name);// na
                ns.putExtra("img",imgBMG);// img
                ns.putExtra("email",_Email);// img
                loadingDialog.dismiss();

                startActivity(ns);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
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
