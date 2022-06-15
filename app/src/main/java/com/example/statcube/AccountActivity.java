package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {
    Button btnsubscribe;
    TextView tbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Intent intent = getIntent();

        btnsubscribe =findViewById(R.id.btn_subscribe);
        tbar_title = findViewById(R.id.toolbar_title);
        tbar_title.setText("Account");

        btnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SubscribeActivity.class);
                startActivity(intent);
            }
        });

    }
}