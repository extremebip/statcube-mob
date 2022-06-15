package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.statcube.adapter.CourseAdapter;
import com.example.statcube.model.Course;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<Course> courses;
    SearchView svSearch;

    RecyclerView courseRecycler;
    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        svSearch = findViewById(R.id.sv_search);
        svSearch.clearFocus();
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        courseRecycler = findViewById(R.id.rv_search_result);
        courseRecycler.setHasFixedSize(true);
        courseRecycler.setLayoutManager(new LinearLayoutManager(this));
        courses = new ArrayList<>();

        // ini nanti diapus
        courses.add(new Course(1,1, "Course Title Temporary", "Course Description Temporary"));
        courses.add(new Course(2,1, "Course Title Dewi", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course(3,1, "Course Title Devita", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course(4,1, "Course Title Anthony", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course(5,1, "Course Title Wongs", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course(6,1, "Course Title Nicho", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course(7,1, "Course Title Icham", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));
        courses.add(new Course(8,1, "Course Title Mob Prog", "Course Description - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis venenatis"));

        courseAdapter = new CourseAdapter(this);
        courseAdapter.setCourses(courses);
        courseRecycler.setAdapter(courseAdapter);
    }

    private void filterList(String text) {
        ArrayList<Course> filteredList = new ArrayList<>();
        int n = courses.size();
        for (int i = 0; i < n; i++){
            Course course = courses.get(i);
            if(course.getCourseTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(course);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "No course found", Toast.LENGTH_SHORT).show();
        }
        else{
            courseAdapter.setCourses(filteredList);
            courseAdapter.notifyDataSetChanged();
        }
    }
}