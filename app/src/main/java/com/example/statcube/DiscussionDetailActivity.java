package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DiscussionDetailActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_detail);
        initializeToolBar("Discussion",1);
    }
}