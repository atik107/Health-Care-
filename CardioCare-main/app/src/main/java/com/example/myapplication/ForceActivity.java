package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForceActivity extends AppCompatActivity {

    Button btn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force);

        btn = findViewById(R.id.okbtn);
        firebaseAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
    }

    private void deleteAccount() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.delete()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Account deleted successfully
                                Toast.makeText(ForceActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForceActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish(); // Close the activity or navigate to another screen
                            } else {
                                // Failed to delete account
                                Toast.makeText(ForceActivity.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // User is not signed in
            Toast.makeText(ForceActivity.this, "No user signed in", Toast.LENGTH_SHORT).show();
        }
    }
}