package com.example.q.project2_master.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.project2_master.Activities.GameActivity;
import com.example.q.project2_master.Activities.MakeRoomActivity;
import com.example.q.project2_master.AsyncTasks.ServerSS;
import com.example.q.project2_master.GlobalObject;
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;

public class CreateJoinFragment extends Fragment { //TODO: disconnect socket


    private View v;
    Socket mSocket;
    GlobalObject go;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        go = ((GlobalObject) getActivity().getApplicationContext());
        v = inflater.inflate(R.layout.fragment_create_join, container, false);


        Button joinButton = v.findViewById(R.id.join);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        return v;
    }

    public void openDialog() {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setView(promptView);

        final EditText editText = promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final String targetName = editText.getText().toString();
                        go = ((GlobalObject) getActivity().getApplicationContext());
                        go.connectSocket();
                        mSocket = go.getSocket();
                        Emitter.Listener listener = new Emitter.Listener() {

                            public void call(Object... args) {
                                Log.d("tink", "responded");
                                final JSONObject obj = (JSONObject)args[0];
                                try {
                                    final boolean room_exist = obj.getBoolean("room_exist");
                                    final boolean room_available = obj.getBoolean("room_available");
                                    Log.d("tink", String.valueOf(room_exist) + String.valueOf(room_available));

                                    Activity activity = getActivity();
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String toastText = "server error";
                                            if (!room_exist) {
                                                toastText = "no such player exist!";
                                            } else if (!room_available) {
                                                toastText = "player is already on play!";
                                            } else {
                                                toastText = "play with " + targetName;
                                                Log.d("tink", "connect success");
                                                /*Intent intent = new Intent(getActivity(), GameActivity.class);
                                                intent.putExtra("color", 2);
                                                startActivity(intent);  //game start with color data 2 (yellow) */ // TODO: 주석처리 다시 해제!
                                            }
                                            Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        mSocket.on("yellow_join_response", listener);
                        mSocket.emit("join_request", targetName);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            //when user declined your request :(
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

    }
}
