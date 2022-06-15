package com.example.statcube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity {
    TextView tbar_title;
    Button paybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_activity);

        Intent intent = getIntent();

        paybtn =findViewById(R.id.paymentbtn);
        tbar_title = findViewById(R.id.toolbar_title);
        tbar_title.setText("Payment");


        Spinner spinnerPayment =findViewById(R.id.spinner_payments);
        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.payment, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPayment.setAdapter(adapter);

        int idx = spinnerPayment.getSelectedItemPosition();


        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idx = spinnerPayment.getSelectedItemPosition();
                if(idx == 0){
                    Toast.makeText(PaymentActivity.this, "Please choose payment method", Toast.LENGTH_SHORT).show();
                }
                else{
                    Calendar calender = Calendar.getInstance();
                    String currentdate = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calender.getTime());
                    Log.i("test", currentdate);

                    String[] splitDate = currentdate.split("/");
                    String monthstr = "";
                    int date = Integer.parseInt(splitDate[1]);
                    int month = Integer.parseInt(splitDate[0]);
                    String year = (splitDate[2]);
                    year = "20"+year;
                    Log.i("date",String.valueOf(date));
                    Log.i("month",String.valueOf(month));
                    Log.i("year",(year));

                    if(month == 1){
                        monthstr = "January";
                    }else if(month == 2){
                        monthstr = "February";
                    }else if(month == 3){
                        monthstr = "March";
                    }else if(month == 4){
                        monthstr = "April";
                    }else if(month == 5){
                        monthstr = "May";
                    }else if(month == 6){
                        monthstr = "June";
                    }else if(month == 7){
                        monthstr = "July";
                    }else if(month == 8){
                        monthstr = "August";
                    }else if(month == 9){
                        monthstr = "September";
                    }else if(month == 10){
                        monthstr = "October";
                    }else if(month == 11){
                        monthstr = "November";
                    }else if(month == 12){
                        monthstr = "December";
                    }

                    Log.i("bulan",monthstr);
                    String subdate = String.valueOf(date) + " " + monthstr + " " +year;
                    Log.i("subdate", subdate);


                    Intent intent = new Intent(PaymentActivity.this, AccountActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}