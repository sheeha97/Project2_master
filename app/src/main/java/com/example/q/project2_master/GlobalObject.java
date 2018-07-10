package com.example.q.project2_master;

import android.app.Application;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class GlobalObject extends Application {
    //public Socket mSocket = IO.socket("http://52.231.65.108:8080");

    private Socket mSocket;

    public Socket getSocket() {
        return mSocket;
    }

    public void connectSocket() {
        try {
            mSocket = IO.socket(getString(R.string.SERVER_URL));
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
