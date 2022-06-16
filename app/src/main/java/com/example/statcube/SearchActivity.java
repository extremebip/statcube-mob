package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statcube.adapter.CourseAdapter;
import com.example.statcube.api.APIHelper;
import com.example.statcube.api.APIResult;
import com.example.statcube.model.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends ToolBarActivity{

    private ArrayList<Course> courses = new ArrayList<>();
    SearchView svSearch;

    RecyclerView courseRecycler;
    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initializeToolBar("Search",1);

        fetchAllCourses();

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
                System.out.println(course.getCourseID() + course.getCourseTitle() + course.getCourseDescription());
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

    private void fetchAllCourses() {
        RequestQueue rq = Volley.newRequestQueue(SearchActivity.this);
        StringRequest sr = APIHelper.createGetRequest(APIHelper.BASE_URL + "courses", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    APIResult result = new APIResult(new JSONObject(response));
                    JSONArray coursesArr = (JSONArray) result.getResult();
                    for (int i = 0; i < coursesArr.length(); i++) {
                        JSONObject courseObj = coursesArr.getJSONObject(i);
                        int CourseID = courseObj.getInt("CourseID");
                        int AdminID = courseObj.getInt("AdminID");
                        String CourseTitle = courseObj.getString("CourseTitle");
                        String CourseDescription = courseObj.getString("CourseDescription");
                        Course course = new Course(CourseID, AdminID, CourseTitle, CourseDescription);
                        courses.add(course);
                    }
                    courseAdapter.setCourses(courses);
                    courseAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("Error", "Parsing JSON Error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { }
        });
        rq.add(sr);
    }
}