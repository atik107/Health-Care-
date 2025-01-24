package com.example.myapplication;

// Import necessary packages
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    TextView per,ver;
    Button ok;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String res = intent.getStringExtra("res");

        per = findViewById(R.id.percent);
        ver = findViewById(R.id.verdict);
        ok = findViewById(R.id.okbutton);

        float p = Float.parseFloat(res);
        p = p*100;
        String tmp = String.format("%.2f", p);
        per.setText("Heart disease probability: "+tmp + "%");

        if(p>=85){
            ver.setText("Go to Doctor Immediately");
        }
        else if(p>=50){
            ver.setText("Maybe you should Go to Doctor");
        }
        else{
            ver.setText("You are in good condition");
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
