package com.ecell.skolartab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent dashboard_intent  = new Intent(SplashActivity.this,Dashboard.class);
        startActivity(dashboard_intent);
        finish();
    }
}