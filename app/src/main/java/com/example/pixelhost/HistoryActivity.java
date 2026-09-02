package com.example.pixelhost;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HistoryActivity extends BaseActivity  {

    private static final String PREFS = "pixelhost_prefs";
    private static final String KEY_ORDERS = "orders_json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        LinearLayout container = findViewById(R.id.historyContainer);
        SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String raw = prefs.getString(KEY_ORDERS, "[]");

        try {
            JSONArray arr = new JSONArray(raw);
            if (arr.length() == 0) {
                TextView tv = new TextView(this);
                tv.setText("No purchases yet.");
                container.addView(tv);
            } else {
                for (int i = arr.length()-1; i >= 0; i--) { // newest first
                    JSONObject o = arr.getJSONObject(i);
                    Purchase p = Purchase.fromJson(o);
                    TextView tv = new TextView(this);
                    String when = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                            .format(new Date(p.timestamp));
                    String line = p.orderId + " — " + p.planTitle + " • " + p.planPrice + "\n"
                            + p.email + " • " + when + "\n"
                            + "ID: " + p.email + "  |  Pass: " + p.password + "\n\n";
                    tv.setText(line);
                    tv.setPadding(8,12,8,12);
                    container.addView(tv);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TextView tv = new TextView(this);
            tv.setText("Failed to load history.");
            container.addView(tv);
        }
    }
}
