package com.tachid.a58121090_3.selfielab;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //step 1 for external
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());


        //http
        HttpClient hc;
        HttpPost hp;
        HttpResponse hr;
        String url = "", data = "";
        BufferedReader br;

        hc = new DefaultHttpClient();
        url = "";
        hp = new HttpPost(url);
        try {
            hr = hc.execute(hp);
            br = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
            data = br.readLine();
        } catch (Exception e) {

        }
/*
      //JSON Build

        String json = "";

        // 3. build jsonObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("token", "EAAUnkVDtHboBAFurxzGUJlzUq6GYozbpueyWA3gZBMBhXfxdgS6AaGZCDBYMXebhMb3dvkXER944F7LRrCONsv0q7D2AiSgSipMuRQAZBwlSElaoaNCg4YtgVVoMGgT7i8Qk5jZAj3ZA5YPrT6Jy2p4vrQm0NijZCj1QW1DDUWzHRhLSg3n3Yi7pGIzSzcrj0EmfetYbNbgQZDZD");
        jsonObject.accumulate("VendorID", "VID0001");
        jsonObject.accumulate("couponCode", "5f6609f6baa94880e490e25d3d3e37211eb41ebae0f4db1ff0c6f8fc64a2dc13");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.accumulate("data",jsonObject);

        // 4. convert JSONObject to JSON to String
        json = jsonObject1.toString();

        StringEntity se = new StringEntity(json);
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(se);


        httpPost.addHeader("content-type", "application/json");
            httpPost.addHeader("cache-control", "no-cache");
            httpPost.addHeader("postman-token", "394ce21e-3151-f79f-02a2-a111654975c6");
            HttpResponse response = client.execute(httpPost);
            Toast.makeText(this,json.toString(),Toast.LENGTH_SHORT).show();
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }

        */
    }
}
