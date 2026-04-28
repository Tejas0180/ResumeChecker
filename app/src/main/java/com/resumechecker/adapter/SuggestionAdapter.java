package com.resumechecker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.resumechecker.R;
import com.resumechecker.model.Suggestion;

import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.ViewHolder> {

    private final List<Suggestion> items;

    public SuggestionAdapter(List<Suggestion> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_suggestion, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        Suggestion s = items.get(pos);
        h.tvTitle.setText(s.getTitle());
        h.tvMessage.setText(s.getMessage());
        h.tvPriority.setText(s.getPriority().name());
        h.tvType.setText(s.getType().name());

        // Priority color
        int priorityColor;
        switch (s.getPriority()) {
            case HIGH:   priorityColor = 0xFFF44336; break;
            case MEDIUM: priorityColor = 0xFFFF9800; break;
            default:     priorityColor = 0xFF4CAF50; break;
        }
        h.tvPriority.setTextColor(priorityColor);
        h.vPriorityBar.setBackgroundColor(priorityColor);
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMessage, tvPriority, tvType;
        View vPriorityBar;
        ViewHolder(View v) {
            super(v);
            tvTitle      = v.findViewById(R.id.tvTitle);
            tvMessage    = v.findViewById(R.id.tvMessage);
            tvPriority   = v.findViewById(R.id.tvPriority);
            tvType       = v.findViewById(R.id.tvType);
            vPriorityBar = v.findViewById(R.id.vPriorityBar);
        }
    }
}
