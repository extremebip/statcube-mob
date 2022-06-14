package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_activity);


        Spinner spinnerPayment =findViewById(R.id.spinner_payments);
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.payment, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);


        spinnerPayment.setAdapter(adapter);

    }
}