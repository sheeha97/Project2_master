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
    String userName;
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
        final String urlTail = "/test1_post";

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //doRegister(urlTail, userName);
                //doGetTest("/get_test");
                doTest("/test1_post", userName);
            }
        });

    }

    public void doRegister(String urlTail, String userName) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("name", userName);
            RegisterServerSS register = new RegisterServerSS(urlTail, jsonObject.toString(), this, METHOD_POST);
            register.execute(getString(R.string.SERVER_URL) + urlTail);//AsyncTask 시작시킴
        } catch (JSONException e) {
            Log.d("tink-exception", "json exception");
            e.printStackTrace();
        }

    }

    public void doTest(String urlTail, String userName) {
        TempServerSS temp = new TempServerSS(urlTail, userName, this, METHOD_POST);
        temp.execute(getString(R.string.SERVER_URL) + urlTail);
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
            //setServerTestTextView(result);
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

    class TempServerSS extends AsyncTask<String, String, String> {

        int METHOD_GET = 0;
        int METHOD_POST = 1;

        String urlTail;
        String stringData;
        Activity context;
        int method;

        public TempServerSS(String urlTail, String stringData, Activity context, int method) {
            this.urlTail = urlTail;
            this.stringData = stringData;
            this.context= context;
            this.method = method;
        }

        @Override
        public String doInBackground(String... urls) {
            try {
                userName = stringData;
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name", userName);

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(context.getString(R.string.SERVER_URL) + urlTail);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    if (method == METHOD_POST) {
                        con.setRequestMethod("POST");
                        con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                        con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                        con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                        con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                        con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    }
                    con.connect();

                    if (method == METHOD_POST) {
                        //서버로 보내기위해서 스트림 만듬
                        OutputStream outStream = con.getOutputStream();
                        //버퍼를 생성하고 넣음
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                        writer.write(jsonObject.toString());
                        writer.flush();
                        writer.close();//버퍼를 닫아줌
                    }

                    //get data from server
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    return buffer.toString();//test1.js// res.end("Server connected to Android!");

                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }



        //doInBackground메소드가 끝나면 호출됨
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

