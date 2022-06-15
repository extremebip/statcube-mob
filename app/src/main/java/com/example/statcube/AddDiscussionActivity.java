package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statcube.api.APIHelper;
import com.example.statcube.api.APIResult;
import com.example.statcube.model.Discussion;
import com.example.statcube.model.User;
import com.example.statcube.model.validation.UsernameRule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddDiscussionActivity extends AppCompatActivity {

    ImageView back_btn,hamb_menu_btn,postbtn;
    TextView tbtitle;
    EditText etDiscussionTitle, etDiscussionContent;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "mySharedPreference";
    private static final String KEY_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        int topicID = b.getInt("TopicID");

        tbtitle = findViewById(R.id.toolbar_title);
        back_btn = findViewById(R.id.back_arrow);
        hamb_menu_btn = findViewById(R.id.hamb_menu);
        etDiscussionTitle =  findViewById(R.id.et_discussion_title);
        etDiscussionContent = findViewById(R.id.et_discussion_content);

        tbtitle.setText("Discussion");

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int userID = sharedPreferences.getInt(KEY_ID, -1);

        hamb_menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String discussionTitle = etDiscussionTitle.getText().toString();
                String discussionContent = etDiscussionContent.getText().toString();
                Date discussionDate = Calendar.getInstance().getTime();
                submitDiscussion(new Discussion(0, topicID, userID, discussionDate, discussionTitle, discussionContent));
            }
        });
    }

    private void showMenu(View view){
        PopupMenu hamb_menu = new PopupMenu(AddDiscussionActivity.this,view);
        hamb_menu.getMenuInflater().inflate(R.menu.hamburger_menu,hamb_menu.getMenu());
        hamb_menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.home_page)
                {
                    //balik ke home
                    Intent intent = null;
                    intent = new Intent(AddDiscussionActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
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
                    intent = new Intent(AddDiscussionActivity.this, AccountActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
        hamb_menu.show();
    }
    private void submitDiscussion(Discussion discussion) {
        Map<String, String> body = new HashMap<String, String>();
        body.put("TopicID", discussion.getTopicID().toString());
        body.put("UserID",discussion.getUserID().toString());
        body.put("DiscussionTitle", discussion.getDiscussionTitle());
        body.put("DiscussionContent", discussion.getDiscussionContent());

        RequestQueue rq = Volley.newRequestQueue(AddDiscussionActivity.this);
        StringRequest sr = APIHelper.createPostRequest(APIHelper.BASE_URL + "discussions", body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(AddDiscussionActivity.this, DiscussionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                String dialogMessage = "";
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        APIResult result = new APIResult(obj);
                        Object messages = result.getMessage();
                        if (result.getMessageType().equals("string"))  {
                            dialogMessage = (String) messages;
                        } else {
                            JSONObject objectMessages = (JSONObject) messages;
                            JSONObject errMessages = objectMessages.getJSONObject("errors");
                            if (errMessages.has("DiscussionTitle")) {
                                etDiscussionTitle.setError(errMessages.getString("DiscussionTitle"));
                            }
                            if (errMessages.has("DiscussionContent")) {
                                etDiscussionContent.setError(errMessages.getString("DiscussionContent"));
                            }
                        }
                    } catch (UnsupportedEncodingException e1) {
                        Log.e("API Error", "API Error");
                        dialogMessage = "There is a problem with the system";
                    } catch (JSONException e2) {
                        Log.e("API Error", "API Error");
                        dialogMessage = "There is a problem with the system";
                    }
                }
                if (!dialogMessage.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddDiscussionActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage(dialogMessage);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        rq.add(sr);
    }

}