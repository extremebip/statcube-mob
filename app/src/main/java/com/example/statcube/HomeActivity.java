package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        titlesRecommended.add("Time Series Analysis");
        titlesRecommended.add("Statistical Process Control");
        titlesRecommended.add("Structural Equation Model");
        titlesRecommended.add("Categorical Data Analysis");
        titlesRecommended.add("Econometics");
        authorsRecommended.add("Dewi Puspita Tanurezal");
        authorsRecommended.add("Anthony Gilrandy Theo");
        authorsRecommended.add("Steven");
        authorsRecommended.add("Syntia Firdaus");
        authorsRecommended.add("Devita");
        courses.add(new Course("CR001", "AD001", "TP001", "Course Title", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course("CR002", "AD001", "TP001", "Course Title", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course("CR003", "AD001", "TP001", "Course Title", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course("CR004", "AD001", "TP001", "Course Title", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course("CR005", "AD001", "TP001", "Course Title", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course("CR006", "AD001", "TP001", "Course Title", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course("CR007", "AD001", "TP001", "Course Title", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));


        btnViewAll = findViewById(R.id.btn_view_all);
        rvRecommended = findViewById(R.id.rv_recommended);
        rvAllCourses = findViewById(R.id.rv_all_courses);

        RecommendedAdapter recommendedAdapter = new RecommendedAdapter(this, titlesRecommended, authorsRecommended);
        rvRecommended.setAdapter(recommendedAdapter);
        rvRecommended.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // pindah ke halaman view all
            }
        });

        AllCoursesAdapter allCoursesAdapter = new AllCoursesAdapter(this, courses);
        rvAllCourses.setAdapter(allCoursesAdapter);
        rvAllCourses.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


    }
}