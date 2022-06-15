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
import com.example.statcube.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AccountActivity extends ToolBarActivity {
    Button btnsubscribe,btnchangepass,btnlogout;
    ImageView subsimg;
    TextView subsenddate, username, email;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "mySharedPreference";
    private static final String KEY_ID = "userId";

    private User user;

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

    private void fetchUserById(Integer userId) {
        RequestQueue rq = Volley.newRequestQueue(AccountActivity.this);
        StringRequest sr = APIHelper.createGetRequest(APIHelper.BASE_URL + "users/" + userId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    APIResult result = new APIResult(new JSONObject(response));
                    JSONArray coursesArr = (JSONArray) result.getResult();
                    for (int i = 0; i < coursesArr.length(); i++) {
                        JSONObject courseObj = coursesArr.getJSONObject(i);
                        int UserID = courseObj.getInt("UserID");
                        String UserName = courseObj.getString("UserName");
                        String UserEmail = courseObj.getString("UserEmail");
                        String UserSubscribeEndDate = courseObj.getString("UserSubscriptionEndDate");
                        Date SubscriptionEndDate = null;
                        try {
                            SubscriptionEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(UserSubscribeEndDate);
                        } catch (ParseException e) {
                            Log.e("Get Data", "Parse Date Error");
                        }
                        user = new User(UserID, UserName, UserEmail, SubscriptionEndDate);
                    }
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