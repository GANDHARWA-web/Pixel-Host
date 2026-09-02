package com.example.pixelhost;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VpsPlansActivity extends BaseActivity  {

    private String location;   // "India" / "Singapore"
    private String cpuTier;    // "Budget VPS" or "Performance Premium VPS"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vps_plans);

        // Receive EXACT keys sent from VpsHostingActivity
        location = getIntent().getStringExtra("location");
        cpuTier  = getIntent().getStringExtra("cpuTier");

        // Fallbacks (avoid null in header)
        if (location == null) location = "India";
        if (cpuTier == null)  cpuTier  = "Budget VPS";

        // Header + sub
        ((TextView) findViewById(R.id.title))
                .setText("VPS — " + cpuTier + " Plans");
        ((TextView) findViewById(R.id.sub))
                .setText("Location: " + location + "     CPU: " + cpuTier);

        // Recycler
        RecyclerView rv = findViewById(R.id.recyclerPlans);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new PlanAdapter(buildVpsPlans(cpuTier), item -> {
            // Go to checkout summary (reuse same CheckoutActivity as games)
            Intent i = new Intent(this, CheckoutActivity.class);
            i.putExtra("game", "VPS");            // label for summary
            i.putExtra("location", location);
            i.putExtra("cpu", cpuTier);
            i.putExtra("planTitle", item.title);
            i.putExtra("planPrice", item.price);
            startActivity(i);
        }));

        // Optional global checkout FAB
        FloatingActionButton fabCheckout = findViewById(R.id.fabCheckout);
        if (fabCheckout != null) {
            fabCheckout.setOnClickListener(v ->
                    Toast.makeText(this, "Global Checkout (mock)", Toast.LENGTH_SHORT).show()
            );
        }
    }

    /** Build VPS plans from your provided datasets */
    private List<PlanItem> buildVpsPlans(String tier) {
        List<PlanItem> list = new ArrayList<>();

        if ("Performance Premium VPS".equals(tier)) {
            // Premium (Ryzen / i5 turbo) — your prices
            list.add(new PlanItem("Premium 8GB",
                    "2 vCores • 8 GB RAM • 40 GB NVMe • 1 Gbps • Premium DDoS",
                    "₹799.00 /month"));
            list.add(new PlanItem("Premium 16GB",
                    "4 vCores • 16 GB RAM • 80 GB NVMe • 1 Gbps • Premium DDoS",
                    "₹1499.00 /month"));
            list.add(new PlanItem("Premium 32GB",
                    "6 vCores • 32 GB RAM • 160 GB NVMe • 1 Gbps • Premium DDoS",
                    "₹2899.00 /month"));
            list.add(new PlanItem("Premium 48GB",
                    "8 vCores • 48 GB RAM • 240 GB NVMe • 1 Gbps • Premium DDoS",
                    "₹3899.00 /month"));
            list.add(new PlanItem("Premium 64GB",
                    "12 vCores • 64 GB RAM • 320 GB NVMe • 1 Gbps • Premium DDoS",
                    "₹4999.00 /month"));
        } else {
            // Budget (AMD EPYC 7282) — your prices
            list.add(new PlanItem("AMD VPS 8GB",
                    "EPYC 7282 • 3 vCores • 8 GB • 50 GB NVMe • Unmetered BW",
                    "₹650.00 /month"));
            list.add(new PlanItem("AMD VPS 16GB",
                    "EPYC 7282 • 4 vCores • 16 GB • 80 GB NVMe • Unmetered BW",
                    "₹999.00 /month"));
            list.add(new PlanItem("AMD VPS 24GB",
                    "EPYC 7282 • 6 vCores • 24 GB • 110 GB NVMe • Unmetered BW",
                    "₹1200.00 /month"));
            list.add(new PlanItem("AMD VPS 32GB",
                    "EPYC 7282 • 8 vCores • 32 GB • 150 GB NVMe • Unmetered BW",
                    "₹1399.00 /month"));
            list.add(new PlanItem("AMD VPS 48GB",
                    "EPYC 7282 • 10 vCores • 48 GB • 200 GB NVMe • Unmetered BW",
                    "₹1850.00 /month"));
            list.add(new PlanItem("AMD VPS 64GB",
                    "EPYC 7282 • 12 vCores • 64 GB • 256 GB NVMe • Unmetered BW",
                    "₹2200.00 /month"));
        }

        return list;
    }
}
