package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.statcube.adapter.AllCoursesAdapter;
import com.example.statcube.adapter.RecommendedAdapter;
import com.example.statcube.model.Course;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ArrayList<String> titlesRecommended = new ArrayList<>(); // courseRecommended
    ArrayList<String> authorsRecommended = new ArrayList<>(); // authorRecommended
    ArrayList<Course> courses = new ArrayList<>();

    TextView btnViewAll,tbtitle;
    RecyclerView rvRecommended, rvAllCourses;
    ImageView back_btn,hamb_menu_btn;

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
}