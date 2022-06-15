package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class SubscribeActivity extends ToolBarActivity {
    Button subsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        initializeToolBar("Subscribe",1);
        Intent intent = getIntent();

        subsbtn =findViewById(R.id.subsbutton);

        subsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubscribeActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }
}