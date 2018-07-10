package com.example.q.project2_master.Activities;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.q.project2_master.R;

public class GameActivity extends AppCompatActivity {
    private View view;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView[] grids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);
        layoutManager = new GridLayoutManager(this, 7);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);


    }

    private ImageView[] createGrids() {
        String grid = "grid";
        for (int i = 1; i < 43; i++) {
            String gridName = grid + Integer.toString(i);
            ImageView imageView = findViewById(R.id.gridName);
        }
    }
}
