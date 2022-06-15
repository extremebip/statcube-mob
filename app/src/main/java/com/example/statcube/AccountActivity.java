package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statcube.api.APIHelper;
import com.example.statcube.api.APIResult;
import com.example.statcube.model.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AccountActivity extends ToolBarActivity {
    Button btnsubscribe,btnchangepass,btnlogout;
    ImageView subsimg;
    TextView subsenddate, username, email;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "mySharedPreference";
    private static final String KEY_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initializeToolBar("Account",1);

        int userID = sharedPreferences.getInt(KEY_ID, -1);

        btnsubscribe =findViewById(R.id.btn_subscribe);
        btnchangepass = findViewById(R.id.btn_change_pass);
        btnlogout = findViewById(R.id.btn_logout);
        subsimg = findViewById(R.id.iv_subscribed);
        subsenddate = findViewById(R.id.subs_end_date);
        username = findViewById(R.id.tv_username);
        email = findViewById(R.id.tv_email);

        btnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SubscribeActivity.class);
                startActivity(intent);
            }
        });
        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}