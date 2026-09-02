package com.example.pixelhost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class GameLocationActivity extends BaseActivity  {

    private String game;           // input from previous
    private String selectedLoc;    // "Delhi" or "Mumbai"

    private CardView cardDelhi, cardMumbai;
    private View containerDelhi, containerMumbai;
    private View badgeDelhi, badgeMumbai;
    private TextView btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_location);

        game = getIntent().getStringExtra("game");

        ((TextView)findViewById(R.id.title)).setText("Game Hosting — Step 2");
        ((TextView)findViewById(R.id.sub)).setText("Choose your location");

        cardDelhi = findViewById(R.id.cardDelhi);
        cardMumbai = findViewById(R.id.cardMumbai);

        containerDelhi = findViewById(R.id.containerDelhi);
        containerMumbai = findViewById(R.id.containerMumbai);

        badgeDelhi = findViewById(R.id.badgeDelhi);
        badgeMumbai = findViewById(R.id.badgeMumbai);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setEnabled(false);
        btnNext.setAlpha(0.4f);

        cardDelhi.setOnClickListener(v -> {
            selectedLoc = "Delhi";
            setSelected("delhi");
        });

        cardMumbai.setOnClickListener(v -> {
            selectedLoc = "Mumbai";
            setSelected("mumbai");
        });

        btnNext.setOnClickListener(v -> {
            if (selectedLoc == null) return;
            Intent i = new Intent(this, GameCpuTypeActivity.class);
            i.putExtra("game", game);
            i.putExtra("location", selectedLoc);
            startActivity(i);
        });
    }

    private void setSelected(String which) {
        boolean d = "delhi".equals(which);
        boolean m = "mumbai".equals(which);

        containerDelhi.setSelected(d);
        containerMumbai.setSelected(m);

        badgeDelhi.setVisibility(d ? View.VISIBLE : View.GONE);
        badgeMumbai.setVisibility(m ? View.VISIBLE : View.GONE);

        cardDelhi.setCardElevation(d ? 12f : 2f);
        cardMumbai.setCardElevation(m ? 12f : 2f);

        btnNext.setEnabled(d || m);
        btnNext.setAlpha((d || m) ? 1f : 0.4f);
    }
}
