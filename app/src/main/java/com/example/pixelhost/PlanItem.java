package com.example.pixelhost;

public class PlanItem {
    public final String title;   // e.g., "1GB Plan"
    public final String specs;   // e.g., "1 GB RAM • 100% CPU • 5GB NVMe • Basic DDoS"
    public final String price;   // e.g., "₹30.00 /mo"

    public PlanItem(String title, String specs, String price) {
        this.title = title;
        this.specs = specs;
        this.price = price;
    }
}
