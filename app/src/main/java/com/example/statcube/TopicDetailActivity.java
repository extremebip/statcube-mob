package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.statcube.model.Discussion;
import com.example.statcube.model.Topic;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopicDetailActivity extends AppCompatActivity {

    private Topic topic;

    TextView tvTopicTitle, tvTopicContent;
    ImageView ivTopicThumbnail;
    LinearLayout llDiscussion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        Intent intent = getIntent();
        topic = (Topic) intent.getSerializableExtra("topic");

        tvTopicTitle = findViewById(R.id.tv_topic_title);
        tvTopicContent = findViewById(R.id.tv_topic_content);
        ivTopicThumbnail = findViewById(R.id.iv_topic_thumbnail);
        llDiscussion = findViewById(R.id.ll_discussion);

        tvTopicTitle.setText(topic.getTopicTitle());
        tvTopicContent.setText(topic.getTopicContent());
        Picasso.with(this).load(topic.getTopicThumbnail()).into(ivTopicThumbnail);

        llDiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}