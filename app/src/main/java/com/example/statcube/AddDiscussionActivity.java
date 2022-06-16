package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.statcube.model.Topic;
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

public class AddDiscussionActivity extends ToolBarActivity {

    Button postbtn;
    EditText etDiscussionTitle, etDiscussionContent;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "mySharedPreference";
    private static final String KEY_ID = "userId";
    Topic topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);
        initializeToolBar("Discussion", 1);

        Intent intent = getIntent();
        topic = (Topic) intent.getSerializableExtra("Topic");

        etDiscussionTitle =  findViewById(R.id.et_discussion_title);
        etDiscussionContent = findViewById(R.id.et_discussion_content);
        postbtn = findViewById(R.id.btn_post);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int userID = sharedPreferences.getInt(KEY_ID, -1);

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String discussionTitle = etDiscussionTitle.getText().toString();
                String discussionContent = etDiscussionContent.getText().toString();
                submitDiscussion(new Discussion(0, topic.getTopicID(), userID, discussionTitle, discussionContent));
            }
        });
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
                Bundle bundle = new Bundle();
                bundle.putSerializable("topic", topic);
                intent.putExtras(bundle);
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