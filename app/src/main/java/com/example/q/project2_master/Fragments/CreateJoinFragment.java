package com.example.q.project2_master.Fragments;

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
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

public class CreateJoinFragment extends Fragment {


    private View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                        String targetName = editText.getText().toString();
                        String urlTail= "/join_room";

                        //server code
                        JoinRoomServerSS dcSS = new JoinRoomServerSS(
                                urlTail, JsonUtils.toJSonRedName(targetName), getContext(), ServerSS.METHOD_POST, targetName);
                        dcSS.execute(getString(R.string.SERVER_URL) + urlTail);
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

    class JoinRoomServerSS extends ServerSS {
        String targetName;
        public JoinRoomServerSS(String urlTail, String stringData, Context context, int method, String targetName) {
            super(urlTail, stringData, context, method);
            this.targetName = targetName;
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);

            String toastText;
            if (result == null) {
                toastText = "network error!";
            } else {
                toastText= "Sorry, json error";
                try {
                    //Receive Json file from the server
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.getBoolean("room_exist")) {
                        toastText = "No such room exists...";
                    } else {
                        //oh boi lets go
                        toastText = "Room exists! Now connecting~!";
                        Intent intent = new Intent(context, GameActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    Log.d("tink-exception", "json object exception");
                    e.printStackTrace();
                }
            }
            Toast.makeText(getContext(), toastText, Toast.LENGTH_SHORT);
        }
    }

}
