package com.example.pixelhost;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GamePlansActivity extends BaseActivity  {

    private String game, location, cpu; // received from previous steps

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_plans);

        game = getIntent().getStringExtra("game");
        location = getIntent().getStringExtra("location");
        cpu = getIntent().getStringExtra("cpu"); // "Intel" or "Ryzen"

        TextView tvTitle = findViewById(R.id.title);
        TextView tvSub = findViewById(R.id.sub);
        tvTitle.setText(game + " — " + cpu + " Plans");
        tvSub.setText("Location: " + location);

        RecyclerView rv = findViewById(R.id.recyclerPlans);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new PlanAdapter(buildPlans(cpu), item -> {
            // Go to Checkout Summary with the selected plan
            Intent i = new Intent(this, CheckoutActivity.class);
            i.putExtra("game", game);
            i.putExtra("location", location);
            i.putExtra("cpu", cpu);
            i.putExtra("planTitle", item.title);
            i.putExtra("planSpecs", item.specs);
            i.putExtra("planPrice", item.price);
            startActivity(i);
        }));

        FloatingActionButton fabCheckout = findViewById(R.id.fabCheckout);
        fabCheckout.setOnClickListener(v ->
                Toast.makeText(this, "Global Checkout  — select a plan first", Toast.LENGTH_SHORT).show()
        );
    }

    // Build the list based on CPU (prices same for Delhi/Mumbai as you said)
    private List<PlanItem> buildPlans(String cpu) {
        List<PlanItem> list = new ArrayList<>();
        if ("Intel".equals(cpu)) {
            // MINECRAFT/ARK INTEL PLANS (your list)
            list.add(new PlanItem("1GB Plan", "1 GB RAM • 100% CPU • 5GB NVMe SSD • Basic DDoS", "₹30.00 /mo"));
            list.add(new PlanItem("2GB Plan", "2 GB RAM • 100% CPU • 4GB NVMe SSD • Basic DDoS", "₹60.00 /mo"));
            list.add(new PlanItem("4GB Plan", "4 GB RAM • 200% CPU • 8GB NVMe SSD • Basic DDoS", "₹125.00 /mo"));
            list.add(new PlanItem("8GB Plan", "8 GB RAM • 200% CPU • 16GB NVMe SSD • Basic DDoS", "₹250.00 /mo"));
            list.add(new PlanItem("12GB Plan", "12 GB RAM • 300% CPU • 24GB NVMe SSD • Basic DDoS", "₹399.00 /mo"));
            list.add(new PlanItem("16GB Plan", "16 GB RAM • 400% CPU • 32GB NVMe SSD • Basic DDoS", "₹499.00 /mo"));
            list.add(new PlanItem("24GB Plan", "24 GB RAM • 500% CPU • 48GB NVMe SSD • Basic DDoS", "₹750.00 /mo"));
            list.add(new PlanItem("32GB Plan", "32 GB RAM • Unlimited CPU • 64GB NVMe SSD • Basic DDoS", "₹899.00 /mo"));
            list.add(new PlanItem("48GB Plan", "48 GB RAM • Unlimited CPU • 96GB NVMe SSD • Basic DDoS", "₹1200.00 /mo"));
        } else {
            // MINECRAFT/ARK RYZEN PLANS (your list)
            list.add(new PlanItem("1GB Plan (Premium)", "1 GB RAM • 80% CPU • 2GB NVMe SSD • Premium DDoS", "₹50.00 /mo"));
            list.add(new PlanItem("2GB Plan (Premium)", "2 GB RAM • 90% CPU • 4GB NVMe SSD • Premium DDoS", "₹100.00 /mo"));
            list.add(new PlanItem("4GB Plan (Premium)", "4 GB RAM • 100% CPU • 8GB NVMe SSD • Premium DDoS", "₹200.00 /mo"));
            list.add(new PlanItem("6GB Plan (Premium)", "6 GB RAM • 200% CPU • 12GB NVMe SSD • Premium DDoS", "₹350.00 /mo"));
            list.add(new PlanItem("8GB Plan (Premium)", "8 GB RAM • 250% CPU • 16GB NVMe SSD • Premium DDoS", "₹449.00 /mo"));
            list.add(new PlanItem("12GB Plan (Premium)", "12 GB RAM • 350% CPU • 24GB NVMe SSD • Premium DDoS", "₹600.00 /mo"));
            list.add(new PlanItem("16GB Plan (Premium)", "16 GB RAM • 450% CPU • 32GB NVMe SSD • Premium DDoS", "₹850.00 /mo"));
            list.add(new PlanItem("24GB Plan (Premium)", "24 GB RAM • 650% CPU • 48GB NVMe SSD • Premium DDoS", "₹1200.00 /mo"));
            list.add(new PlanItem("32GB Plan (Premium)", "32 GB RAM • Unlimited CPU • 64GB NVMe SSD • Premium DDoS", "₹1700.00 /mo"));
            list.add(new PlanItem("48GB Plan (Premium)", "48 GB RAM • Unlimited CPU • 96GB NVMe SSD • Premium DDoS", "₹2400.00 /mo"));
        }
        return list;
    }
}
