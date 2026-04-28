package com.resumechecker.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.resumechecker.R;
import com.resumechecker.ResumeCheckerApp;
import com.resumechecker.analyzer.KeywordMatcher;
import com.resumechecker.model.ResumeReport;
import com.resumechecker.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobMatchActivity extends AppCompatActivity {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private ResumeReport report;

    private AutoCompleteTextView spinnerRole;
    private MaterialButton btnAnalyze;
    private TextView tvMatchScore, tvMatchedCount, tvMissingCount;
    private LinearProgressIndicator progressMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_match);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Job Role Match");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spinnerRole    = findViewById(R.id.spinnerRole);
        btnAnalyze     = findViewById(R.id.btnAnalyze);
        tvMatchScore   = findViewById(R.id.tvMatchScore);
        tvMatchedCount = findViewById(R.id.tvMatchedCount);
        tvMissingCount = findViewById(R.id.tvMissingCount);
        progressMatch  = findViewById(R.id.progressMatch);

        List<String> roles = new ArrayList<>(Constants.ROLE_KEYWORDS.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, roles);
        spinnerRole.setAdapter(adapter);
        spinnerRole.setText(roles.get(0), false);

        int reportId = getIntent().getIntExtra(Constants.KEY_REPORT_ID, -1);
        if (reportId == -1) { finish(); return; }

        executor.execute(() -> {
            report = ResumeCheckerApp.getInstance()
                    .getDatabase().reportDao().getById(reportId);
            runOnUiThread(() -> {
                if (report != null) spinnerRole.setText(report.targetRole, false);
            });
        });

        btnAnalyze.setOnClickListener(v -> analyzeRole(spinnerRole.getText().toString().trim()));
    }

    private void analyzeRole(String role) {
        if (report == null) return;
        executor.execute(() -> {
            // Build search text from already-matched keywords list
            String text = buildTextFromKeywords(report.matchedKeywords);
            List<String> matched = KeywordMatcher.getMatchedKeywords(text, role);
            List<String> missing = KeywordMatcher.getMissingKeywords(text, role);
            float ratio = KeywordMatcher.getMatchRatio(text, role);
            int pct = (int) (ratio * 100);
            int color = pct >= 65 ? 0xFF10B981 : pct >= 40 ? 0xFFF59E0B : 0xFFEF4444;
            String label = pct >= 75 ? "🟢 Strong Match" :
                           pct >= 50 ? "🟡 Good Match"   :
                           pct >= 30 ? "🟠 Fair Match"   : "🔴 Low Match";

            runOnUiThread(() -> {
                tvMatchScore.setText(pct + "% · " + label + "\nfor " + role);
                progressMatch.setProgress(pct);
                progressMatch.setIndicatorColor(color);
                // update hidden views silently
                if (tvMatchedCount != null) tvMatchedCount.setText(String.valueOf(matched.size()));
                if (tvMissingCount != null) tvMissingCount.setText(String.valueOf(missing.size()));
            });
        });
    }

    private String buildTextFromKeywords(List<String> keywords) {
        if (keywords == null) return "";
        StringBuilder sb = new StringBuilder();
        for (String kw : keywords) sb.append(kw).append(" ");
        return sb.toString();
    }

    @Override public boolean onSupportNavigateUp() { onBackPressed(); return true; }
    @Override protected void onDestroy() { super.onDestroy(); executor.shutdown(); }
}
