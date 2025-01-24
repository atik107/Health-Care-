package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmergencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        Button btnEmergency1 = findViewById(R.id.btnEmergency1);
        Button btnEmergency2 = findViewById(R.id.btnEmergency2);
        Button btnEmergency3 = findViewById(R.id.btnEmergency3);
        Button btnAmbulance = findViewById(R.id.btnAmbulance);

        btnEmergency1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialEmergencyNumber("123"); // Replace "123" with the actual emergency hotline number
            }
        });

        btnEmergency2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialEmergencyNumber("456"); // Replace "456" with the actual emergency hotline number
            }
        });

        btnEmergency3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialEmergencyNumber("789"); // Replace "789" with the actual emergency hotline number
            }
        });

        btnAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmergencyActivity.this, EmAmbActivity.class));
            }
        });
    }

    private void dialEmergencyNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }
}