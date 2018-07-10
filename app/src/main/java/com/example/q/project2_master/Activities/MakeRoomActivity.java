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
import com.example.q.project2_master.GlobalObject;
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;
import com.github.nkzawa.socketio.client.IO;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;

import static com.example.q.project2_master.Utils.JsonUtils.toJSonRedName;

public class MakeRoomActivity extends AppCompatActivity {
    private Button startBtn;
    private Context mContext = this;
    String SERVER_URL = getString(R.string.SERVER_URL);
    com.github.nkzawa.socketio.client.Socket mSocket;
    GlobalObject go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeroom);
        startBtn = findViewById(R.id.start_button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go.connectSocket();
                mSocket = go.getSocket();
                String userName = MainActivity.userName;



            }
        });
    }

}
