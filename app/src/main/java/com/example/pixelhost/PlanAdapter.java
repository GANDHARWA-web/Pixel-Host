package com.example.pixelhost;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.VH> {

    public interface OnOrderClick { void onOrder(PlanItem item); }

    private final List<PlanItem> items;
    private final OnOrderClick callback;

    public PlanAdapter(List<PlanItem> items, OnOrderClick callback) {
        this.items = items;
        this.callback = callback;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plan, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        PlanItem p = items.get(position);
        h.title.setText(p.title);
        h.specs.setText(p.specs);
        h.price.setText(p.price);
        h.order.setOnClickListener(v -> {
            if (callback != null) callback.onOrder(p);
        });
    }

    @Override public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, specs, price, order;
        VH(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.tvTitle);
            specs = v.findViewById(R.id.tvSpecs);
            price = v.findViewById(R.id.tvPrice);
            order = v.findViewById(R.id.btnOrder);
        }
    }
}
