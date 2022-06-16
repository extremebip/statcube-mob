package com.example.statcube;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statcube.adapter.PostAdapter;
import com.example.statcube.api.APIHelper;
import com.example.statcube.api.APIResult;
import com.example.statcube.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DiscussionDetailActivity extends ToolBarActivity {

    TextView tvDiscussionTitle, tvDiscussionUsername, tvDiscussionDate, tvDiscussionContent, tvNoComment;
    EditText etAddPost;
    LinearLayout addPostWrapper;

    RequestQueue rq;
    ArrayList<Post> posts = new ArrayList<>();
    private static final String SHARED_PREFERENCE_NAME = "mySharedPreference";
    private static final String KEY_ID = "userId";

    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_detail);
        initializeToolBar("Discussion",1);

        Intent intent = getIntent();
        int DiscussionID = intent.getIntExtra("DiscussionID", 0);

        rq = Volley.newRequestQueue(this);

        tvDiscussionTitle = findViewById(R.id.tv_discussion_title);
        tvDiscussionUsername = findViewById(R.id.tv_discussion_username);
        tvDiscussionDate = findViewById(R.id.tv_discussion_date);
        tvDiscussionContent = findViewById(R.id.tv_discussion_content);
        tvNoComment = findViewById(R.id.tv_no_comment);
        etAddPost = findViewById(R.id.et_add_post);
        addPostWrapper = findViewById(R.id.add_post_wrapper);

        tvDiscussionTitle.setText("Title: ");
        tvDiscussionUsername.setText("Writer: ");
        tvDiscussionDate.setText("Date: ");

        RecyclerView rvPosts = findViewById(R.id.rv_posts);
        postAdapter = new PostAdapter(DiscussionDetailActivity.this);
        postAdapter.setPosts(posts);
        rvPosts.setAdapter(postAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fetchDiscussionDetail(DiscussionID);
    }

    private void fetchDiscussionDetail(int DiscussionID) {
        String endpoint = String.format("discussions/%d/detail", DiscussionID);
        StringRequest sr = APIHelper.createGetRequest(APIHelper.BASE_URL + endpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm");

                try {
                    APIResult result = new APIResult(new JSONObject(response));
                    JSONObject discussionObj = (JSONObject) result.getResult();
                    String discussionTitle = discussionObj.getString("DiscussionTitle");
                    String discussionAuthor = discussionObj.getString("DiscussionAuthor");
                    String discussionDateString = discussionObj.getString("DiscussionDate");
                    String discussionContent = discussionObj.getString("DiscussionContent");

                    Date discussionDate = parser.parse(discussionDateString);

                    tvDiscussionTitle.setText("Title : " + discussionTitle);
                    tvDiscussionUsername.setText("Writer : " + discussionAuthor);
                    tvDiscussionDate.setText("Date : " + formatter.format(discussionDate));
                    tvDiscussionContent.setText(discussionContent);

                    JSONArray postsArr = discussionObj.getJSONArray("Posts");
                    for (int i = 0; i < postsArr.length(); i++) {
                        JSONObject postObj = postsArr.getJSONObject(i);
                        int PostID = postObj.getInt("PostID");
                        int DiscussionID = postObj.getInt("DiscussionID");
                        int UserID = postObj.getInt("UserID");
                        String author = postObj.getString("PostAuthor");
                        String content = postObj.getString("PostContent");
                        String postDateString = postObj.getString("PostDate");
                        boolean isDeleted = postObj.getBoolean("PostDeleted");

                        Post post = new Post(
                            PostID, DiscussionID, UserID, author, content, parser.parse(postDateString), isDeleted
                        );
                        posts.add(post);
                    }
                    postAdapter.setPosts(posts);
                    postAdapter.notifyDataSetChanged();
                    if(posts.size() == 0){
                        tvNoComment.setText("There are no comment yet");
                    }
                } catch (JSONException e) {
                    Log.e("Error", "Parsing JSON Error");
                } catch (Exception e) {
                    Log.e("Error", "API Error");
                }
                addPostWrapper.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitPost(DiscussionID);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(sr);
    }

    private void submitPost(int DiscussionID) {
        String postContent = etAddPost.getText().toString();

        etAddPost.setError(null);
        if (postContent.length() == 0) {
            etAddPost.setError("Post Content must not be empty");
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int userId = sharedPreferences.getInt(KEY_ID, -1);

        Map<String, String> body = new HashMap<String, String>();
        body.put("DiscussionID", Integer.valueOf(DiscussionID).toString());
        body.put("UserID", Integer.valueOf(userId).toString());
        body.put("PostContent", postContent);

        StringRequest sr = APIHelper.createPostRequest(APIHelper.BASE_URL + "posts", body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    APIResult result = new APIResult(new JSONObject(response));
                    JSONObject postObj = (JSONObject) result.getResult();
                    int PostID = postObj.getInt("PostID");
                    int DiscussionID = postObj.getInt("DiscussionID");
                    int UserID = postObj.getInt("UserID");
                    String author = postObj.getString("PostAuthor");
                    String content = postObj.getString("PostContent");
                    String postDateString = postObj.getString("PostDate");
                    boolean isDeleted = postObj.getBoolean("PostDeleted");

                    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    Post post = new Post(
                            PostID, DiscussionID, UserID, author, content, parser.parse(postDateString), isDeleted
                    );
                    posts.add(post);
                    postAdapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
                Toast.makeText(DiscussionDetailActivity.this, "Add Post Success", Toast.LENGTH_SHORT).show();
                etAddPost.getText().clear();
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
                            if (errMessages.has("PostContent")) {
                                etAddPost.setError(errMessages.getString("PostContent"));
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(DiscussionDetailActivity.this);
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