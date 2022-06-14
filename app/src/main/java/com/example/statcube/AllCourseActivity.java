package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.statcube.adapter.CourseAdapter;
import com.example.statcube.model.Course;

import java.util.ArrayList;

public class AllCourseActivity extends AppCompatActivity {

    private ArrayList<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_course);

        // ini nanti dihapus
        for(int i = 1; i < 12; i++){
            courses.add(new Course(i, 1, "Course Title", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        }

        RecyclerView courseRecycler = findViewById(R.id.rv_courses);
        CourseAdapter courseAdapter = new CourseAdapter(this);
        courseAdapter.setCourses(courses);
        courseRecycler.setAdapter(courseAdapter);
        courseRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }
}