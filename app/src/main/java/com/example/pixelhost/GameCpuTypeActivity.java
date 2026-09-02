package com.example.pixelhost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class GameCpuTypeActivity extends BaseActivity  {

    private String game, location; // inputs
    private String selectedCpu;    // "Intel" or "Ryzen"

    private CardView cardIntel, cardRyzen;
    private View containerIntel, containerRyzen;
    private View badgeIntel, badgeRyzen;
    private TextView btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_cpu_type);

        game = getIntent().getStringExtra("game");
        location = getIntent().getStringExtra("location");

        ((TextView)findViewById(R.id.title)).setText("Game Hosting — Step 3");
        ((TextView)findViewById(R.id.sub)).setText("Choose CPU Type");

        cardIntel = findViewById(R.id.cardIntel);
        cardRyzen = findViewById(R.id.cardRyzen);

        containerIntel = findViewById(R.id.containerIntel);
        containerRyzen = findViewById(R.id.containerRyzen);

        badgeIntel = findViewById(R.id.badgeIntel);
        badgeRyzen = findViewById(R.id.badgeRyzen);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setAlpha(0.4f);

        cardIntel.setOnClickListener(v -> {
            selectedCpu = "Intel";
            setSelected("intel");
        });

        cardRyzen.setOnClickListener(v -> {
            selectedCpu = "Ryzen";
            setSelected("ryzen");
        });

        btnNext.setOnClickListener(v -> {
            if (selectedCpu == null) return;
            Intent i = new Intent(this, GamePlansActivity.class);
            i.putExtra("game", game);
            i.putExtra("location", location);
            i.putExtra("cpu", selectedCpu);
            startActivity(i);
        });
    }

    private void setSelected(String which) {
        boolean intel = "intel".equals(which);
        boolean ryzen = "ryzen".equals(which);

        containerIntel.setSelected(intel);
        containerRyzen.setSelected(ryzen);

        badgeIntel.setVisibility(intel ? View.VISIBLE : View.GONE);
        badgeRyzen.setVisibility(ryzen ? View.VISIBLE : View.GONE);

        cardIntel.setCardElevation(intel ? 12f : 2f);
        cardRyzen.setCardElevation(ryzen ? 12f : 2f);

        btnNext.setEnabled(intel || ryzen);
        btnNext.setAlpha((intel || ryzen) ? 1f : 0.4f);
    }
}
