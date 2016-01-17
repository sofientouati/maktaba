package com.sofientouati.maktaba;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.sofientouati.ISSATsoLibrary.R;

public class ResetPassActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private TextInputLayout inputLayoutEmail;
    private EditText email;
    private Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final Intent intent=getIntent();
        intent.getExtras();
        email= (EditText) findViewById(R.id.input_emaail);


        String mail=intent.getStringExtra("email");

        email.setText(mail);


        ImageView backBtn= (ImageView) findViewById(R.id.btnBack);
        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ResetPassActivity.this.intent1=new Intent(ResetPassActivity.this,LoginActivity.class);
                ResetPassActivity.this.startActivity(intent1);
                return false;
            }
        });
        inputLayoutEmail= (TextInputLayout) findViewById(R.id.input_layout_email);
        final Button btn= (Button) findViewById(R.id.btnSingUp);
        email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                    // NOTE: In the author's example, he uses an identifier
                    // called searchBar. If setting this code on your EditText
                    // then use v.getWindowToken() as a reference to your
                    // EditText is passed into this callback as a TextView

                    in.hideSoftInputFromWindow(email
                                    .getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    btn.performClick();

                    return true;
                }
                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitForm();
                showProgressBar("SendingEmail");
                if (!isNetworkAvailable()) {
                    dismissProgressBar();
                    inputLayoutEmail.setError("no connection");
                    //showSnackBar("no connection");


                } else {
                    ParseUser.requestPasswordResetInBackground(email.getText().toString(), new RequestPasswordResetCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // An email was successfully sent with reset instructions.
                                dismissProgressBar();
                                Context context=ResetPassActivity.this;
                                 new AlertDialog.Builder(context)
                                         .setTitle("check your email")
                                        .setMessage(R.string.linkwillbesent)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                              /*  Intent intent1 = new Intent(Intent.ACTION_VIEW);
                                               // Uri data= Uri.parse("mailto:?subject="+"blahblah"+"&body="+"blah"+"&to="+"send@me.com");
                                                //intent1.setData(data);
                                                intent1.setType("message/rfc822");
                                                //Intent inte=new Intent(SignUpActivity.this,LoginActivity.class);
                                                Intent mailer = Intent.createChooser(intent1, null);
                                                try {
                                                    startActivity(mailer);
                                                }catch (ActivityNotFoundException e){
                                                    showSnackBar("somthing went wrong please check your email app");
                                                }*/
                                                Intent intent1 = new Intent(Intent.ACTION_MAIN);
                                                intent1.addCategory(Intent.CATEGORY_APP_EMAIL);
                                                startActivity(intent1);
                                                startActivity(Intent.createChooser(intent1, getString(R.string.ChoseEmailClient)));

                                                System.exit(0);
                                            }
                                        })
                                        .show();

                            } else {
                                // Something went wrong. Look at the ParseException to see what's up.
                                dismissProgressBar();
                                showSnackBar("please check your email to reset the password");
                                dismissProgressBar();
                            }


                        }

                    });
                }
            }
        });

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

