package com.example.q.project2_master;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class GlobalObject extends Application {

    private Socket mSocket;
    String SERVER_URL = "http://52.231.65.108:8080";

    public Socket getSocket() {
        return mSocket;
    }

    public void connectSocket() {
        try {
            mSocket = IO.socket(SERVER_URL);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
