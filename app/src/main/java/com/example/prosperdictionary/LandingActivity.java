package com.example.prosperdictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Button started = findViewById(R.id.get_started);
        started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mStarted=new Intent(LandingActivity.this, MainActivity.class);
                startActivity(mStarted);
            }
        });
    }
}