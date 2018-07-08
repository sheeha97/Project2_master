package com.example.q.project2_master.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.q.project2_master.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.q.project2_master.AsyncTasks.SendPost;

//TODO: 여기서 back버튼 누르면 빈화면 뜸

public class AppStartActivity extends AppCompatActivity {
    TextView serverTestTextView;
    String userName;
    Button sendpostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appstart);
        TextView textView = findViewById(R.id.user_name_textview);
        Intent mainActivityIntent = getIntent();
        userName= mainActivityIntent.getStringExtra("user_name");
        textView.setText(userName);

        serverTestTextView= findViewById(R.id.server_test_textview);
        sendpostBtn = findViewById(R.id.send_post_button);
        String urlTail = "/test1_post";
        final TestSendPost sendPost = new TestSendPost(urlTail, userName, this);
        sendpostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlTail = "/test1_post";
                sendPost.execute(getString(R.string.SERVER_URL) + urlTail);//AsyncTask 시작시킴
            }
        });

    }

    public void setServerTestTextView(String text){
        serverTestTextView.setText(text);
    }

    class TestSendPost extends SendPost{

        public TestSendPost(String urlTail, String stringData, Activity context) {
            super(urlTail, stringData, context);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONArray jsonArray = new JSONArray(result);
                String[] names = new String[jsonArray.length()];
                for (int i=0; i<jsonArray.length(); i++) {
                    names[i] = jsonArray.getJSONObject(i).getString("name");
                }
                setServerTestTextView(names[0] + "  and  "+names[1]);
            }
            catch (JSONException e) {
                Log.d("tink-exception", "json exception");
                e.printStackTrace();
            }
        }
    }

}

