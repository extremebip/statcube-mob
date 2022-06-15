package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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
import java.util.Vector;

public class AllCourseActivity extends AppCompatActivity {

    private ArrayList<Course> courses = new ArrayList<>();
    CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_course);

        RecyclerView courseRecycler = findViewById(R.id.rv_courses);
        courseAdapter = new CourseAdapter(this);
        courseAdapter.setCourses(new ArrayList<>());
        courseRecycler.setAdapter(courseAdapter);
        courseRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fetchAllCourses();
    }

    private void fetchAllCourses() {
        RequestQueue rq = Volley.newRequestQueue(AllCourseActivity.this);
        StringRequest sr = APIHelper.createGetRequest(APIHelper.BASE_URL + "courses", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    APIResult result = new APIResult(new JSONObject(response));
                    JSONArray coursesArr = (JSONArray) result.getResult();
                    ArrayList<Course> courses = new ArrayList<>();
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
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(sr);
    }
}