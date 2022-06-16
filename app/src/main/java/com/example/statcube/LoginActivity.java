package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statcube.api.APIHelper;
import com.example.statcube.api.APIResult;
import com.example.statcube.model.User;
import com.example.statcube.model.validation.EmailRule;
import com.example.statcube.model.validation.PasswordRule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button btnlogin;
    EditText log_email, log_pass;
    int userid = -1;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "mySharedPreference";
    private static final String KEY_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);

        btnlogin = findViewById(R.id.btn_login);
        log_email = findViewById(R.id.login_email);
        log_pass = findViewById(R.id.login_password);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = log_email.getText().toString();
                String password = log_pass.getText().toString();

                boolean validateemail = validateEmail(email);
                boolean validatepassword = validatePassword(password);

                if (!validateemail || !validatepassword) {
                    return;
                }

                Map<String, String> body = new HashMap<String, String>();
                body.put("UserEmail", email);
                body.put("UserPassword", password);

                RequestQueue rq = Volley.newRequestQueue(LoginActivity.this);

                StringRequest jor = APIHelper.createPostRequest(APIHelper.BASE_URL + "users/login", body, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        User user = null;
                        try {
                            APIResult result = new APIResult(new JSONObject(response));
                            JSONObject userResult = (JSONObject) result.getResult();

                            int UserID = userResult.getInt("UserID");
                            String UserName = userResult.getString("UserName");
                            String UserEmail = userResult.getString("UserEmail");
                            String UserSubscriptionEndDateString = userResult.getString("UserSubscriptionEndDate");
                            Date SubscriptionEndDate = null;
                            try {
                                SubscriptionEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(UserSubscriptionEndDateString);
                            } catch (ParseException e) {
                                Log.e("Get Data", "Parse Date Error");
                            }
                            user = new User(UserID, UserName, UserEmail, null, SubscriptionEndDate);
                            userid = UserID;
                        } catch (JSONException e) { }
                        // session
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(KEY_ID, userid);
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        String dialogMessage = "Login Failed";
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
                                    if (errMessages.has("UserEmail")) {
                                        dialogMessage = errMessages.getString("UserEmail");
                                    } else if (errMessages.has("UserPassword")) {
                                        dialogMessage = errMessages.getString("UserPassword");
                                    } else {
                                        dialogMessage = "Login Failed";
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
                        AlertDialog.Builder builder =new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage(dialogMessage);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                rq.add(jor);
            }
        });
    }

    private boolean validateEmail(String email){
        EmailRule rule = new EmailRule(email);
        rule.validate();
        log_email.setError(null);
        if (!rule.isValid()){
            log_email.setError(rule.getErrorMessage());
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password){
        log_pass.setError(null);
        if (password.isEmpty()){
            log_pass.setError("Password must not be empty");
            return false;
        }
        return true;
    }
}