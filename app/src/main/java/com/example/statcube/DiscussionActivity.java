package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statcube.adapter.AllCoursesAdapter;
import com.example.statcube.adapter.DiscussionAdapter;
import com.example.statcube.adapter.RecommendedAdapter;
import com.example.statcube.api.APIHelper;
import com.example.statcube.api.APIResult;
import com.example.statcube.model.Course;
import com.example.statcube.model.Discussion;
import com.example.statcube.model.Topic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

public class DiscussionActivity extends ToolBarActivity {

    Button btnadddiscussion;
    DiscussionAdapter discussionAdapter;
    RecyclerView disscusionRecycler;
    Topic topic;

    private ArrayList<Discussion> discussions = new ArrayList<>();
    private ArrayList<String> usersName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        initializeToolBar("Discussion",1);

        Intent intent = getIntent();
        topic = (Topic) intent.getSerializableExtra("topic");

        fetchDiscussionByTopic(topic.getTopicID());

        btnadddiscussion = findViewById(R.id.btn_add_discussion);

        btnadddiscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                intent = new Intent(DiscussionActivity.this, AddDiscussionActivity.class);
                startActivity(intent);
            }
        });

        disscusionRecycler = findViewById(R.id.rv_discussions);
        discussionAdapter = new DiscussionAdapter(this);
        discussionAdapter.setDiscussions(discussions);
        discussionAdapter.setUsersName(usersName);
        disscusionRecycler.setAdapter(discussionAdapter);
        disscusionRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    private void fetchDiscussionByTopic(int topicId) {
        System.out.println("CHECKPOINT");
        RequestQueue rq = Volley.newRequestQueue(DiscussionActivity.this);
        StringRequest sr = APIHelper.createGetRequest(APIHelper.BASE_URL + "topics/" + topicId + "/discussions", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    APIResult result = new APIResult(new JSONObject(response));
                    JSONArray discussionArr = (JSONArray) result.getResult();
                    for (int i = 0; i < discussionArr.length(); i++) {
                        JSONObject discussionObj = discussionArr.getJSONObject(i);
                        int discussionID = discussionObj.getInt("DiscussionID");
                        int topicID = discussionObj.getInt("TopicID");
                        int userID = discussionObj.getInt("UserID");
                        String discussionAuthor = discussionObj.getString("DiscussionAuthor");
                        String discussionDate = discussionObj.getString("DiscussionDate");
                        String discussionTitle = discussionObj.getString("DiscussionTitle");
                        String discussionContent = discussionObj.getString("DiscussionContent");

                        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        Discussion discussion = new Discussion(discussionID, topicID, userID, parser.parse(discussionDate), discussionTitle, discussionContent);
                        discussions.add(discussion);
                        usersName.add(discussionAuthor);
                        System.out.println("CHECKPOINTS");
                    }
                    discussionAdapter.setDiscussions(discussions);
                    discussionAdapter.setUsersName(usersName);
                    discussionAdapter.notifyDataSetChanged();
                } catch (Exception e) { }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { }
        });
        rq.add(sr);
    }
}