package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.statcube.adapter.CourseAdapter;
import com.example.statcube.adapter.TopicAdapter;
import com.example.statcube.model.Course;
import com.example.statcube.model.Topic;

import java.util.ArrayList;

public class CourseDetailActivity extends AppCompatActivity {

    private Course course;
    private String author;
    private ArrayList<Topic> topics = new ArrayList<>();

    TextView tvTitle, tvAuthor, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // ini nanti diapus
        author = "Dewi Puspita Tanurezal";
        for(int i = 1; i < 10; i++){
            topics.add(new Topic(i, 1, "Topic Title", "Topic Content - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Enim aliquam vitae feugiat iaculis elit faucibus eu tellus. Sit nibh consequat sapien ac dignissim tellus. Vestibulum dignissim dui nunc id vitae in. Aliquet semper at nunc cras nunc in. Rutrum convallis nec viverra elit vulputate sagittis.\n" +
                    "Dictum phasellus facilisi adipiscing sit sed tincidunt. Pulvinar est suspendisse ante nullam mattis urna tincidunt semper. Vitae dignissim tempus interdum risus. Leo maecenas ac arcu tempor, aliquet. Vestibulum amet adipiscing egestas neque. Dolor egestas varius felis, massa id. Nisl nisl erat pellentesque.", "https://www.ilmubahasainggris.com/wp-content/uploads/2017/03/NGC.jpg"));
        }

        Intent intent = getIntent();
        course = (Course) intent.getSerializableExtra("course");

        tvTitle = findViewById(R.id.tv_title);
        tvAuthor = findViewById(R.id.tv_author);
        tvDescription = findViewById(R.id.tv_description);

        tvTitle.setText(course.getCourseTitle());
        tvAuthor.setText("By " + author);
        tvDescription.setText(course.getCourseDescription());

        RecyclerView topicRecycler = findViewById(R.id.rv_topics);
        TopicAdapter topicAdapter = new TopicAdapter(this);
        topicAdapter.setTopics(topics);
        topicRecycler.setAdapter(topicAdapter);
        topicRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}