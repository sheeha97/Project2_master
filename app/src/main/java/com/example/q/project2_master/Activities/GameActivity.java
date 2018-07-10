package com.example.q.project2_master.Activities;
import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.q.project2_master.R;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private View view;
    private ArrayList<ImageView> grids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        createGrids();
        grids.get(37).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        grids.get(38).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        grids.get(39).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        grids.get(40).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        grids.get(41).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        grids.get(42).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void createGrids() {
        ImageView imageView1 = findViewById(R.id.grid1);
        ImageView imageView2 = findViewById(R.id.grid2);
        ImageView imageView3 = findViewById(R.id.grid3);
        ImageView imageView4 = findViewById(R.id.grid4);
        ImageView imageView5 = findViewById(R.id.grid5);
        ImageView imageView6 = findViewById(R.id.grid6);
        ImageView imageView7 = findViewById(R.id.grid7);
        ImageView imageView8 = findViewById(R.id.grid8);
        ImageView imageView9 = findViewById(R.id.grid9);
        ImageView imageView10 = findViewById(R.id.grid10);
        ImageView imageView11 = findViewById(R.id.grid11);
        ImageView imageView12 = findViewById(R.id.grid12);
        ImageView imageView13 = findViewById(R.id.grid13);
        ImageView imageView14 = findViewById(R.id.grid14);
        ImageView imageView15 = findViewById(R.id.grid15);
        ImageView imageView16 = findViewById(R.id.grid16);
        ImageView imageView17 = findViewById(R.id.grid17);
        ImageView imageView18 = findViewById(R.id.grid18);
        ImageView imageView19 = findViewById(R.id.grid19);
        ImageView imageView20 = findViewById(R.id.grid20);
        ImageView imageView21 = findViewById(R.id.grid21);
        ImageView imageView22 = findViewById(R.id.grid22);
        ImageView imageView23 = findViewById(R.id.grid23);
        ImageView imageView24 = findViewById(R.id.grid24);
        ImageView imageView25 = findViewById(R.id.grid25);
        ImageView imageView26 = findViewById(R.id.grid26);
        ImageView imageView27 = findViewById(R.id.grid27);
        ImageView imageView28 = findViewById(R.id.grid28);
        ImageView imageView29 = findViewById(R.id.grid29);
        ImageView imageView30 = findViewById(R.id.grid30);
        ImageView imageView31 = findViewById(R.id.grid31);
        ImageView imageView32 = findViewById(R.id.grid32);
        ImageView imageView33 = findViewById(R.id.grid33);
        ImageView imageView34 = findViewById(R.id.grid34);
        ImageView imageView35 = findViewById(R.id.grid35);
        ImageView imageView36 = findViewById(R.id.grid36);
        ImageView imageView37 = findViewById(R.id.grid37);
        ImageView imageView38 = findViewById(R.id.grid38);
        ImageView imageView39 = findViewById(R.id.grid39);
        ImageView imageView40 = findViewById(R.id.grid40);
        ImageView imageView41 = findViewById(R.id.grid41);
        ImageView imageView42 = findViewById(R.id.grid42);
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
}
