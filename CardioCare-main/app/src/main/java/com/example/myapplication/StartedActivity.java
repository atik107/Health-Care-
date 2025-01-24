package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartedActivity extends AppCompatActivity {

    int x=0;
    private TextView textView;
    private ImageView imageView;
    private Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        myButton = findViewById(R.id.button);

        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String FirstTime = preferences.getString("FirstTimeInstall","");

        if(FirstTime.equals("Yes")){
            Intent intent = new Intent(StartedActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall","Yes");
            editor.apply();
        }

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnactivity();
            }
        });
    }

    void btnactivity(){

        if(x==0){
            x++;
            textView.setText("Welcome to CardioCare");
            imageView.setImageResource(R.drawable.sp2);
        }
        else if(x==1){
            x++;
            ViewGroup.LayoutParams params = myButton.getLayoutParams();
            params.width = 560; // Replace with the desired width
            myButton.setLayoutParams(params);

            textView.setText("Take charge of your heart with professional guidance.");
            imageView.setImageResource(R.drawable.s1);
            myButton.setText("Let's Get Started");
        }
        else{
            Intent intent = new Intent(StartedActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}