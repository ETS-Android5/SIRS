package com.example.smartphoneapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.time.Instant;
import java.time.Duration;

import com.example.smartphoneapplication.totp.TOTPAuthenticator;
import com.example.smartphoneapplication.totp.TOTPSecretKey;

import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AssociationCompleted extends AppCompatActivity {

    //private byte[] sharedSecret = new byte[128];
     //new byte[] { "0xe04fd020ea3a6910a2d808002b30309d" };
    private byte[] sharedSecret = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
    private Timer timer;
    long TIME_WINDOW = Duration.ofSeconds(30).toMillis();
    TextView totp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_completed);
        Bundle bundle = getIntent().getExtras();
        //sharedSecret = bundle.getByteArray("sharedSecret");
        totp = (TextView) findViewById(R.id.totpNumber);
        start();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            Log.d("entrei", "ENTREI");
            TOTPAuthenticator totpAuthenticator = null;
            try {
                totpAuthenticator = new TOTPAuthenticator();
                TOTPSecretKey secretKey = new TOTPSecretKey(sharedSecret);
                int totpNumber = totpAuthenticator.createOneTimePassword(secretKey, Instant.now());
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
}