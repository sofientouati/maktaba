package com.sofientouati.maktaba;


import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;
import com.sofientouati.ISSATsoLibrary.R;

public class SplashScreen extends Activity implements Animation.AnimationListener {
    //splash screen timer
    private static int SPLASH_TIME_OUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // load the animation
        final Animation blink =AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
        final Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        final ImageView i= (ImageView) findViewById(R.id.imgLogo);


        // load the animation

        //RelativeLayout r = (RelativeLayout) findViewById(R.id.rel);
       // onAnimationEnd(blink);
        //r.setVisibility(View.INVISIBLE);

       i.startAnimation(blink);


  Animation.AnimationListener animationListener= new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                i.startAnimation(slide);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };


       blink.setAnimationListener(animationListener);


        
        new Handler().postDelayed(new Runnable() {
            public static final String TAG = "logged";
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                // Determine whether the current user is an anonymous user
                Intent i;
                if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
                    Intent intent = new Intent(SplashScreen.this,
                            LoginActivity.class);
                   // startActivity(intent);
                    SplashScreen.this.startActivity(intent);
                    SplashScreen.this.finish();
                } else {
                    if (ParseUser.getCurrentUser() != null) {
                        i = new Intent(SplashScreen.this, BrowseActivity.class);


                    } else {
                        i = new Intent(SplashScreen.this, LoginActivity.class);

                    }
                    SplashScreen.this.startActivity(i);

                    // close this activity
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_TIME_OUT);
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
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
}



