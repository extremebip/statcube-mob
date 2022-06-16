package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class ToolBarActivity extends AppCompatActivity {
    TextView tbtitle;
    ImageView back_btn,hamb_menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    protected void initializeToolBar(String title, int back)
    {
        tbtitle = findViewById(R.id.toolbar_title);
        back_btn = findViewById(R.id.back_arrow);
        hamb_menu_btn = findViewById(R.id.hamb_menu);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        hamb_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });
        tbtitle.setText(title);
        if(back==0)
        {
            back_btn.setVisibility(View.INVISIBLE);
        }
        else
        {
            back_btn.setVisibility(View.VISIBLE);
        }
    }
    private void showMenu(View view){
        PopupMenu hamb_menu = new PopupMenu(getApplicationContext(),view);
        hamb_menu.getMenuInflater().inflate(R.menu.hamburger_menu,hamb_menu.getMenu());
        hamb_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.home_page)
                {
                    Intent intent = null;
                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(menuItem.getItemId() == R.id.courses_page)
                {
                    Intent intent = null;
                    intent = new Intent(getApplicationContext(), AllCourseActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(menuItem.getItemId() == R.id.account_page)
                {
                    //balik ke page account
                    Intent intent = null;
                    intent = new Intent(getApplicationContext(), AccountActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
        hamb_menu.show();
    }
}