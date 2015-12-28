package com.sofientouati.maktaba;

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
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.sofientouati.ISSATsoLibrary.R;

public class LoginActivity extends AppCompatActivity implements Animation.AnimationListener {
    private EditText email,pass;
    private TextInputLayout inputLayoutEmail,inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load the animation
        Animation blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        RelativeLayout r = (RelativeLayout) findViewById(R.id.rel);
        ImageView r1 = (ImageView) findViewById(R.id.imgLogo);
        r.setVisibility(View.INVISIBLE);

        r1.startAnimation(blink);

        blink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RelativeLayout r = (RelativeLayout) findViewById(R.id.rel);
                r.setVisibility(View.VISIBLE);
                Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidedown);
                // r.startAnimation(a);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
       onAnimationEnd(blink);
        if (!isNetworkAvailable()) {
            Snackbar snackbar=Snackbar.make(findViewById(R.id.coordinatorLayout),"not connected to the internet!",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
         email= (EditText) findViewById(R.id.input_emaail);
        pass= (EditText) findViewById(R.id.input_password);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        final CheckBox checkBox= (CheckBox) findViewById(R.id.Chkforgot);

        Button btnin = (Button) findViewById(R.id.btnSignIn);
        Button btnUp = (Button) findViewById(R.id.btnSingUp);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(LoginActivity.this,SignUpActivity.class);
                if (email!=null)signup.putExtra("email",email.getText().toString());
                if(pass!=null)signup.putExtra("pass",pass.getText().toString());
                startActivity(signup);
            }
        });
        btnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isNetworkAvailable()) {
                    Snackbar snackbar=Snackbar.make(findViewById(R.id.coordinatorLayout),"no internet connection",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    if (checkBox.isChecked()){
                        Intent intent=new Intent();
                    }
                    submitForm();
                String mail=email.getText().toString();
                String passw=pass.getText().toString();
                ParseUser.logInInBackground(mail, passw,
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (user!=null){
                                    Intent intent=new Intent(LoginActivity.this,BrowseActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else {
                                    Snackbar snackbar=Snackbar.make(findViewById(R.id.coordinatorLayout),"Invalid email/password",Snackbar.LENGTH_LONG);
                                    snackbar.show();

                                }

                            }});}

            }        });




            }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {




    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    private void submitForm() {
        if (!validateEmail() && !validatePassword()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
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

    private boolean validatePassword() {
        if (pass.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(pass);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
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
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
