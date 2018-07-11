package com.example.q.project2_master.Activities;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.q.project2_master.GlobalObject;
import com.example.q.project2_master.R;
import com.example.q.project2_master.Utils.JsonUtils;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GameActivity extends AppCompatActivity {
    private View view;
    private ArrayList<CircleImageView> grids = new ArrayList<CircleImageView>();
    Socket mSocket;
    GlobalObject go;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        go = ((GlobalObject) getApplicationContext());
        go.connectSocket();
        mSocket = go.getSocket();
        createGrids();

        Intent intent = getIntent();
        final int color = (int)intent.getSerializableExtra("color");
        if (color == 2) {
            for (int i = 0; i < 6; i++) {
                grids.get(36 + i).setClickable(false);
            }
        }

        Emitter.Listener listener = new Emitter.Listener() {

            public void call(Object... args) {
                Log.d("tink", "responded");
                final JSONObject obj = (JSONObject)args[0];
                try {
                    final int done = obj.getInt("done");
                    final boolean valid = obj.getBoolean("valid");
                    final int position = obj.getInt("position");

                    runOnUiThread(new Runnable() {
                        private int doColor;
                        @Override
                        public void run() {
                            if (color == 1 && !grids.get(36).isClickable()) {
                                doColor = 2;
                                colorOther(doColor, done, valid, position);
                                for (int i = 0; i < 6; i++) {
                                    grids.get(36 + i).setClickable(true);
                                }

                            } else if (color == 2 && !grids.get(36).isClickable()){
                                doColor = 1;
                                colorOther(doColor, done, valid, position);
                                for (int i = 0; i < 6; i++) {
                                    grids.get(36 + i).setClickable(true);
                                }
                            } else if(color == 1 && grids.get(36).isClickable()) {
                                move(color, done, valid, position);
                            } else {
                                move(color, done, valid, position);
                            }

                        }
                    });
                } catch (JSONException e) {
                        e.printStackTrace();
                }
            }
        };
        mSocket.on("show_result", listener);


        //index0
        grids.get(36).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeMove(0);
            }
        });

        //index1
        grids.get(37).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeMove(1);

            }
        });

        grids.get(38).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeMove(2);

            }
        });

        grids.get(39).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeMove(3);

            }
        });

        grids.get(40).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeMove(4);

            }
        });

        grids.get(41).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeMove(5);
            }
        });


    }

    private void createGrids() {
        CircleImageView imageView1 = findViewById(R.id.grid1);
        CircleImageView imageView2 = findViewById(R.id.grid2);
        CircleImageView imageView3 = findViewById(R.id.grid3);
        CircleImageView imageView4 = findViewById(R.id.grid4);
        CircleImageView imageView5 = findViewById(R.id.grid5);
        CircleImageView imageView6 = findViewById(R.id.grid6);
        CircleImageView imageView7 = findViewById(R.id.grid7);
        CircleImageView imageView8 = findViewById(R.id.grid8);
        CircleImageView imageView9 = findViewById(R.id.grid9);
        CircleImageView imageView10 = findViewById(R.id.grid10);
        CircleImageView imageView11 = findViewById(R.id.grid11);
        CircleImageView imageView12 = findViewById(R.id.grid12);
        CircleImageView imageView13 = findViewById(R.id.grid13);
        CircleImageView imageView14 = findViewById(R.id.grid14);
        CircleImageView imageView15 = findViewById(R.id.grid15);
        CircleImageView imageView16 = findViewById(R.id.grid16);
        CircleImageView imageView17 = findViewById(R.id.grid17);
        CircleImageView imageView18 = findViewById(R.id.grid18);
        CircleImageView imageView19 = findViewById(R.id.grid19);
        CircleImageView imageView20 = findViewById(R.id.grid20);
        CircleImageView imageView21 = findViewById(R.id.grid21);
        CircleImageView imageView22 = findViewById(R.id.grid22);
        CircleImageView imageView23 = findViewById(R.id.grid23);
        CircleImageView imageView24 = findViewById(R.id.grid24);
        CircleImageView imageView25 = findViewById(R.id.grid25);
        CircleImageView imageView26 = findViewById(R.id.grid26);
        CircleImageView imageView27 = findViewById(R.id.grid27);
        CircleImageView imageView28 = findViewById(R.id.grid28);
        CircleImageView imageView29 = findViewById(R.id.grid29);
        CircleImageView imageView30 = findViewById(R.id.grid30);
        CircleImageView imageView31 = findViewById(R.id.grid31);
        CircleImageView imageView32 = findViewById(R.id.grid32);
        CircleImageView imageView33 = findViewById(R.id.grid33);
        CircleImageView imageView34 = findViewById(R.id.grid34);
        CircleImageView imageView35 = findViewById(R.id.grid35);
        CircleImageView imageView36 = findViewById(R.id.grid36);
        CircleImageView imageView37 = findViewById(R.id.grid37);
        CircleImageView imageView38 = findViewById(R.id.grid38);
        CircleImageView imageView39 = findViewById(R.id.grid39);
        CircleImageView imageView40 = findViewById(R.id.grid40);
        CircleImageView imageView41 = findViewById(R.id.grid41);
        CircleImageView imageView42 = findViewById(R.id.grid42);
        grids.add(imageView1);
        grids.add(imageView2);
        grids.add(imageView3);
        grids.add(imageView4);
        grids.add(imageView5);
        grids.add(imageView6);
        grids.add(imageView7);
        grids.add(imageView8);
        grids.add(imageView9);
        grids.add(imageView10);
        grids.add(imageView11);
        grids.add(imageView12);
        grids.add(imageView13);
        grids.add(imageView14);
        grids.add(imageView15);
        grids.add(imageView16);
        grids.add(imageView17);
        grids.add(imageView18);
        grids.add(imageView19);
        grids.add(imageView20);
        grids.add(imageView21);
        grids.add(imageView22);
        grids.add(imageView23);
        grids.add(imageView24);
        grids.add(imageView25);
        grids.add(imageView26);
        grids.add(imageView27);
        grids.add(imageView28);
        grids.add(imageView29);
        grids.add(imageView30);
        grids.add(imageView31);
        grids.add(imageView32);
        grids.add(imageView33);
        grids.add(imageView34);
        grids.add(imageView35);
        grids.add(imageView36);
        grids.add(imageView37);
        grids.add(imageView38);
        grids.add(imageView39);
        grids.add(imageView40);
        grids.add(imageView41);
        grids.add(imageView42);
    }

    //makes move
    private void makeMove(int index) {
        Intent intent = getIntent();
        final int color = (int)intent.getSerializableExtra("color");
        final String targetName = (String)intent.getSerializableExtra("name");
        String jSonmove;

        if (color == 1) {
            jSonmove = JsonUtils.toJsonMove(MainActivity.userName, index);
            mSocket.emit("red_play_request", jSonmove);
        } else {
            jSonmove = JsonUtils.toJsonMove(targetName, index);
            mSocket.emit("yellow_play_request", jSonmove);
        }
    }

    private void move(int color, int done, boolean valid, int position) {
        if (done == 0 && valid) {
            if (color == 1) {
                grids.get(position - 1).setImageResource(R.drawable.red);
            } else {
                grids.get(position - 1).setImageResource(R.drawable.yellow);
            }
            //set text not clickable
            for (int i=0; i< 6; i++) {
                grids.get(36 +i ).setClickable(false);
            }
        } else if (done == 0 && !valid) {
            Toast.makeText(this, "INVALID MOVE, please choose a different index", Toast.LENGTH_SHORT).show();

        } else if (done == 1 || done == 2) {
            win(done);
        }
    }

    private void colorOther(int color, int done, boolean valid, int position) {
        if (done == 0 && valid) {
            if (color == 1) {
                grids.get(position - 1).setImageResource(R.drawable.red);
            } else {
                grids.get(position - 1).setImageResource(R.drawable.yellow);
            }
        } else if (done == 0 && !valid) {
            Toast.makeText(this, "INVALID MOVE, please choose a different index", Toast.LENGTH_SHORT).show();

        } else if (done == 1 || done == 2) {
            if (done == 1) {
                win(2);
            } else {
                win(1);
            }
        }
    }



    private void win(int winner) {
        String winnerName;
        if (winner == 1) {
            winnerName = "Red";
        } else {
            winnerName = "Yellow";
        }
        AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
        alertDialog.setTitle("Winner!");
        alertDialog.setMessage("Player " + winnerName+" is the Winner!");
        alertDialog.show();
    }

}
