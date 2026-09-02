package com.example.pixelhost;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;   // use AppCompat for consistency

public class MainActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Open Step 1 (Choose Game)
        findViewById(R.id.btnGameHosting).setOnClickListener(v ->
                startActivity(new Intent(this, GameHostingActivity.class))
        );

        // Keep VPS as a toast for now
        findViewById(R.id.btnVpsHosting).setOnClickListener(v ->
                startActivity(new android.content.Intent(this, VpsHostingActivity.class))
        );

    }
}
