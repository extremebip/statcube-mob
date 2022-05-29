package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.statcube.model.validation.EmailRule;
import com.example.statcube.model.validation.PasswordRule;
import com.example.statcube.model.validation.UsernameRule;

public class RegisterActivity extends AppCompatActivity {

    Switch swGender;
    TextView tvMale, tvFemale;
    EditText etUsername, etEmail, etPassword, etConfirmPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        swGender = findViewById(R.id.register_gender);
        tvMale = findViewById(R.id.tv_switch_gender_male);
        tvFemale = findViewById(R.id.tv_switch_gender_female);
        swGender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    tvMale.setVisibility(View.VISIBLE);
                    tvFemale.setVisibility(View.GONE);
                } else {
                    tvMale.setVisibility(View.GONE);
                    tvFemale.setVisibility(View.VISIBLE);
                }
            }
        });

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
                    // TODO: Create User

                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
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

        boolean isUnique = true;
        // TODO: Check Unique to DB
//        for (int i = 0; i < users.size(); i++){
//            if (email.equals(users.get(i).getUserEmailAddress())){
//                isUnique = false;
//                break;
//            }
//        }

        if (!isUnique){
            etEmail.setError("Email must be unique");
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
        } else {
            etPassword.setError(null);
        }
        return true;
    }
}