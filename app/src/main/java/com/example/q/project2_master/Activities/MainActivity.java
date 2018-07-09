package com.example.q.project2_master.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.q.project2_master.AsyncTasks.ServerSS;
import com.example.q.project2_master.R;
import com.example.q.project2_master.AsyncTasks.SendPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    String[] PERMISSIONS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.VIBRATE,
            Manifest.permission.INTERNET  };

    private int requestCode= 0;
    private EditText nameInput;
    private Button confirmBtn;
    private TextView duplicateTextView;
    private String preferenceName = "user_name_saver";
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS,
                    requestCode); }
        else {
            doOncreate();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doOncreate();

                } else {
                    setContentView(R.layout.permission_denied);
                }
                return;
            }
        }
    }

    public void doOncreate() {
        SharedPreferences sf = getSharedPreferences(preferenceName, requestCode);
        String savedUserName = sf.getString("user_name_preference", "");
        //if (savedUserName.equals("")) {
        if (true) {
            Log.d("tink_main", "no saved username");
            setContentView(R.layout.activity_main);
            nameInput = (EditText) findViewById(R.id.name_input);
            confirmBtn = (Button) findViewById(R.id.confirm_button);
            duplicateTextView = findViewById(R.id.duplicate_textview);
            confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userName = nameInput.getText().toString();
                    if (userName.equals("")) {
                        return;
                    }
                    SharedPreferences sf = getSharedPreferences(preferenceName, requestCode);
                    SharedPreferences.Editor editor = sf.edit();//저장하려면 editor가 필요
                    editor.putString("user_name_preference", userName); // 입력
                    editor.commit(); // 파일에 최종 반영함

                    doRegister("/register", userName);
                    //intentToAppstartActivity(userName);
                }
            });

        } else {
            Log.d("tink_main", savedUserName);
            userName = savedUserName;
            intentToAppstartActivity(userName);
        }
    }

    public void intentToAppstartActivity(String passedUserName) {
        Intent appStartIntent = new Intent(MainActivity.this, AppStartActivity.class);
        appStartIntent.putExtra("user_name", passedUserName);
        startActivity(appStartIntent);
    }

    public static boolean hasPermissions(Context context, String... permissions){
        if (context != null && permissions != null) {
            for (String permission : permissions){
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void doRegister(String urlTail, String userName) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("name", userName);
            RegisterServerSS register = new RegisterServerSS(urlTail, jsonObject.toString(), this, ServerSS.METHOD_POST, userName);
            register.execute(getString(R.string.SERVER_URL) + urlTail);//AsyncTask 시작시킴
        } catch (JSONException e) {
            Log.d("tink-exception", "json request exception");
            e.printStackTrace();
        }

    }

    class RegisterServerSS extends ServerSS {
        String userName;
        public RegisterServerSS(String urlTail, String stringData, Activity context, int method, String userName) {
            super(urlTail, stringData, context, method);
            this.userName = userName;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.d("registered", result);
            //if (result.equals(""))
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getBoolean("valid")) {
                        intentToAppstartActivity(this.userName);
                    } else {
                        duplicateTextView.setText("Duplicate name! Please type another name.");
                    }
                } catch (JSONException e) {
                    Log.d("tink-exception", "json response exception");
                    e.printStackTrace();
                }
            }
            else {
                duplicateTextView.setText("Network Error!");
            }

        }
    }

}
