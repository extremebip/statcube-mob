package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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
import com.example.statcube.model.User;
import com.example.statcube.model.validation.EmailRule;
import com.example.statcube.model.validation.PasswordRule;
import com.example.statcube.model.validation.UsernameRule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initRegisterForm();
    }

    private void initRegisterForm() {
        etUsername = findViewById(R.id.register_username);
        etEmail = findViewById(R.id.register_email);
        etPassword = findViewById(R.id.register_password);
        etConfirmPassword = findViewById(R.id.confirm_password);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                boolean isValid = validateUsername(username);
                isValid = validateEmail(email) && isValid;
                isValid = validatePassword(password) && isValid;

                if (isValid) {
                    submitRegister(new User(0, username, email, password, null));
                }
            }
        });
    }

    private boolean validateUsername(String username) {
        UsernameRule rule = new UsernameRule(username);
        rule.validate();
        etUsername.setError(null);
        if (!rule.isValid()){
            etUsername.setError(rule.getErrorMessage());
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email){
        EmailRule rule = new EmailRule(email);
        rule.validate();
        etEmail.setError(null);
        if (!rule.isValid()){
            etEmail.setError(rule.getErrorMessage());
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password){
        PasswordRule rule = new PasswordRule(password);
        rule.validate();

        if (!rule.isValid()){
            etPassword.setError(rule.getErrorMessage());
            return false;
        }

        String confirmPassword = etConfirmPassword.getText().toString();
        if (!password.equals(confirmPassword)) {
            etPassword.setError("Confirm Password does not match!");
            return false;
        } else {
            etPassword.setError(null);
        }
        return true;
    }

    private void submitRegister(User user) {
        Map<String, String> body = new HashMap<String, String>();
        body.put("UserName", user.getUserName());
        body.put("UserEmail", user.getUserEmail());
        body.put("UserPassword", user.getUserPassword());

        RequestQueue rq = Volley.newRequestQueue(RegisterActivity.this);
        StringRequest sr = APIHelper.createPostRequest(APIHelper.BASE_URL + "users", body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
                            if (errMessages.has("UserName")) {
                                etUsername.setError(errMessages.getString("UserName"));
                            }
                            if (errMessages.has("UserEmail")) {
                                etEmail.setError(errMessages.getString("UserEmail"));
                            }
                            if (errMessages.has("UserPassword")) {
                                etPassword.setError(errMessages.getString("UserPassword"));
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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