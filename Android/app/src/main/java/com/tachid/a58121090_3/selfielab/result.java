package com.tachid.a58121090_3.selfielab;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class result extends AppCompatActivity {



    ImageView Image;
    TextView name,gender,age,eth,ms;
    ProgressBar Female_S;

    ProgressDialog loadingDialog;
    Bundle extras;
    Button save,back;
    String _Email;
    float score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);



        //add logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.selfielab);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f6eeba")));




        save  =(Button) findViewById(R.id.btnsave);
        back  =(Button) findViewById(R.id.btnBack);

        Image = (ImageView) findViewById(R.id.ivIMG);
        name = (TextView) findViewById(R.id.tvna);
        gender= (TextView) findViewById(R.id.tvg);
        age= (TextView) findViewById(R.id.tva);
        eth = (TextView) findViewById(R.id.tveth);
        ms = (TextView) findViewById(R.id.tvms);



        Female_S = (ProgressBar) findViewById(R.id.pbF);


         extras = getIntent().getExtras();


        Image.setImageBitmap( (Bitmap) getIntent().getParcelableExtra("img"));
        name.setText((String) extras.get("name"));
        gender.setText((String) extras.get("gender"));
        age.setText((String) extras.get("age"));
        eth.setText((String) extras.get("eth"));
        score = (Float.parseFloat( (String) extras.get("bf"))+Float.parseFloat( (String) extras.get("bm")))/2f ;
        ms.setText(score+" %");
        _Email = (String) extras.get("email");

        Female_S.setProgress( Math.round(score) );

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog = ProgressDialog.show(result.this, "Upload to Server", "Save...", true, false);

                RequestQueue requestQueue = Volley.newRequestQueue(result.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ec2-35-165-235-120.us-west-2.compute.amazonaws.com:3030/save", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("ERROR")){
                            loadingDialog.dismiss();
                            Toast.makeText(result.this, "Save ERROR", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(result.this, "Save Complete", Toast.LENGTH_SHORT).show();


                        loadingDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(result.this);
                        builder1.setMessage("Save to Server Complete.");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        dialog.cancel();

                                    }
                                });



                        AlertDialog alert11 = builder1.create();
                        alert11.show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismiss();
                        Toast.makeText(result.this, ""+error, Toast.LENGTH_SHORT).show();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param = new HashMap<>();

                        param.put("name",name.getText().toString());
                        param.put("score",score+"");

                        param.put("age",age.getText().toString());
                        param.put("eth",eth.getText().toString());
                        param.put("gender",gender.getText().toString());

                        param.put("image",_function.encodeImage((Bitmap) getIntent().getParcelableExtra("img")));
                         param.put("email",(String) extras.get("email"));
                        return param;
                    }
                };

                requestQueue.add(stringRequest);



            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
