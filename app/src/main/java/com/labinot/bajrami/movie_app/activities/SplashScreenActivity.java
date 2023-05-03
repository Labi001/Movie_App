package com.labinot.bajrami.movie_app.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.labinot.bajrami.movie_app.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.SplashScreenTheme);
        super.onCreate(savedInstanceState);

        Intent i = new Intent(SplashScreenActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
