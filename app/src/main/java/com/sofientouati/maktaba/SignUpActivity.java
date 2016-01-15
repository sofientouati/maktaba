package com.sofientouati.maktaba;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sofientouati.ISSATsoLibrary.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {
private TextInputLayout inputLayoutEmail,inputLayoutPass,inputLayoutCin,inputLayoutCen,inputLayoutName,inputLayoutSurname,inputLayoutDate;
    private EditText inputEmail,inputPass,inputCin,inputCen,inputName,inputSurname,inputDate;
    private ImageView backBtn;
    private Intent intent;
    private Spinner spinner;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent main=getIntent();
        main.getExtras();
        String txtemail = null,txtpass = null;

        txtemail =main.getStringExtra("email");


        txtpass= main.getStringExtra("pass");

        EditText email= (EditText) findViewById(R.id.input_emaail);
        EditText pass= (EditText) findViewById(R.id.input_password);
        email.setText(txtemail);
        pass.setText(txtpass);
        spinner= (Spinner) findViewById(R.id.division);
        inputLayoutEmail= (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPass= (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutCen= (TextInputLayout) findViewById(R.id.input_layout_cen);
        inputLayoutCin= (TextInputLayout) findViewById(R.id.input_layout_cin);
        inputLayoutName= (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutSurname= (TextInputLayout) findViewById(R.id.input_layout_surname);
        inputLayoutDate= (TextInputLayout) findViewById(R.id.input_layout_date);
        inputEmail = (EditText) findViewById(R.id.input_emaail);
        inputPass = (EditText) findViewById(R.id.input_password);
        inputCin = (EditText) findViewById(R.id.input_cin);
        inputCen = (EditText) findViewById(R.id.input_cen);
        inputName = (EditText) findViewById(R.id.input_name);
        inputSurname = (EditText) findViewById(R.id.input_surname);
        inputDate = (EditText) findViewById(R.id.input_date);
         inputDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
                 if(hasFocus){
                     DateDialog dialog=new DateDialog(v);
                     FragmentTransaction ft =getFragmentManager().beginTransaction();
                     dialog.show(ft, "DatePicker");

                 }
             }
         });


        backBtn= (ImageView) findViewById(R.id.btnBack);

        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                intent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                return false;
            }
        });
        Button btn= (Button) findViewById(R.id.btnContinue);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();

            }
        });









    }
    //progress bar
    public void showProgressBar(String message){
        progressDialog = ProgressDialog.show(this, "", message, true);
    }

    public void dismissProgressBar(){
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    //checking for network
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();}
//submit form
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }
        if (!validatePass()){
            return;
    }
        if (!validateCin()){
            return;
    }
        if(!validateCen()){
            return;
        }
        if(!validateName()){
            return;
        }
        //checking division
        spinner= (Spinner) findViewById(R.id.division);
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;
            String s= selectedTextView.getText().toString();
            if (selectedTextView.getText().toString().equals("Choose Your Division")) {
                        /*String errorString = "select your division";
                        selectedTextView.setError(errorString);
                        selectedTextView.setErrorTe*/
                TextView errorText = (TextView)spinner.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Choose Your Division");//changes the selected item text to this
            }
            else {
                selectedTextView.setError(null);
            }
        }

    }


    //validating email

    private boolean validateEmail() {
        String mail = inputEmail.getText().toString().trim();


        if (mail.isEmpty() || !isValidEmail(mail)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    //validate Pass()
    private boolean validatePass() {
        String pass=inputPass.getText().toString().trim();

        if(pass.isEmpty()){
            inputLayoutPass.setError(getString(R.string.err_msg_password));
            requestFocus(inputPass);
            return false;}
        else{inputLayoutPass.setErrorEnabled(false);
        }
        return true;
    }
    //validate cen



    private boolean validateCen() {
        String cen = inputCen.getText().toString().trim();


        if (cen.isEmpty() || !isValidCen(cen)) {
            inputLayoutCen.setError(getString(R.string.err_msg_cen));
            requestFocus(inputCen);
            return false;
        } else {
            inputLayoutCen.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidCen(String cen) {
        return !TextUtils.isEmpty(cen) && cen.length()==7;
    }

    // validate cin



    private boolean validateCin() {
        String cin = inputCin.getText().toString().trim();


        if (cin.isEmpty() || !isValidCin(cin)) {
            inputLayoutCin.setError(getString(R.string.err_msg_cin));
            requestFocus(inputCin);
            return false;
        } else {
            inputLayoutCin.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidCin(String cin) {
        return !TextUtils.isEmpty(cin) && cin.length()==8;
    }

    //validate name

    private boolean validateName() {
        String name = inputName.getText().toString().trim();


        if (name.isEmpty() || !isValidName(name)) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidName(String name) {
        return !TextUtils.isEmpty(name) ;
    }

    //validate surname

    private boolean validateSurname() {
        String surname = inputSurname.getText().toString().trim();


        if (surname.isEmpty() || !isValidSurname(surname)) {
            inputLayoutSurname.setError(getString(R.string.err_msg_surname));
            requestFocus(inputSurname);
            return false;
        } else {
            inputLayoutSurname.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidSurname(String surname) {
        return !TextUtils.isEmpty(surname) ;
    }

    //validate birth date



    private boolean validateDate() {
        String date = inputDate.getText().toString().trim();


        if (date.isEmpty() && isValidDate(date)) {
            inputLayoutDate.setError(getString(R.string.err_msg_date));
            requestFocus(inputDate);
            return false;
        } else {
            inputLayoutDate.setErrorEnabled(false);
        }

        return true;

    }
    private static boolean isValidDate(String Date) {
        return !TextUtils.isEmpty(Date) ;
    }
//main sets
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
        private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_emaail:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePass();
                    break;
                case R.id.input_layout_cin:
                    validateCin();
                    break;
                case R.id.input_layout_cen:
                    validateCen();
                    break;
                case R.id.input_layout_name:
                    validateName();
                    break;
                case R.id.input_layout_surname:
                    validateSurname();
                    break;
                case R.id.input_layout_date:
                    validateDate();
                    break;
            }
        }
    }
}



