package com.resumechecker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.resumechecker.R;
import com.resumechecker.model.ResumeReport;
import com.resumechecker.utils.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ResumeReport report);
    }

    private final List<ResumeReport> items;
    private final OnItemClickListener listener;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());

    public HistoryAdapter(List<ResumeReport> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        ResumeReport r = items.get(pos);
        h.tvFileName.setText(r.fileName);
        h.tvDate.setText(SDF.format(new Date(r.timestamp)));
        h.tvRole.setText(r.targetRole);
        h.tvResumeScore.setText("Resume: " + r.resumeScore + "%");
        h.tvATSScore.setText("ATS: " + r.atsScore + "%");
        h.tvResumeScore.setTextColor(TextUtils.getScoreColor(r.resumeScore));
        h.tvATSScore.setTextColor(TextUtils.getScoreColor(r.atsScore));
        h.itemView.setOnClickListener(v -> listener.onItemClick(r));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFileName, tvDate, tvRole, tvResumeScore, tvATSScore;
        ViewHolder(View v) {
            super(v);
            tvFileName    = v.findViewById(R.id.tvFileName);
            tvDate        = v.findViewById(R.id.tvDate);
            tvRole        = v.findViewById(R.id.tvRole);
            tvResumeScore = v.findViewById(R.id.tvResumeScore);
            tvATSScore    = v.findViewById(R.id.tvATSScore);
        }
    }
}
