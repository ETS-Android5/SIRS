package com.example.smartphoneapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.os.AsyncTask;

import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Login extends AppCompatActivity {
    private int userId;
    EditText usernameRegister;
    EditText passwordRegister;
    Button buttonPreRegister;
    TextView textViewCodeGenerator;

    public void sendMessage(View view) {
        final String uri = "http://10.0.2.2:8080/RegisterMobile";
        new RESTTask().execute(uri);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        usernameRegister = (EditText) findViewById(R.id.usernameLogin);
        passwordRegister = (EditText) findViewById(R.id.passcodeLogin);
        buttonPreRegister = (Button) findViewById(R.id.buttonPreRegister);
        //textViewCodeGenerator = (TextView) findViewById(R.id.codeGenerator);
        //textViewCodeGenerator.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Intent CodeGenIntent = new Intent(Register.this, CodeGenerator.class);
        //        startActivity(CodeGenIntent);
        //    }
        //});
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    class RESTTask extends AsyncTask<String, Void, ResponseEntity<User>> {

        protected ResponseEntity<User> doInBackground(String... uri) {
            try {
                Log.d("ENTREI", "entrei 1");
                final String url = uri[0];
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                /*HttpHeaders headers = new HttpHeaders();
                headers.set("APIKey", "");
                String auth = "Jack" + ":" + "Jones";*/

                String name = usernameRegister.getText().toString().trim();
                String password = passwordRegister.getText().toString().trim();
                User user = new User(name, password);

                /*String encodedAuth = Base64.encodeToString(auth.getBytes(), Base64.DEFAULT);
                String authHeader = "Basic" + new String(encodedAuth);
                headers.set("Authorization", authHeader);*/

                HttpEntity<User> request = new HttpEntity<>(user);
                ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.POST, request, User.class);

                return response;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseEntity<User> result) {
            Log.d("ENTREI2", "entrei 2");
            HttpStatus statusCode = result.getStatusCode();
            User userReturned = result.getBody();
        }
    }
}