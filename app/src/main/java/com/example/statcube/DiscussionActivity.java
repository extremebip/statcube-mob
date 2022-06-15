package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DiscussionActivity extends AppCompatActivity {

    Button btnadddiscussion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        btnadddiscussion = findViewById(R.id.btn_add_discussion);

        btnadddiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(DiscussionActivity.this, AddDiscussionActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}