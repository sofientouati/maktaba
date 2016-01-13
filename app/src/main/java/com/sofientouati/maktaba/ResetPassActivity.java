package com.sofientouati.maktaba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.sofientouati.ISSATsoLibrary.R;

public class ResetPassActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private TextInputLayout inputLayoutEmail;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        final Intent intent=getIntent();
        intent.getExtras();
        email= (EditText) findViewById(R.id.input_emaail);


        String mail=intent.getStringExtra("email");

        email.setText(mail);



        inputLayoutEmail= (TextInputLayout) findViewById(R.id.input_layout_email);
        Button btn= (Button) findViewById(R.id.btnSingUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitForm();
                showProgressBar("SendingEmail");
                if (!isNetworkAvailable()){
                    dismissProgressBar();
                    inputLayoutEmail.setError("no connection");
                    //showSnackBar("no connection");

                }else {
                ParseUser.requestPasswordResetInBackground(email.getText().toString(), new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // An email was successfully sent with reset instructions.


                            Intent intent1 = new Intent(ResetPassActivity.this, LoginActivity.class);
                            startActivity(intent1);
                        } else {
                            // Something went wrong. Look at the ParseException to see what's up.
                            dismissProgressBar();
                            showSnackBar("please check your email to reset the password");
                            dismissProgressBar();
                        }


                        }

                });
            }
        }});

    }
//snack bar
    public void showSnackBar(String message){

        Snackbar s=Snackbar.make(findViewById(R.id.coordinatorLayout), message, Snackbar.LENGTH_SHORT);
        s.show();
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
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }



    }
    private boolean validateEmail() {
        String mail = email.getText().toString().trim();


        if (mail.isEmpty() || !isValidEmail(mail)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(email);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }



    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

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

            }
        }
    }
    }

