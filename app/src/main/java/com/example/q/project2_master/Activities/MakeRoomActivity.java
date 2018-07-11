
package com.example.q.project2_master.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.project2_master.AsyncTasks.ServerSS;
import com.example.q.project2_master.GlobalObject;
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import static com.example.q.project2_master.Utils.JsonUtils.toJSonRedName;

public class MakeRoomActivity extends AppCompatActivity {  //TODO: disconnect socket
    private Button startBtn;
    private Context mContext = this;
    Socket mSocket;
    GlobalObject go;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makeroom);
        startBtn = findViewById(R.id.start_button);
        textView = findViewById(R.id.loading_textview);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //'create_response', 'create_request'
                go = ((GlobalObject) getApplicationContext());
                go.setUsercolor(1);
                go.connectSocket();
                mSocket = go.getSocket();
                String userName = MainActivity.userName;
                Emitter.Listener listener = new Emitter.Listener() {

                    public void call(Object... args) {
                        Log.d("tink", "responded");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Yellow user is not connected yet...");
                                listenYellow(mSocket);
                            }
                        });
                    }
                };
                mSocket.on("create_response", listener);
                mSocket.emit("create_request", userName);
            }
        });
    }

    public void listenYellow(Socket mSocket) {
        mSocket = go.getSocket();
        Emitter.Listener listener = new Emitter.Listener() {

            public void call(Object... args) {
                Log.d("tink", "responded");
                final JSONObject obj = (JSONObject)args[0];
                try {
                    final boolean room_exist = obj.getBoolean("room_exist");
                    final boolean room_available = obj.getBoolean("room_available");
                    Log.d("tink", String.valueOf(room_exist) + String.valueOf(room_available));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (room_exist && room_available) {
                                Toast.makeText(mContext, "start play!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, GameActivity.class);
                                intent.putExtra("color", 1);
                                startActivity(intent);
                            }
                        }
                    });
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        mSocket.on("red_join_response", listener);
    }

}
