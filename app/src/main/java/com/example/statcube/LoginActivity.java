package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    //buat gw pake nanti


//    Button btnlogin, btnrediregister;
//    EditText log_email, log_pass;
//    ArrayList<Database> database  = new ArrayList<>();
//    int userid=-1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        btnlogin = findViewById(R.id.btn_login);
//        btnrediregister = findViewById(R.id.btn_redi_register);
//        log_email = findViewById(R.id.login_email);
//        log_pass = findViewById(R.id.login_password);
//
//        ArrayList<Database> temp  = new ArrayList<>();
//        temp = (ArrayList<Database>)getIntent().getSerializableExtra("userdetails");
//
//        if(temp!=null)
//        {
//            for(int i=0;i<temp.size();i++)
//            {
//                String tempemail = temp.get(i).getEmail();
//                String temppassword = temp.get(i).getPassword();
//                String tempphonenumber = temp.get(i).getPhonenumber();
//                ArrayList tempfavorite = temp.get(i).getFavorite();
//                database.add(new Database(tempemail,temppassword,tempphonenumber,tempfavorite));
//            }
//        }
//
//        btnlogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String email =log_email.getText().toString();
//                String password = log_pass.getText().toString();
//
//                boolean validateemail = false;
//                boolean validatepassword=false;
//
//
//                if(database!=null) {
//                    for (int i = 0; i < database.size(); i++) {
//                        String reg_email = database.get(i).getEmail();
//                        String reg_password = database.get(i).getPassword();
//                        if (reg_email.equals(email)) {
//                            validateemail = true;
//                        }
//                        if (email.equals(reg_email) && password.equals(reg_password)) {
//                            validatepassword = true;
//                            userid=i;
//                        }
//                    }
//                }
//
//                if(validatepassword==true)
//                {
//                    Intent intent2= new Intent(LoginActivity.this, CampusActivity.class);
//                    intent2.putExtra("userdetails",database);
//                    intent2.putExtra("userid",userid);
//                    startActivity(intent2);
//                }
//                else
//                {
//                    String message = "";
//                    if(!validateemail)
//                    {
//                        message = message + "Email does not exist";
//                    }
//                    if(!validatepassword && validateemail)
//                    {
//                        message =message + "Incorrect Password";
//                    }
//                    AlertDialog.Builder builder =new AlertDialog.Builder(LoginActivity.this);
//                    builder.setTitle("Error Message");
//                    builder.setMessage(message);
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
//                }
//            }
//        });
//
//        btnrediregister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(LoginActivity.this, RegisterActivity.class);
//                intent.putExtra("userdetails", database);
//                Toast toast = Toast.makeText(LoginActivity.this, "Redirecting to register page\n", Toast.LENGTH_SHORT);
//                toast.show();
//                startActivity(intent);
//            }
//        });
//    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent2= new Intent(LoginActivity.this, LoginActivity.class);
//        intent2.putExtra("userdetails",database);
//        intent2.putExtra("userid",userid);
//        startActivity(intent2);
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}