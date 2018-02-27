package com.tachid.a58121090_3.selfielab;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class result extends AppCompatActivity {



    ImageView Image;
    TextView name,gender,age,eth,ms,fs;
    ProgressBar Male_S,Female_S;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        Image = (ImageView) findViewById(R.id.ivIMG);
        name = (TextView) findViewById(R.id.tvna);
        gender= (TextView) findViewById(R.id.tvg);
        age= (TextView) findViewById(R.id.tva);
        eth = (TextView) findViewById(R.id.tveth);
        ms = (TextView) findViewById(R.id.tvms);
        fs = (TextView) findViewById(R.id.tvfs);

        Male_S = (ProgressBar) findViewById(R.id.pbMale);
        Female_S = (ProgressBar) findViewById(R.id.pbF);


        Bundle extras = getIntent().getExtras();


        Image.setImageBitmap( (Bitmap) getIntent().getParcelableExtra("img"));
        name.setText((String) extras.get("name"));
        gender.setText((String) extras.get("gender"));
        age.setText((String) extras.get("age"));
        eth.setText((String) extras.get("eth"));

        ms.setText((String) extras.get("bm"));
        fs.setText((String) extras.get("bf"));
         Male_S.setProgress( Math.round(Float.parseFloat( (String) extras.get("bm"))));
        Female_S.setProgress( Math.round(Float.parseFloat( (String) extras.get("bf"))));


/*
*               ns.putExtra("gender",arr[0]);
                ns.putExtra("age",arr[1]);
                ns.putExtra("bf",arr[2]);//femscore
                ns.putExtra("bm",arr[3]);//mscore
                ns.putExtra("eth",arr[4]);// ethnic
                ns.putExtra("name",_Name);// na
                ns.putExtra("img",imgBMG);// img
* */
    }
}
