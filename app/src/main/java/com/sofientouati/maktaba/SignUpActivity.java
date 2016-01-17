package com.sofientouati.maktaba;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.sofientouati.ISSATsoLibrary.R;

import java.lang.reflect.Array;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "date";
    private TextInputLayout inputLayoutEmail,inputLayoutPass,inputLayoutCin,inputLayoutCen,inputLayoutName,inputLayoutSurname,inputLayoutDate;
    private EditText inputEmail,inputPass,inputCin,inputCen,inputName,inputSurname,inputDate;
    private String email,pass,cin,cen,name,surname,date;
    private ImageView backBtn;
    private Intent intent;
    private Spinner spinner;
    private ProgressDialog progressDialog;
    private Date dated;
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


        spinner= (Spinner) findViewById(R.id.division);
        inputLayoutEmail= (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPass= (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutCen= (TextInputLayout) findViewById(R.id.input_layout_cen);
        inputLayoutCin= (TextInputLayout) findViewById(R.id.input_layout_cin);
        inputLayoutName= (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutSurname= (TextInputLayout) findViewById(R.id.input_layout_surname);
        //inputLayoutDate= (TextInputLayout) findViewById(R.id.input_layout_date);
        inputEmail = (EditText) findViewById(R.id.input_emaail);
        inputPass = (EditText) findViewById(R.id.input_password);
        inputCin = (EditText) findViewById(R.id.input_cin);
        inputCen = (EditText) findViewById(R.id.input_cen);
        inputName = (EditText) findViewById(R.id.input_name);
        inputSurname = (EditText) findViewById(R.id.input_surname);
       // inputDate = (EditText) findViewById(R.id.input_date);


/*         inputDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
             @Override
             public void onFocusChange(View v, boolean hasFocus) {
                 if(hasFocus){
                     DateDialog dialog=new DateDialog(v);
                     FragmentTransaction ft =getFragmentManager().beginTransaction();
                     dialog.show(ft, "DatePicker");

                 }
             }
         });
*/




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
                showProgressBar("loading");



                if(!submitForm()){
                    Log.d("date", "onClick: "+date);
                    dismissProgressBar();
            }


              else  if(!isNetworkAvailable()){
                    dismissProgressBar();
                    showSnackBar("no connection!");}
                else{



                email= inputEmail.getText().toString();
                pass= inputPass.getText().toString();
                cin= inputCin.getText().toString();
                cen= inputCen.getText().toString();
                name= inputName.getText().toString();
                surname= inputSurname.getText().toString();
//                date= inputDate.getText().toString();
                   // Log.d("dated", "onClick: "+date);
                   // Log.d("dated", "onClick: " + inputDate.getText().toString());

                ParseQuery<ParseObject> query=ParseQuery.getQuery("Student");

                query.whereEqualTo("cin",inputCin.getText().toString());
                query.whereEqualTo("cen", inputCen.getText().toString());
               // Log.d("Student", "onClick: " + inputCen.getText().toString());
                query.whereEqualTo("name", inputName.getText().toString());
                query.whereEqualTo("surname", inputSurname.getText().toString());
                //query.whereEqualTo("birthdate", convDate());
                query.whereEqualTo("division", spinner.getSelectedItem().toString());
                //Log.d("Student", "onClick: " + spinner.getSelectedItem().toString());
              //  query.whereEqualTo("existing", false);
                query.getFirstInBackground(new GetCallback<ParseObject>() {

                    @Override
                    public void done(final ParseObject object, com.parse.ParseException e) {
                        if(e==null){

                           if(object.isDataAvailable()){
                             String s=   object.getDate("birthdate").toString();
                               //Date date = object.getDate("birthday");
                               Log.d("daters", "done: " + s);
                               object.saveInBackground();
                                ParseUser user=new ParseUser();
                                user.setUsername(email);
                                user.setPassword(pass);
                                user.setEmail(email);
                                user.put("name", name);
                                user.put("surname", surname);
                                //user.put("birthdate", convDate());
                                user.put("division", spinner.getSelectedItem().toString());
                                user.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(com.parse.ParseException e) {
                                        if(e==null){
                                            object.put("existing",true);
                                            Context context=SignUpActivity.this;
                                            new AlertDialog.Builder(context)
                                                    .setTitle(R.string.chk_mail)
                                                    .setMessage(R.string.linkwillbesent)
                                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent intent1 = new Intent(Intent.ACTION_VIEW);
                                                            Uri data= Uri.parse("mailto:?" +
                                                                    "subject="+"blahblah"+"&body="+"blah"+"&to="+"send@me.com");
                                                            intent1.setData(data);
                                                            Intent inte=new Intent(SignUpActivity.this,LoginActivity.class);
                                                            startActivity(intent1);
                                                            System.exit(0);
                                                        }
                                                    })
                                                    .show();
                                            dismissProgressBar();}
                                        else {
                                            Log.d("user", "done: !");
                                            showSnackBar( e.getMessage());
                                            dismissProgressBar();
                                        }
                                    }
                               });
                           }
                       }
                 else{
                            dismissProgressBar();
                            if(object.getBoolean("existing")==true){
                                showSnackBar("already exited account with this id");
                            }else{


                                showSnackBar("Check your info");

                            }
                        }
                    }
                });
            }}
       });
    }







    //convert edittext to date
   /* public Date convDate(){
       // inputDate= (EditText) findViewById(R.id.input_date);
        SimpleDateFormat formatter=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
        try{
            dated=formatter.parse(inputDate.getText().toString());

        }catch (ParseException e){
            e.getMessage();
            Log.d("daters", "convDate: "+e.getMessage());
            e.printStackTrace();
        }
        return dated;
    }*/


    //snack
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
//submit form
    private boolean submitForm() {
        if (!validateEmail()) {

            return false;
        }
        if (!validatePass()){

            return false;
    }
        if (!validateCin()){

            return false;
    }
        if(!validateCen()){

            return false;
        }
        if(!validateName()){

            return false;
        }
        if(!validateSurname()){
            return false;
        }
       /* if(!validateDate()){
           return false;
        }*/
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
                return false;
            }
            else {
                selectedTextView.setError(null);
                return true;
            }
        }
return true;
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
//        String date = inputDate.getText().toString().trim();
        Log.d("date", "validateDate: "+date);

        if (date.isEmpty()/* || isValidDate(date)*/) {
            inputLayoutDate.setError(getString(R.string.err_msg_date));
            requestFocus(inputDate);
            return false;
        } else {
            inputLayoutDate.setErrorEnabled(false);
        }

        return true;

    }
    private static boolean isValidDate(String date) {
        return !TextUtils.isEmpty(date);
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
              /*  case R.id.input_layout_date:
                    validateDate();
                    break;*/
            }
        }
    }
}



