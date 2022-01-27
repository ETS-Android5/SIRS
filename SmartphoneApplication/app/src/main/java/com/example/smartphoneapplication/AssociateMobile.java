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
    private String sharedSecret;

    TextView randomCode;
    Button generateButton;

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
        new RESTTask().execute(uri);
    }

    /*public HttpEntity<String> execute(String uri) throws IOException {
        try {
            DefaultHttpClient client = new MyHttpClient((HttpParams) getApplicationContext());
            HttpPost httpPost = new HttpPost(uri);

            String json = "{'randomCode':" + code + ",'name':" + sharedSecret + " }";
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // Execute the POST call and obtain the response
            HttpResponse getResponse = client.execute(httpPost);
            HttpEntity<String> responseEntity = (HttpEntity) getResponse.getEntity();

            return responseEntity;
        }  catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }*/

    private InputStream getInputStream(String user, String password) throws IOException
    {
        URL url = new URL("https://10.0.2.2:8443/test");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        // Create the SSL connection
        SSLContext sc;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null, null, new SecureRandom());
            conn.setSSLSocketFactory(sc.getSocketFactory());

            // Use this if you need SSL authentication
            String userPass= user + ":" + password;
            String basicAuth = "Basic " + Base64.encodeToString(userPass.getBytes(), Base64.DEFAULT);
            conn.setRequestProperty("Authorization", basicAuth);

            // set Timeout and method
            conn.setReadTimeout(7000);
            conn.setConnectTimeout(7000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoInput(true);

            OutputStream os = new FileOutputStream("test.txt");
            String jsonInputString = "{" + randomCode + ": "+ code +", " + sharedSecret + ": " + String.valueOf(sharedSecret) + "}";

            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
            // Add any data you wish to post here

            conn.connect();
            return conn.getInputStream();
        }
        catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    class RESTTask extends AsyncTask<String, Void, HttpResponse> {

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


        /*protected ResponseEntity<String> doInBackground(String... uri) {
            try {
                DefaultHttpClient client = new MyHttpClient((HttpParams) getApplicationContext());
                HttpPost httpPost = new HttpPost(uri[0]);

                String json = "{'randomCode':" + code + ",'name':" + sharedSecret + " }";
                StringEntity entity = new StringEntity(json);
                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // Execute the POST call and obtain the response
                HttpResponse getResponse = client.execute(httpPost);
                HttpEntity<String> responseEntity = (HttpEntity) getResponse.getEntity();

                return responseEntity;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        protected ResponseEntity<String> doInBackground(String... uri) {
            try {
                Log.d("ENTREI", "entrei 1");
                final String url = uri[0];
                RestTemplate restTemplate = new RestTemplate();
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.setObjectMapper(mapper);
                restTemplate.getMessageConverters().add(converter);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                Map<String, Object> body = new HashMap<>();

                body.put("randomCode", code);
                body.put("sharedSecret", sharedSecret);

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);*/

                //ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

                /*String encodedAuth = Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
                String authHeader = "Basic" + new String(encodedAuth);
                headers.set("Authorization", authHeader);

                /*ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

                return response;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }*/

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
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(AssociateMobile.this, AssociationCompleted.class);
                intent.putExtra("sharedSecret", sharedSecret);
                startActivity(intent);
            }
        }
    }
}