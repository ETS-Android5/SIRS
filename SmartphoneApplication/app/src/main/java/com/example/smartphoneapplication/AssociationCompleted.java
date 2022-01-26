package com.example.smartphoneapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import java.time.Instant;
import java.time.Duration;

import com.example.smartphoneapplication.totp.TOTPAuthenticator;
import com.example.smartphoneapplication.totp.TOTPSecretKey;

import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AssociationCompleted extends AppCompatActivity {

    private byte[] sharedSecret = new byte[128];
    private Timer timer;
    long TIME_WINDOW = Duration.ofSeconds(30).toMillis();
    TextView totp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_completed);
        Bundle bundle = getIntent().getExtras();
        sharedSecret = bundle.getByteArray("sharedSecret");
        totp = (TextView) findViewById(R.id.totpNumber);
        start();
    }

    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            TOTPAuthenticator totpAuthenticator = new TOTPAuthenticator();
            TOTPSecretKey secretKey = new TOTPSecretKey(sharedSecret);
            try {
                int totpNumber = totpAuthenticator.createOneTimePassword(secretKey, Instant.now());
                totp.setText(totpNumber);
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
}