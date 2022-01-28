package com.example.smartphoneapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.time.Instant;
import java.time.Duration;

import com.example.smartphoneapplication.totp.TOTPAuthenticator;
import com.example.smartphoneapplication.totp.TOTPSecretKey;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AssociationCompleted extends AppCompatActivity {

    private String sharedSecret;
    private boolean authorization;
    private int totpNumber;
    private Timer timer;
    TextView totp;
    TextView purchaseRequest;
    Button acceptButton;
    Button rejectButton;
    private String username;
    private final String refresh = "https://10.0.2.2:8443/RefreshPurchase";
    private final String confirmation = "https://10.0.2.2:8443/ConfirmPurchase";

    public void acceptPurchase(View view) throws NoSuchProviderException, NoSuchAlgorithmException {
        authorization = true;
        new CONFIRMATIONTask().execute(confirmation);
    }

    public void rejectPurchase(View view) throws NoSuchProviderException, NoSuchAlgorithmException {
        authorization = false;
        new CONFIRMATIONTask().execute(confirmation);
    }

    public void refreshPurchase(View view) throws NoSuchProviderException, NoSuchAlgorithmException {
        new REFRESHTask().execute(refresh);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_completed);
        Bundle bundle = getIntent().getExtras();
        sharedSecret = bundle.getString("sharedSecret");
        username =  bundle.getString("username");
        totp = (TextView) findViewById(R.id.totpNumber);
        purchaseRequest = (TextView) findViewById(R.id.purchaseRequest);
        acceptButton = (Button) findViewById(R.id.acceptButton);
        rejectButton = (Button) findViewById(R.id.rejectButton);
        acceptButton.setVisibility(View.INVISIBLE);
        rejectButton.setVisibility(View.INVISIBLE);
        start();
    }

    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            TOTPAuthenticator totpAuthenticator = null;
            try {
                byte[] byteSecret = sharedSecret.getBytes();
                totpAuthenticator = new TOTPAuthenticator();
                TOTPSecretKey secretKey = new TOTPSecretKey(byteSecret);
                totpNumber = totpAuthenticator.createOneTimePassword(secretKey, Instant.now());
                Log.d("totp", String.valueOf(totpNumber));
                totp.setText(String.valueOf(totpNumber));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    };

    public void start() {
        if(timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 30000);
    }

    public void stop() {
        timer.cancel();
        timer = null;
    }

    class REFRESHTask extends AsyncTask<String, Void, HttpResponse> {

        protected HttpResponse doInBackground(String... uri) {
            try {
                DefaultHttpClient client = new MyHttpClient(getApplicationContext());
                HttpPost httpPost = new HttpPost(uri[0]);

                JSONObject json = new JSONObject();
                json.put("totp", totpNumber);
                json.put("username", username);

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

                    purchaseRequest.setText(apiOutput);
                    acceptButton.setVisibility(View.VISIBLE);
                    rejectButton.setVisibility(View.VISIBLE);

                } catch (IOException | JsonIOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CONFIRMATIONTask extends AsyncTask<String, Void, HttpResponse> {

        protected HttpResponse doInBackground(String... uri) {
            try {
                DefaultHttpClient client = new MyHttpClient(getApplicationContext());
                HttpPost httpPost = new HttpPost(uri[0]);

                JSONObject json = new JSONObject();
                json.put("authorization", authorization);
                json.put("username", username);

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

                    purchaseRequest.setText("");
                    acceptButton.setVisibility(View.INVISIBLE);
                    rejectButton.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}