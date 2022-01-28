package com.example.smartphoneapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.*;

import org.apache.commons.codec.binary.Base32;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class AssociateMobile extends AppCompatActivity {

    private String code;
    private String username;
    private String sharedSecret;

    TextView randomCode;
    Button generateButton;
    Intent intent;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associate_mobile);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        randomCode = (TextView) findViewById(R.id.randomCode);
        generateButton = (Button) findViewById(R.id.generateCodeButton);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generateCode(View view) throws NoSuchProviderException, NoSuchAlgorithmException {
        ArrayList<String> codes = KeyGenerator.generateCodes();
        code = codes.get(1);
        sharedSecret = codes.get(0);

        randomCode.setText(code);
        final String uri = "https://10.0.2.2:8443/RegisterMobile";
        new POSTTask().execute(uri);
    }

    class POSTTask extends AsyncTask<String, Void, HttpResponse> {

        protected HttpResponse doInBackground(String... uri) {
            try {
                DefaultHttpClient client = new MyHttpClient(getApplicationContext());
                HttpPost httpPost = new HttpPost(uri[0]);


                JSONObject json = new JSONObject();
                json.put("randomCode", code);
                json.put("sharedSecret", sharedSecret);

                StringEntity entity = new StringEntity(json.toString());
                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // Execute the POST call and obtain the response
                HttpResponse response = client.execute(httpPost);

                return response;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(HttpResponse response)  {
            Log.d("ENTREI2", "entrei 2");

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200)
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }
            else {
                HttpEntity httpEntity = response.getEntity();

                try {
                    String apiOutput = EntityUtils.toString(httpEntity, "utf-8");
                    System.out.println(apiOutput);
                    if(apiOutput == "NOT OK") {
                        intent = new Intent(AssociateMobile.this, MainActivity.class);
                    }
                    else {
                        System.out.println(apiOutput);
                        username = apiOutput;
                        intent = new Intent(AssociateMobile.this, AssociationCompleted.class);
                        intent.putExtra("sharedSecret", sharedSecret);
                        intent.putExtra("username", username);
                    }
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}