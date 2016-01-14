package com.sofientouati.maktaba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;
import com.sofientouati.ISSATsoLibrary.R;

public class BrowseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Button b= (Button) findViewById(R.id.btnlogout);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent=new Intent(BrowseActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
