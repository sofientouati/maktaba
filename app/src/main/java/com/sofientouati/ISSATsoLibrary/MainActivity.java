package com.sofientouati.ISSATsoLibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {

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

        final EditText email= (EditText) findViewById(R.id.input_emaail);
        final EditText pass= (EditText) findViewById(R.id.input_password);
        Button btnUp = (Button) findViewById(R.id.btnSingUp);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(MainActivity.this,SignUpActivity.class);
                if (email!=null)signup.putExtra("email",email.getText().toString());
                if(pass!=null)signup.putExtra("pass",pass.getText().toString());
                startActivity(signup);
            }
        });



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
}
