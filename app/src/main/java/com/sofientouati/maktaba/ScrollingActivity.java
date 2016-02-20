package com.sofientouati.maktaba;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sofientouati.ISSATsoLibrary.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ScrollingActivity extends AppCompatActivity {

    private ProgressDialog progressDialogue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        Intent intent;
        intent=getIntent();
        String s=intent.getStringExtra("book");
        String url="https://igclibapi.herokuapp.com/api/book/By/N/"+s;
        Context context = getApplicationContext();
        new AsyncHttpTask().execute(url);
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            showProgressBar("Loading...");
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();

                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {

            }
            return result; //"Failed to fetch data!";
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            dismissProgressBar();

            if (result == 1) {BookItem f= (BookItem) feedList.get(0);
                TextView textView= (TextView) findViewById(R.id.r_title);
                textView.setText(Html.fromHtml(f.getTitre()));
                TextView textView1= (TextView) findViewById(R.id.r_auteur);
                textView1.setText(Html.fromHtml(f.getAuteur()));
                TextView textView2= (TextView) findViewById(R.id.r_code);
                textView2.setText(Html.fromHtml(f.getCode()));
                TextView textView3= (TextView) findViewById(R.id.r_category);
                textView3.setText(Html.fromHtml(f.getCategory()));
                TextView textView4= (TextView) findViewById(R.id.r_collection);

                textView4.setText(Html.fromHtml(f.getCollection()));
                if(Objects.equals(textView4.getText().toString(), "")){
                    TextView textView5= (TextView) findViewById(R.id.collection);
                    textView5.setVisibility(View.INVISIBLE);

                }
            }
            else {
                Toast.makeText(ScrollingActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.relativ_scrol);
                relativeLayout.setVisibility(View.INVISIBLE);
            }
        }
    }
    ArrayList<Object> feedList = new ArrayList<>();
    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("books");


            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                BookItem item = new BookItem();
                item.setTitre(post.optString("Titre"));
                item.setCategory(post.optString("Category"));
                item.setCode(post.optString("Code"));
                item.setAuteur(post.optString("Auteur"));
                item.setCollection(post.optString("Collection"));
                feedList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public void showProgressBar(String message){
        progressDialogue= ProgressDialog.show(this,"",message,true);
    }
    public void dismissProgressBar(){
        if(progressDialogue!=null && progressDialogue.isShowing())
        {
            progressDialogue.dismiss();
        }
    }
}
