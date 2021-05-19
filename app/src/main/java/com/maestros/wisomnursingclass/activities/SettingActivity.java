package com.maestros.wisomnursingclass.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.maestros.wisomnursingclass.R;

public class SettingActivity extends AppCompatActivity {

    RelativeLayout term,privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        term = findViewById(R.id.term);
        privacy = findViewById(R.id.privacy);

        term.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),TermsAndConditionActivity.class)));
        privacy.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),PrivacyPlocyActivity.class)));
    }
}