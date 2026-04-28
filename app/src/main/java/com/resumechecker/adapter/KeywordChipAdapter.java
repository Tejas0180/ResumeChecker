package com.resumechecker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.resumechecker.R;

import java.util.List;

public class KeywordChipAdapter extends RecyclerView.Adapter<KeywordChipAdapter.VH> {

    private final List<String> items;
    private final boolean isMatched;

    public KeywordChipAdapter(List<String> items, boolean isMatched) {
        this.items     = items;
        this.isMatched = isMatched;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_keyword_chip, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String keyword = items.get(position);
        holder.tvKeyword.setText(keyword);
        if (isMatched) {
            holder.tvKeyword.setBackgroundResource(R.drawable.bg_chip_green);
            holder.tvKeyword.setTextColor(0xFF065F46); // dark green
        } else {
            holder.tvKeyword.setBackgroundResource(R.drawable.bg_chip_red);
            holder.tvKeyword.setTextColor(0xFF991B1B); // dark red
        }
    }

    @Override public int getItemCount() { return items == null ? 0 : items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvKeyword;
        VH(View v) {
            super(v);
            tvKeyword = v.findViewById(R.id.tvKeyword);
        }
    }
}
