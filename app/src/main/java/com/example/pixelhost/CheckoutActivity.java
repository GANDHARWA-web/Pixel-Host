package com.example.pixelhost;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CheckoutActivity extends BaseActivity  {

    private String game, location, cpu, planTitle, planSpecs, planPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        game      = getIntent().getStringExtra("game");
        location  = getIntent().getStringExtra("location");
        cpu       = getIntent().getStringExtra("cpu");
        planTitle = getIntent().getStringExtra("planTitle");
        planSpecs = getIntent().getStringExtra("planSpecs");
        planPrice = getIntent().getStringExtra("planPrice");

        ((TextView)findViewById(R.id.tvSummaryTitle)).setText("Checkout Summary");
        ((TextView)findViewById(R.id.tvLine1)).setText("Game/Type: " + game);
        ((TextView)findViewById(R.id.tvLine2)).setText("Location: " + location);
        ((TextView)findViewById(R.id.tvLine3)).setText("CPU: " + cpu);
        ((TextView)findViewById(R.id.tvLine4)).setText("Plan: " + planTitle);
        ((TextView)findViewById(R.id.tvLine5)).setText(planSpecs);
        ((TextView)findViewById(R.id.tvPrice)).setText(planPrice);

        findViewById(R.id.btnProceedPayment).setOnClickListener(v -> {
            Intent i = new Intent(this, PaymentActivity.class);
            i.putExtra("game", game);
            i.putExtra("location", location);
            i.putExtra("cpu", cpu);
            i.putExtra("planTitle", planTitle);
            i.putExtra("planPrice", planPrice);
            startActivity(i);
        });
    }
}
