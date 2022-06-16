package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.example.statcube.api.APIHelper;
import com.example.statcube.api.APIResult;
import com.example.statcube.model.Course;
import com.example.statcube.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends ToolBarActivity {

    Button paybtn;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_NAME = "mySharedPreference";
    private static final String KEY_ID = "userId";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_activity);
        initializeToolBar("Payment",1);

        Intent intent = getIntent();

        paybtn =findViewById(R.id.paymentbtn);


        Spinner spinnerPayment =findViewById(R.id.spinner_payments);
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.payment, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPayment.setAdapter(adapter);

        int idx = spinnerPayment.getSelectedItemPosition();

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        int userID = sharedPreferences.getInt(KEY_ID, -1);

        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idx = spinnerPayment.getSelectedItemPosition();
                String paymethod = "";
                if(idx == 1) paymethod = "BCA";
                else if(idx == 2) paymethod = "BNI";
                else if(idx == 3) paymethod = "MANDIRI";
                else if(idx == 4) paymethod = "CIMB NIAGA";
                else if(idx == 5) paymethod = "DBS";

                if(idx == 0){
                    Toast.makeText(PaymentActivity.this, "Please choose payment method", Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String, String> body = new HashMap<String, String>();
                    body.put("UserID", String.valueOf(userID));
                    body.put("PaymentMethod", paymethod);

                    Log.i("pay", String.valueOf(userID));

                    RequestQueue rq = Volley.newRequestQueue(PaymentActivity.this);
                    StringRequest sr = APIHelper.createPostRequest(APIHelper.BASE_URL + "users/subscribe", body, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent intent = new Intent(PaymentActivity.this, AccountActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            
                        }
                    });

                    rq.add(sr);
                }
            }
        });

    }
}