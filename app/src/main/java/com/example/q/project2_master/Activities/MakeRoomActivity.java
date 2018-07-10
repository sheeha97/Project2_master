package com.example.q.project2_master.Activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.q.project2_master.AsyncTasks.ServerSS;
import com.example.q.project2_master.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MakeRoomActivity extends AppCompatActivity {
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeroom);
        startBtn = findViewById(R.id.start_button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlTail = "/make_room";
                //MakeRoomServerSS mrSS= new MakeRoomServerSS(urlTail, )
            }
        });
    }

    class MakeRoomServerSS extends ServerSS {
        public MakeRoomServerSS(String urlTail, String stringData, Context context, int method) {
            super(urlTail, stringData, context, method);
        }

        @Override
        protected void onPostExecute(String result) {
            String toastText;
            if (result == null) {
                toastText = "network error!";
            } else {
                toastText = "json error!";
                try {
                    Log.d("tink", "make_room response");
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getBoolean("success")) {
                        toastText = "sorry, server error";
                    } else {
                        Log.d("tink", "make_room successful");
                        toastText = "room is created!";
                        //open socket
                        //set textView "loading"
                    }
                } catch (JSONException e) {
                    Log.d("tink-exception", "json object exception");
                    e.printStackTrace();
                }
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
