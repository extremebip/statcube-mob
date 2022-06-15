package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    ImageView ivStatcube;
    Button btnLogin, btnRegister;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "mySharedPreference";
    private static final String KEY_ID = "userId";
    int userid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // kalo session udah ada langsung login
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        userid = sharedPreferences.getInt(KEY_ID, -1);
        if (userid != -1) {
            Intent intent = new Intent(new Intent(WelcomeActivity.this, HomeActivity.class));
            startActivity(intent);
        }

        ivStatcube = findViewById(R.id.iv_statcube);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        ivStatcube.setImageResource(R.drawable.logo);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(WelcomeActivity.this, LoginActivity.class));
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(WelcomeActivity.this, RegisterActivity.class));
                startActivity(intent);
            }
        });

    }
}