package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 1000; // 1 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Use a Handler to delay the transition to the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String storedEmail = preferences.getString("id", "");
                int value = preferences.getInt("type", 0);

                if (!storedEmail.isEmpty() && value == 1) {
                    // User is already logged in, proceed to MenuActivity directly
                    Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (!storedEmail.isEmpty() && value == 2) {
                    // User is already logged in, proceed to MenuActivity directly
                    Intent intent = new Intent(SplashActivity.this, MenuActivity2.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashActivity.this, StartedActivity.class);
                    startActivity(intent);
                    finish(); // Finish this activity to prevent the user from going back
                }
            }
        }, SPLASH_DELAY);
    }
}
