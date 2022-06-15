package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.statcube.adapter.AllCoursesAdapter;
import com.example.statcube.adapter.DiscussionAdapter;
import com.example.statcube.adapter.RecommendedAdapter;
import com.example.statcube.model.Discussion;
import com.example.statcube.model.Topic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public class DiscussionActivity extends ToolBarActivity {

    Button btnadddiscussion;
    DiscussionAdapter discussionAdapter;
    RecyclerView rvDiscussions;
    Topic topic;

    private ArrayList<Discussion> discussions = new ArrayList<>();
    private ArrayList<String> usersName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        initializeToolBar("Discussion",1);

        // temp
        for(int i=0; i<10; i++){
            discussions.add(new Discussion(1, 1, 1, "2022-06-14T00:00:00.000Z", "Discussion title", "Discussion Content - Discussion Content Discussion Content Discussion ContentDiscussion ContentDiscussion ContentDiscussion Content Discussion Content Discussion Content"));
            usersName.add("Dewi Puspita Tanurezal");
        }

        btnadddiscussion = findViewById(R.id.btn_add_discussion);

        btnadddiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(DiscussionActivity.this, AddDiscussionActivity.class);
                startActivity(intent);
            }
        });

        discussionAdapter = new DiscussionAdapter(this);
        discussionAdapter.setDiscussions(discussions);
        discussionAdapter.setUsersName(usersName);
        rvDiscussions.setAdapter(discussionAdapter);
        rvDiscussions.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }
}