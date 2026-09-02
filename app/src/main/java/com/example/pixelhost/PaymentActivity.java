package com.example.pixelhost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PaymentActivity extends BaseActivity  {

    private EditText etEmail, etCardNumber, etMonth, etYear, etCardName, etCvv, etUpi;
    private RadioButton rbCard, rbUpi;
    private boolean updatingMonth = false;

    private static final String PREFS = "pixelhost_prefs";
    private static final String KEY_ORDERS = "orders_json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        etEmail      = findViewById(R.id.etEmail);
        etCardNumber = findViewById(R.id.etCardNumber);
        etMonth      = findViewById(R.id.etMonth);
        etYear       = findViewById(R.id.etYear);
        etCardName   = findViewById(R.id.etCardName);
        etCvv        = findViewById(R.id.etCvv);
        etUpi        = findViewById(R.id.etUpiId);

        rbCard = findViewById(R.id.rbCard);
        rbUpi  = findViewById(R.id.rbUpi);

        // filters
        etMonth.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(2), onlyDigitsFilter });
        etYear.setFilters(new InputFilter[]{ onlyDigitsFilter, new InputFilter.LengthFilter(4) });
        etCvv.setFilters(new InputFilter[]{ onlyDigitsFilter, new InputFilter.LengthFilter(3) });
        etCardName.setFilters(new InputFilter[]{ onlyLettersFilter });
        etCardNumber.setFilters(new InputFilter[]{ onlyDigitsFilter, new InputFilter.LengthFilter(16) });

        // month watcher (clamp after 2 digits)
        etMonth.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                if (updatingMonth) return;
                updatingMonth = true;
                try {
                    String v = s.toString();
                    if (v.length() < 2) return;
                    if (v.length() == 2) {
                        try {
                            int val = Integer.parseInt(v);
                            if (val < 1) {
                                etMonth.setText("01");
                                etMonth.setSelection(2);
                            } else if (val > 12) {
                                etMonth.setText("12");
                                etMonth.setSelection(2);
                            } else {
                                etMonth.setSelection(2);
                            }
                        } catch (NumberFormatException ignored) {}
                    }
                } finally { updatingMonth = false; }
            }
        });

        rbCard.setOnCheckedChangeListener((b, checked) -> {
            findViewById(R.id.cardFields).setVisibility(checked? View.VISIBLE: View.GONE);
            findViewById(R.id.upiFields).setVisibility(checked? View.GONE: View.VISIBLE);
        });
        rbUpi.setOnCheckedChangeListener((b, checked) -> {
            findViewById(R.id.cardFields).setVisibility(checked? View.GONE: View.VISIBLE);
            findViewById(R.id.upiFields).setVisibility(checked? View.VISIBLE: View.GONE);
        });

        findViewById(R.id.btnPay).setOnClickListener(v -> doPay());
    }

    private void doPay() {
        // Reuse validation from before
        String email = safeText(etEmail);
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            setErr(etEmail, "Enter a valid email"); return;
        }

        boolean usingCard = rbCard.isChecked();

        if (usingCard) {
            String card = safeDigits(etCardNumber);
            String mm   = safeText(etMonth);
            String yyyy = safeText(etYear);
            String name = safeText(etCardName);
            String cvv  = safeText(etCvv);

            if (card.length() != 16) { setErr(etCardNumber, "Enter 16-digit card number"); return; }
            if (mm.length() != 2)    { setErr(etMonth, "Enter month as MM"); return; }
            int mVal = parseInt(mm, -1);
            if (mVal < 1 || mVal > 12) { setErr(etMonth, "Month must be 01–12"); return; }
            if (yyyy.length() != 4 || !isAllDigits(yyyy)) { setErr(etYear, "Enter year as YYYY"); return; }
            if (!name.matches("^[A-Za-z ]+$")) { setErr(etCardName, "Letters and spaces only"); return; }
            if (cvv.length() != 3 || !isAllDigits(cvv)) { setErr(etCvv, "Enter 3-digit CVV"); return; }
        } else {
            String upi = safeText(etUpi);
            if (!upi.matches("^[A-Za-z0-9._-]{2,}@[A-Za-z]{2,}$")) {
                setErr(etUpi, "Enter UPI like name@bank"); return;
            }
        }

        // Build purchase info (we will try to read details passed through Intent: planTitle, planPrice, game, cpu, location)
        String planTitle = getIntent().getStringExtra("planTitle");
        String planPrice = getIntent().getStringExtra("planPrice");
        String label     = getIntent().getStringExtra("game"); // e.g., "Minecraft" or "VPS"
        String cpu       = getIntent().getStringExtra("cpu");
        String location  = getIntent().getStringExtra("location");

        if (planTitle == null) planTitle = "Selected Plan";
        if (planPrice == null) planPrice = "₹0.00";

        if (label == null) label = usingCard ? "Server" : "VPS";
        if (cpu == null) cpu = "";
        if (location == null) location = "";

        // create order id and password
        String orderId = "PH-" + System.currentTimeMillis()/1000L; // e.g. PH-163...
        String password = generateRandomDigits(10);

        // timestamp
        long ts = System.currentTimeMillis();

        // create Purchase object and save
        Purchase p = new Purchase(orderId, email, password, label, cpu, location, planTitle, planPrice, ts);
        savePurchase(p);

        // show invoice dialog
        showInvoiceDialog(p);
    }

    // Show a custom dialog with invoice details
    private void showInvoiceDialog(Purchase p) {
        LayoutInflater li = LayoutInflater.from(this);
        View view = li.inflate(R.layout.dialog_invoice, null);

        // fill views
        ((EditText)view.findViewById(R.id.invOrderId)).setText(p.orderId);
        ((EditText)view.findViewById(R.id.invEmail)).setText(p.email);
        ((EditText)view.findViewById(R.id.invPassword)).setText(p.password);
        ((EditText)view.findViewById(R.id.invLabel)).setText(p.label);
        ((EditText)view.findViewById(R.id.invCpu)).setText(p.cpu);
        ((EditText)view.findViewById(R.id.invLocation)).setText(p.location);
        ((EditText)view.findViewById(R.id.invPlan)).setText(p.planTitle + " — " + p.planPrice);

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date(p.timestamp));
        ((EditText)view.findViewById(R.id.invTimestamp)).setText(date);

        AlertDialog dlg = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("Done", (d,w)-> { d.dismiss(); finish(); })
                .setNeutralButton("View History", (d,w)-> {
                    d.dismiss();
                    startActivity(new Intent(this, HistoryActivity.class));
                    finish();
                })
                .create();
        dlg.show();
    }

    // Save JSON array to SharedPreferences
    private void savePurchase(Purchase p) {
        SharedPreferences prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String raw = prefs.getString(KEY_ORDERS, "[]");
        try {
            JSONArray arr = new JSONArray(raw);
            arr.put(p.toJson());
            prefs.edit().putString(KEY_ORDERS, arr.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // helpers
    private String safeText(EditText e) {
        return e.getText() == null ? "" : e.getText().toString().trim();
    }

    // NEW: digits-only from any EditText (used for card number)
    private String safeDigits(EditText e) {
        String s = safeText(e);
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) sb.append(c);
        }
        return sb.toString();
    }

    private boolean isAllDigits(String s) {
        if (s==null || s.isEmpty()) return false;
        for (char c: s.toCharArray()) if (!Character.isDigit(c)) return false;
        return true;
    }

    private int parseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (Exception e) { return def; }
    }

    private void setErr(EditText e, String msg) {
        e.setError(msg);
        e.requestFocus();
    }

    private String generateRandomDigits(int n) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(r.nextInt(10));
        return sb.toString();
    }

    // Input filters
    private final InputFilter onlyDigitsFilter = (source, start, end, dest, dstart, dend) -> {
        for (int i = start; i < end; i++) if (!Character.isDigit(source.charAt(i))) return "";
        return null;
    };
    private final InputFilter onlyLettersFilter = (source, start, end, dest, dstart, dend) -> {
        for (int i = start; i < end; i++) { char c = source.charAt(i); if (!Character.isLetter(c) && c!=' ') return ""; }
        return null;
    };
}
