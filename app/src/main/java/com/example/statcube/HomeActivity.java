package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.statcube.adapter.AllCoursesAdapter;
import com.example.statcube.adapter.RecommendedAdapter;
import com.example.statcube.model.Course;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<String> titlesRecommended = new ArrayList<>();
    ArrayList<String> authorsRecommended = new ArrayList<>();
    ArrayList<Course> courses = new ArrayList<>();

    TextView btnViewAll;
    RecyclerView rvRecommended, rvAllCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ini nanti dihapus
        for(int i = 1; i < 12; i++){
            titlesRecommended.add("temporary title recommended");
            authorsRecommended.add("temporary author recommended");
            courses.add(new Course(i, 1, "Course Title", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        }

        btnViewAll = findViewById(R.id.btn_view_all);
        rvRecommended = findViewById(R.id.rv_recommended);
        rvAllCourses = findViewById(R.id.rv_all_courses);

        RecommendedAdapter recommendedAdapter = new RecommendedAdapter(this, titlesRecommended, authorsRecommended);
        rvRecommended.setAdapter(recommendedAdapter);
        rvRecommended.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AllCourseActivity.class);
                startActivity(intent);
            }
        });

        AllCoursesAdapter allCoursesAdapter = new AllCoursesAdapter(this, courses);
        rvAllCourses.setAdapter(allCoursesAdapter);
        rvAllCourses.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }
}