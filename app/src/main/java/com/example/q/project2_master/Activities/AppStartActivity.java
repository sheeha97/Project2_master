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

import com.example.q.project2_master.AsyncTasks.ServerSS;
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

    int METHOD_GET = 0;
    int METHOD_POST = 1;


    TextView serverTestTextView;
    public static String userName;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appstart);
        TextView textView = findViewById(R.id.user_name_textview);
        Intent mainActivityIntent = getIntent();
        userName= mainActivityIntent.getStringExtra("user_name");
        textView.setText(userName);

        serverTestTextView= findViewById(R.id.server_test_textview);
        registerBtn = findViewById(R.id.register_button);
        final String urlTail = "/register";

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //doRegister(urlTail, userName);
                doGetTest("/get_test");
            }
        });

    }

    public void doRegister(String urlTail, String userName) {
        RegisterServerSS register = new RegisterServerSS(urlTail, userName, this, METHOD_POST);
        register.execute(getString(R.string.SERVER_URL) + urlTail);//AsyncTask 시작시킴
    }

    public void doGetTest(String urlTail) {
        GetServerSS getss = new GetServerSS(urlTail, "", this, METHOD_GET);
        getss.execute(getString(R.string.SERVER_URL) + urlTail);
    }

    public void setServerTestTextView(String text){
        serverTestTextView.setText(text);
    }

    class RegisterServerSS extends ServerSS {
        public RegisterServerSS(String urlTail, String stringData, Activity context, int method) {
            super(urlTail, stringData, context, method);
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

    class GetServerSS extends ServerSS {
        public GetServerSS(String urlTail, String stringData, Activity context, int method) {
            super(urlTail, stringData, context, method);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            setServerTestTextView(result);
        }
    }




}

