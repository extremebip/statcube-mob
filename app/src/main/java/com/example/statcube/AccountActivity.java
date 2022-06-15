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
    TextView subsenddate, username, email, subsstatus;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "mySharedPreference";
    private static final String KEY_ID = "userId";

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initializeToolBar("Account",1);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int userID = sharedPreferences.getInt(KEY_ID, -1);

        btnsubscribe =findViewById(R.id.btn_subscribe);
        btnchangepass = findViewById(R.id.btn_change_pass);
        btnlogout = findViewById(R.id.btn_logout);
        subsimg = findViewById(R.id.iv_subscribed);
        subsenddate = findViewById(R.id.subs_end_date);
        username = findViewById(R.id.tv_username);
        email = findViewById(R.id.tv_email);
        subsstatus = findViewById(R.id.tv_subscribed);

        fetchUserById(userID);

        String subscribed = subsenddate.toString();

        btnsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SubscribeActivity.class);
                startActivity(intent);
            }
        });

        if(!subscribed.equals("null"))
        {
            btnsubscribe.setVisibility(View.INVISIBLE);
        }
        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(AccountActivity.this, ChangePasswordActivity.class);
//                startActivity(intent);
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
                    JSONObject UserObj = (JSONObject) result.getResult();
                        int UserID = UserObj.getInt("UserID");
                        String UserName = UserObj.getString("UserName");
                        String UserEmail = UserObj.getString("UserEmail");
                        String UserSubscribeEndDate = UserObj.getString("UserSubscriptionEndDate");

                        username.setText(UserName);
                        email.setText(UserEmail);
//                        Date SubscriptionEndDate = null;
//                        try {
//                            SubscriptionEndDate = new SimpleDateFormat("dd-MM-yyyy").parse(UserSubscribeEndDate);
//                        } catch (ParseException e) {
//                            Log.e("Get Data", "Parse Date Error");
//                        }
                        if(UserSubscribeEndDate.equals("null"))
                        {
                            UserSubscribeEndDate="-";
                            subsimg.setImageResource(R.drawable.unsubscribed);
                            subsstatus.setText("Haven't subscribed");
                        }
                        String day="";String month="";String year="";
                        for(int i=0;i<UserSubscribeEndDate.length();i++)
                        {
                            if(i==0)
                            {
                                String c = Character.toString(UserSubscribeEndDate.charAt(i));
                                String c2 = Character.toString(UserSubscribeEndDate.charAt(i+1));
                                String c3 = Character.toString(UserSubscribeEndDate.charAt(i+2));
                                String c4 = Character.toString(UserSubscribeEndDate.charAt(i+3));
                                year = c+c2+c3+c4;
                            }
                            if(i==5)
                            {
                                String c = Character.toString(UserSubscribeEndDate.charAt(i));
                                String c2 = Character.toString(UserSubscribeEndDate.charAt(i+1));
                                month = c+c2;
                            }
                            if(i==7)
                            {
                                String c = Character.toString(UserSubscribeEndDate.charAt(i));
                                String c2 = Character.toString(UserSubscribeEndDate.charAt(i+1));
                                day = c+c2;
                            }
                        }
                        if(month.equals("1-")){ month = "January";}
                        else if(month.equals("2-")){ month = "February";}
                        else if(month.equals("3-")){ month = "March";}
                        else if(month.equals("4-")){ month = "April";}
                        else if(month.equals("5-")){ month = "May";}
                        else if(month.equals("6-")){ month = "June";}
                        else if(month.equals("7-")){ month = "July";}
                        else if(month.equals("8-")){ month = "August";}
                        else if(month.equals("9-")){ month = "September";}
                        else if(month.equals("10")){ month = "October";}
                        else if(month.equals("11")){ month = "November";}
                        else if(month.equals("12")){ month = "December";}
                        UserSubscribeEndDate = day + " " + month + " " + year ;

                        subsenddate.setText(UserSubscribeEndDate);
                        //user = new User(UserID, UserName, UserEmail, SubscriptionEndDate);

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