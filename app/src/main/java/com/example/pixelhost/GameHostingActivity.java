package com.example.pixelhost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class GameHostingActivity extends BaseActivity  {

    private String selectedGame = null;

    private CardView cardMinecraft, cardARK;
    private View containerMinecraft, containerARK;
    private View badgeMinecraft, badgeARK;
    private TextView btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_hosting);

        // Match EXACT ids from your XML
        cardMinecraft      = findViewById(R.id.cardMinecraft);
        cardARK            = findViewById(R.id.cardARK);

        containerMinecraft = findViewById(R.id.containerMinecraft);
        containerARK       = findViewById(R.id.containerARK);

        badgeMinecraft     = findViewById(R.id.badgeMinecraft);
        badgeARK           = findViewById(R.id.badgeARK);

        btnNext            = findViewById(R.id.btnNext);

        // disable Next until a game is chosen
        btnNext.setEnabled(false);
        btnNext.setAlpha(0.4f);

        // clicks
        cardMinecraft.setOnClickListener(v -> {
            selectedGame = "Minecraft";
            setSelected("mc");
        });

        cardARK.setOnClickListener(v -> {
            selectedGame = "ARK";
            setSelected("ark");
        });

        btnNext.setOnClickListener(v -> {
            if (selectedGame == null) return;
            Intent i = new Intent(this, GameLocationActivity.class);
            i.putExtra("game", selectedGame);
            startActivity(i);
        });
    }

    private void setSelected(String which) {
        boolean mc  = "mc".equals(which);
        boolean ark = "ark".equals(which);

        containerMinecraft.setSelected(mc);
        containerARK.setSelected(ark);

        badgeMinecraft.setVisibility(mc ? View.VISIBLE : View.GONE);
        badgeARK.setVisibility(ark ? View.VISIBLE : View.GONE);

        cardMinecraft.setCardElevation(mc ? 12f : 2f);
        cardARK.setCardElevation(ark ? 12f : 2f);

        btnNext.setEnabled(mc || ark);
        btnNext.setAlpha((mc || ark) ? 1f : 0.4f);
    }
}
