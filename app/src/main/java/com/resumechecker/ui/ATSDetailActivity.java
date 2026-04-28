package com.resumechecker.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.resumechecker.R;
import com.resumechecker.ResumeCheckerApp;
import com.resumechecker.adapter.KeywordChipAdapter;
import com.resumechecker.model.ResumeReport;
import com.resumechecker.utils.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ATSDetailActivity extends AppCompatActivity {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private TextView tvATSScore, tvMatchPercent, tvMatchedCount, tvMissingCount, tvRole;
    private LinearProgressIndicator progressMatch;
    private RecyclerView rvMatched, rvMissing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ats_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("ATS Analysis");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvATSScore     = findViewById(R.id.tvATSScore);
        tvMatchPercent = findViewById(R.id.tvMatchPercent);
        tvMatchedCount = findViewById(R.id.tvMatchedCount);
        tvMissingCount = findViewById(R.id.tvMissingCount);
        tvRole         = findViewById(R.id.tvRole);
        progressMatch  = findViewById(R.id.progressMatch);
        rvMatched      = findViewById(R.id.rvMatched);
        rvMissing      = findViewById(R.id.rvMissing);

        rvMatched.setLayoutManager(new LinearLayoutManager(this));
        rvMissing.setLayoutManager(new LinearLayoutManager(this));

        int reportId = getIntent().getIntExtra(Constants.KEY_REPORT_ID, -1);
        if (reportId == -1) { finish(); return; }

        executor.execute(() -> {
            ResumeReport report = ResumeCheckerApp.getInstance()
                    .getDatabase().reportDao().getById(reportId);
            runOnUiThread(() -> populateUI(report));
        });
    }

    private void populateUI(ResumeReport report) {
        if (report == null) return;

        int matchPct = (int) (report.keywordMatchRatio * 100);

        tvATSScore.setText(report.atsScore + "/100");
        tvMatchPercent.setText(matchPct + "% matched");
        tvRole.setText("Role: " + report.targetRole);

        // Just the number in the pill
        int matchedCount = report.matchedKeywords != null ? report.matchedKeywords.size() : 0;
        int missingCount = report.missingKeywords  != null ? report.missingKeywords.size()  : 0;
        tvMatchedCount.setText(String.valueOf(matchedCount));
        tvMissingCount.setText(String.valueOf(missingCount));

        progressMatch.setProgress(matchPct);
        // Dynamic color on progress bar
        int color = matchPct >= 65 ? 0xFF10B981 : matchPct >= 40 ? 0xFFF59E0B : 0xFFEF4444;
        progressMatch.setIndicatorColor(color);

        if (report.matchedKeywords != null) {
            rvMatched.setAdapter(new KeywordChipAdapter(report.matchedKeywords, true));
        }
        if (report.missingKeywords != null) {
            rvMissing.setAdapter(new KeywordChipAdapter(report.missingKeywords, false));
        }
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
