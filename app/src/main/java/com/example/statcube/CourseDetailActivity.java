package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statcube.adapter.CourseAdapter;
import com.example.statcube.adapter.TopicAdapter;
import com.example.statcube.api.APIHelper;
import com.example.statcube.api.APIResult;
import com.example.statcube.model.Course;
import com.example.statcube.model.Topic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CourseDetailActivity extends AppCompatActivity {

    TopicAdapter topicAdapter;
    int CourseID;

    TextView tvTitle, tvAuthor, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        Intent intent = getIntent();
        CourseID = intent.getIntExtra("CourseID", 0);

        tvTitle = findViewById(R.id.tv_title);
        tvAuthor = findViewById(R.id.tv_author);
        tvDescription = findViewById(R.id.tv_description);

        tvTitle.setText("");
        tvAuthor.setText("By");
        tvDescription.setText("");

        RecyclerView topicRecycler = findViewById(R.id.rv_topics);
        topicAdapter = new TopicAdapter(this);
        topicAdapter.setTopics(new ArrayList<>());
        topicRecycler.setAdapter(topicAdapter);
        topicRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fetchCourseDetail();
    }

    private void fetchCourseDetail() {
        RequestQueue rq = Volley.newRequestQueue(CourseDetailActivity.this);
        String endpoint = String.format("courses/%d/detail", CourseID);
        StringRequest sr = APIHelper.createGetRequest(APIHelper.BASE_URL + endpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    APIResult result = new APIResult(new JSONObject(response));
                    JSONObject coursesObj = (JSONObject) result.getResult();
                    String title = coursesObj.getString("CourseTitle");
                    String description = coursesObj.getString("CourseDescription");
                    String author = coursesObj.getString("CourseAuthor");

                    tvTitle.setText(title);
                    tvDescription.setText(description);
                    tvAuthor.setText("By " + author);

                    JSONArray topicsArr = coursesObj.getJSONArray("Topics");
                    ArrayList<Topic> topics = new ArrayList<>();
                    for (int i = 0; i < topicsArr.length(); i++) {
                        JSONObject courseObj = topicsArr.getJSONObject(i);
                        int TopicID = courseObj.getInt("TopicID");
                        String TopicTitle = courseObj.getString("TopicTitle");
                        String TopicContent = courseObj.getString("TopicContent");
                        String TopicThumbnail = courseObj.getString("TopicThumbnail");
                        Topic topic = new Topic(TopicID, CourseID, TopicTitle, TopicContent, TopicThumbnail);
                        topics.add(topic);
                    }
                    topicAdapter.setTopics(topics);
                    topicAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("Error", "Parsing JSON Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(sr);
    }
}