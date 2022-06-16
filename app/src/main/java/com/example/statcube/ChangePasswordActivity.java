package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statcube.api.APIHelper;
import com.example.statcube.api.APIResult;
import com.example.statcube.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends ToolBarActivity {

    EditText oldPass, newPass, confirmPass;
    Button btn_save;
    boolean validate = true;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "mySharedPreference";
    private static final String KEY_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initializeToolBar("Change Password",1);

        oldPass = findViewById(R.id.et_oldPass);
        newPass = findViewById(R.id.et_newPass);
        confirmPass = findViewById(R.id.et_confirmPass);

        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String old_pass = oldPass.getText().toString();
                String new_pass = newPass.getText().toString();
                String confirm_pass = confirmPass.getText().toString();

                validate = validateNew(new_pass);
                validate = validateConfirm(confirm_pass) && validate;

                if (validate) {
                        validateOld(old_pass);
                        Toast toast = Toast.makeText(ChangePasswordActivity.this, "Change Password Success\n", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(ChangePasswordActivity.this, AccountActivity.class);
                        startActivity(intent);
                }
            }
        });

    }

    private void validateOld(String old_pass){
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int userID = sharedPreferences.getInt(KEY_ID, -1);

        Map<String, String> body = new HashMap<String, String>();
        body.put("UserID", String.valueOf(userID));
        body.put("OldPassword", old_pass);
        body.put("NewPassword", newPass.getText().toString());

        RequestQueue rq = Volley.newRequestQueue(ChangePasswordActivity.this);
        StringRequest jor = APIHelper.createPostRequest(APIHelper.BASE_URL + "users/password", body, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    APIResult result = new APIResult(new JSONObject(response));
                    JSONObject userResult = (JSONObject) result.getResult();
                    int userId = userResult.getInt("UserID");
                    String userPassword = userResult.getString("UserPassword");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                String dialogMessage = "";
                if (error instanceof ServerError && response != null){
                    validate = false;
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        APIResult result = new APIResult(obj);
                        Object messages = result.getMessage();
                        if(result.getMessageType().equals("string")){
                            dialogMessage = (String) messages;
                        }else{
                            JSONObject objectMessages = (JSONObject) messages;
                            JSONObject errorMessages = objectMessages.getJSONObject("errors");
                            if(errorMessages.has("OldPassword")){
                                oldPass.setError(errorMessages.getString("OldPassword"));
                            }
                            if (errorMessages.has("NewPassword")){
                                newPass.setError(errorMessages.getString("NewPassword"));
                            }
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (!dialogMessage.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage(dialogMessage);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
        rq.add(jor);
    }

    private boolean validateNew(String new_pass){
        newPass.setError(null);
        if (new_pass.isEmpty()){
            newPass.setError("New password must not be empty");
            return false;
        }
        if (new_pass.length() < 6){
            newPass.setError("New password must be at least 6 characters");
            return false;
        }
        return true;
    }

    private boolean validateConfirm(String confirm_pass){
        confirmPass.setError(null);
        if (confirm_pass.isEmpty()){
            confirmPass.setError("Confirm password must not be empty");
            return false;
        }
        if (!confirm_pass.equals(newPass.getText().toString())){
            confirmPass.setError("Confirm password must be equal to new password");
            return false;
        }
        return true;
    }
}