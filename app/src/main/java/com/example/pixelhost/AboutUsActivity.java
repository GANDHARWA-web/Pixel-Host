package com.example.pixelhost;

import android.os.Bundle;
import android.widget.TextView;

public class AboutUsActivity extends BaseActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        if (getSupportActionBar() != null) getSupportActionBar().setTitle("About Us");

        TextView tv = findViewById(R.id.tvAbout);
        tv.setText("Team 12\nDeveloped by \nManisha, \nRohan, \nDatta");
    }
}
