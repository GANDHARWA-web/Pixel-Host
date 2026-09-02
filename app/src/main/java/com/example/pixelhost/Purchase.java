package com.example.pixelhost;

import org.json.JSONException;
import org.json.JSONObject;

public class Purchase {
    public String orderId;
    public String email;
    public String password; // 10-digit random
    public String label;    // Game or "VPS"
    public String cpu;
    public String location;
    public String planTitle;
    public String planPrice;
    public long timestamp;

    public Purchase() {}

    public Purchase(String orderId, String email, String password, String label,
                    String cpu, String location, String planTitle, String planPrice, long timestamp) {
        this.orderId = orderId;
        this.email = email;
        this.password = password;
        this.label = label;
        this.cpu = cpu;
        this.location = location;
        this.planTitle = planTitle;
        this.planPrice = planPrice;
        this.timestamp = timestamp;
    }

    // JSON helpers
    public JSONObject toJson() {
        JSONObject o = new JSONObject();
        try {
            o.put("orderId", orderId);
            o.put("email", email);
            o.put("password", password);
            o.put("label", label);
            o.put("cpu", cpu);
            o.put("location", location);
            o.put("planTitle", planTitle);
            o.put("planPrice", planPrice);
            o.put("timestamp", timestamp);
        } catch (JSONException ignored) {}
        return o;
    }

    public static Purchase fromJson(JSONObject o) {
        Purchase p = new Purchase();
        p.orderId = o.optString("orderId");
        p.email = o.optString("email");
        p.password = o.optString("password");
        p.label = o.optString("label");
        p.cpu = o.optString("cpu");
        p.location = o.optString("location");
        p.planTitle = o.optString("planTitle");
        p.planPrice = o.optString("planPrice");
        p.timestamp = o.optLong("timestamp");
        return p;
    }
}
