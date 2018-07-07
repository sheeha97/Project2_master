package com.example.q.project2_master;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AppStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appstart);
        TextView textView = findViewById(R.id.user_name_textview);
        Intent mainActivityIntent = getIntent();
        String userName= mainActivityIntent.getStringExtra("user_name");
        textView.setText(userName);
    }
}
