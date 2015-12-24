package com.sofientouati.ISSATsoLibrary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent main=getIntent();
        main.getExtras();
        String txtemail = null,txtpass = null;

        txtemail =main.getStringExtra("email");


        txtpass= main.getStringExtra("pass");

        EditText email= (EditText) findViewById(R.id.input_emaail);
        EditText pass= (EditText) findViewById(R.id.input_password);
        email.setText(txtemail);
        pass.setText(txtpass);

        DatePicker datePicker= (DatePicker) findViewById(R.id.datePicker);






    }
}
