package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statcube.adapter.AllCoursesAdapter;
import com.example.statcube.adapter.RecommendedAdapter;
import com.example.statcube.api.APIHelper;
import com.example.statcube.api.APIResult;
import com.example.statcube.model.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<Course> coursesRecommended = new ArrayList<>(); // courseRecommended
    ArrayList<String> authorsRecommended = new ArrayList<>(); // authorRecommended
    ArrayList<Course> courses = new ArrayList<>();

    TextView tvSearch, btnViewAll,tbtitle;
    RecyclerView rvRecommended, rvAllCourses;
    ImageView back_btn,hamb_menu_btn;

    RecommendedAdapter recommendedAdapter;
    AllCoursesAdapter allCoursesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fetchAllCourses();
        fetchRecommendedCourse();

        btnViewAll = findViewById(R.id.btn_view_all);
        rvRecommended = findViewById(R.id.rv_recommended);
        rvAllCourses = findViewById(R.id.rv_all_courses);
        tvSearch = findViewById(R.id.tv_search);
        tbtitle = findViewById(R.id.toolbar_title);
        back_btn = findViewById(R.id.back_arrow);
        back_btn.setVisibility(View.INVISIBLE);
        hamb_menu_btn = findViewById(R.id.hamb_menu);

        tbtitle.setText("Home");

        hamb_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        recommendedAdapter = new RecommendedAdapter(this, courses, authorsRecommended);
        rvRecommended.setAdapter(recommendedAdapter);
        rvRecommended.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AllCourseActivity.class);
                startActivity(intent);
            }
        });

        allCoursesAdapter = new AllCoursesAdapter(this, courses);
        rvAllCourses.setAdapter(allCoursesAdapter);
        rvAllCourses.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


    }

    private void showMenu(View view){
        PopupMenu hamb_menu = new PopupMenu(HomeActivity.this,view);
        hamb_menu.getMenuInflater().inflate(R.menu.hamburger_menu,hamb_menu.getMenu());
        hamb_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.home_page)
                {
                    //balik ke home
                }
                else if(menuItem.getItemId() == R.id.courses_page)
                {
                    Intent intent = null;
                    //intent =new Intent(this, c)    //blm ad courses page anjay
                    //balik ke page courses
                }
                else if(menuItem.getItemId() == R.id.account_page)
                {
                    //balik ke page account
                    //Toast.makeText(HomeActivity.this,"udh berhasil",Toast.LENGTH_SHORT).show();
                    Intent intent = null;
                    intent = new Intent(HomeActivity.this, AccountActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
        hamb_menu.show();
    }

    private void fetchAllCourses() {
        RequestQueue rq = Volley.newRequestQueue(HomeActivity.this);
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
                    allCoursesAdapter.setCourses(courses);
                    allCoursesAdapter.notifyDataSetChanged();
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

    private void fetchRecommendedCourse() {
        RequestQueue rq = Volley.newRequestQueue(HomeActivity.this);
        StringRequest sr = APIHelper.createGetRequest(APIHelper.BASE_URL + "courses/recommended", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    APIResult result = new APIResult(new JSONObject(response));
                    JSONArray coursesArr = (JSONArray) result.getResult();
                    for (int i = 0; i < coursesArr.length(); i++) {
                        JSONObject courseObj = coursesArr.getJSONObject(i);
                        int CourseID = courseObj.getInt("CourseID");
                        String CourseAuthor = courseObj.getString("CourseAuthor");
                        String CourseTitle = courseObj.getString("CourseTitle");
                        String CourseDescription = courseObj.getString("CourseDescription");
                        Course course = new Course(CourseID, CourseTitle, CourseDescription);
                        coursesRecommended.add(course);
                        authorsRecommended.add(CourseAuthor);
                    }
                    recommendedAdapter.setCourses(coursesRecommended);
                    recommendedAdapter.setAuthors(authorsRecommended);
                    recommendedAdapter.notifyDataSetChanged();
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