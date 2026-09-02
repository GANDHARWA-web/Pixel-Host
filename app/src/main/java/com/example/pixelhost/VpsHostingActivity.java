package com.example.pixelhost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class VpsHostingActivity extends BaseActivity  {

    private String location;          // from previous step (e.g., "India" / "Singapore")
    private String selectedTier;      // "Budget VPS" or "Performance Premium VPS"

    private CardView cardBudget, cardPremium;
    private View containerBudget, containerPremium;
    private View badgeBudget, badgePremium;
    private TextView btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vps_hosting);

        // get location from previous screen
        location = getIntent().getStringExtra("location");

        ((TextView) findViewById(R.id.title)).setText("VPS — Step 2");
        ((TextView) findViewById(R.id.sub)).setText("Choose CPU Type");

        cardBudget       = findViewById(R.id.cardBudget);
        cardPremium      = findViewById(R.id.cardPremium);

        containerBudget  = findViewById(R.id.containerBudget);
        containerPremium = findViewById(R.id.containerPremium);

        badgeBudget      = findViewById(R.id.badgeBudget);
        badgePremium     = findViewById(R.id.badgePremium);

        btnNext          = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setAlpha(0.4f);

        cardBudget.setOnClickListener(v -> {
            selectedTier = "Budget VPS";
            setSelected("budget");
        });

        cardPremium.setOnClickListener(v -> {
            selectedTier = "Performance Premium VPS";
            setSelected("premium");
        });

        btnNext.setOnClickListener(v -> {
            if (selectedTier == null) return;
            Intent i = new Intent(this, VpsPlansActivity.class);
            i.putExtra("location", location);
            i.putExtra("cpuTier", selectedTier);
            startActivity(i);
        });
    }

    private void setSelected(String which) {
        boolean budget  = "budget".equals(which);
        boolean premium = "premium".equals(which);

        containerBudget.setSelected(budget);
        containerPremium.setSelected(premium);

        badgeBudget.setVisibility(budget ? View.VISIBLE : View.GONE);
        badgePremium.setVisibility(premium ? View.VISIBLE : View.GONE);

        cardBudget.setCardElevation(budget ? 12f : 2f);
        cardPremium.setCardElevation(premium ? 12f : 2f);

        btnNext.setEnabled(budget || premium);
        btnNext.setAlpha((budget || premium) ? 1f : 0.4f);
    }
}
