package com.resumechecker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.resumechecker.R;
import com.resumechecker.ResumeCheckerApp;
import com.resumechecker.adapter.HistoryAdapter;
import com.resumechecker.model.ResumeReport;
import com.resumechecker.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity implements HistoryAdapter.OnItemClickListener {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private RecyclerView rvHistory;
    private TextView tvEmpty;
    private HistoryAdapter adapter;
    private final List<ResumeReport> reportList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Scan History");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvHistory = findViewById(R.id.rvHistory);
        tvEmpty   = findViewById(R.id.tvEmpty);
        MaterialButton btnClearAll = findViewById(R.id.btnClearAll);

        adapter = new HistoryAdapter(reportList, this);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(adapter);

        btnClearAll.setOnClickListener(v -> confirmClearAll());

        loadHistory();
    }

    private void loadHistory() {
        executor.execute(() -> {
            List<ResumeReport> reports = ResumeCheckerApp.getInstance()
                    .getDatabase().reportDao().getAll();
            runOnUiThread(() -> {
                reportList.clear();
                reportList.addAll(reports);
                adapter.notifyDataSetChanged();
                tvEmpty.setVisibility(reportList.isEmpty() ? View.VISIBLE : View.GONE);
                rvHistory.setVisibility(reportList.isEmpty() ? View.GONE : View.VISIBLE);
            });
        });
    }

    private void confirmClearAll() {
        new AlertDialog.Builder(this)
                .setTitle("Clear History")
                .setMessage("Delete all scan history?")
                .setPositiveButton("Delete", (d, w) -> {
                    executor.execute(() -> {
                        ResumeCheckerApp.getInstance().getDatabase().reportDao().deleteAll();
                        runOnUiThread(this::loadHistory);
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onItemClick(ResumeReport report) {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra(Constants.KEY_REPORT_ID, report.id);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
