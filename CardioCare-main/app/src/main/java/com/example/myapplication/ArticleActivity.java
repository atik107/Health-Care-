package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        TextView art1 = findViewById(R.id.art1);
        TextView art2 = findViewById(R.id.art2);
        TextView art3 = findViewById(R.id.art3);
        TextView art4 = findViewById(R.id.art4);
        TextView art5 = findViewById(R.id.art5);
        TextView art6 = findViewById(R.id.art6);

        // Set click listeners for the art TextViews
        art1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullArticleActivity(1); // Send 1 for art1
            }
        });

        art2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullArticleActivity(2); // Send 2 for art2
            }
        });

        art3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullArticleActivity(3); // Send 3 for art3
            }
        });

        art4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullArticleActivity(4); // Send 4 for art4
            }
        });

        art5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullArticleActivity(5); // Send 5 for art5
            }
        });

        art6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFullArticleActivity(6); // Send 5 for art5
            }
        });
    }

    // Method to open FullArticleActivity and pass the article number
    private void openFullArticleActivity(int articleNumber) {
        Intent intent = new Intent(this, FullArticleActivity.class);
        intent.putExtra("ARTICLE_NUMBER", articleNumber);
        startActivity(intent);
    }
}
