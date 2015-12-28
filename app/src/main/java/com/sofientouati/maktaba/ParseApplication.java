package com.sofientouati.maktaba;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by sofirntouati on 24/12/15.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "MxosqIgdjNI0neo2OUWM9ariLheMokK4yR7bhdRA", "don97qYjLwuB2eeUvjQfXiVNITGOJKmQ4VM0bvHb");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }
}
