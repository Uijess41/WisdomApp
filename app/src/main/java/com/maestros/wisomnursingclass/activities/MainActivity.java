package com.maestros.wisomnursingclass.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.maestros.wisomnursingclass.R;
import com.maestros.wisomnursingclass.databinding.ActivityLoginBinding;
import com.maestros.wisomnursingclass.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Context context;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        view=binding.getRoot();
        setContentView(view);

        context=this;

        binding.layoutCompete.setOnClickListener(view -> {
            startActivity(new Intent(context,Nav.class));
            finish();
        });


        binding.layoutCollege.setOnClickListener(view -> {
            startActivity(new Intent(context,Nav.class));
            finish();
        });

    }
}