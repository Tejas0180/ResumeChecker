package com.resumechecker.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.resumechecker.R;
import com.resumechecker.ResumeCheckerApp;
import com.resumechecker.model.ResumeReport;
import com.resumechecker.utils.Constants;
import com.resumechecker.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportActivity extends AppCompatActivity {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private ResumeReport report;

    private PieChart chartResume, chartATS;
    private TextView tvResumeScore, tvResumeLabel, tvATSScore, tvATSLabel;
    private TextView tvFileName, tvRole, tvWordCount, tvSections, tvActionVerbs;
    private TextView tvEmailStatus, tvPhoneStatus, tvLinkedInStatus, tvGitHubStatus;
    private MaterialButton btnATS, btnSuggestions, btnJobMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        bindViews();

        int reportId = getIntent().getIntExtra(Constants.KEY_REPORT_ID, -1);
        if (reportId == -1) { finish(); return; }

        executor.execute(() -> {
            report = ResumeCheckerApp.getInstance().getDatabase().reportDao().getById(reportId);
            runOnUiThread(this::populateUI);
        });
    }

    private void bindViews() {
        chartResume       = findViewById(R.id.chartResume);
        chartATS          = findViewById(R.id.chartATS);
        tvResumeScore     = findViewById(R.id.tvResumeScore);
        tvResumeLabel     = findViewById(R.id.tvResumeLabel);
        tvATSScore        = findViewById(R.id.tvATSScore);
        tvATSLabel        = findViewById(R.id.tvATSLabel);
        tvFileName        = findViewById(R.id.tvFileName);
        tvRole            = findViewById(R.id.tvRole);
        tvWordCount       = findViewById(R.id.tvWordCount);
        tvSections        = findViewById(R.id.tvSections);
        tvActionVerbs     = findViewById(R.id.tvActionVerbs);
        tvEmailStatus     = findViewById(R.id.tvEmailStatus);
        tvPhoneStatus     = findViewById(R.id.tvPhoneStatus);
        tvLinkedInStatus  = findViewById(R.id.tvLinkedInStatus);
        tvGitHubStatus    = findViewById(R.id.tvGitHubStatus);
        btnATS            = findViewById(R.id.btnATS);
        btnSuggestions    = findViewById(R.id.btnSuggestions);
        btnJobMatch       = findViewById(R.id.btnJobMatch);
    }

    private void populateUI() {
        if (report == null) return;

        tvFileName.setText(report.fileName);
        tvRole.setText("Target Role: " + report.targetRole);
        tvWordCount.setText(report.wordCount + " words");
        tvSections.setText(report.sectionsFound + " sections detected");
        tvActionVerbs.setText(report.actionVerbCount + " action verbs found");

        // Contact status
        tvEmailStatus.setText(report.hasEmail ? "✓ Email" : "✗ Email");
        tvPhoneStatus.setText(report.hasPhone ? "✓ Phone" : "✗ Phone");
        tvLinkedInStatus.setText(report.hasLinkedIn ? "✓ LinkedIn" : "✗ LinkedIn");
        tvGitHubStatus.setText(report.hasGitHub ? "✓ GitHub" : "✗ GitHub");

        tvEmailStatus.setTextColor(report.hasEmail ? 0xFF4CAF50 : 0xFFF44336);
        tvPhoneStatus.setTextColor(report.hasPhone ? 0xFF4CAF50 : 0xFFF44336);
        tvLinkedInStatus.setTextColor(report.hasLinkedIn ? 0xFF4CAF50 : 0xFFFF9800);
        tvGitHubStatus.setTextColor(report.hasGitHub ? 0xFF4CAF50 : 0xFFFF9800);

        // Resume score chart
        setupPieChart(chartResume, report.resumeScore);
        tvResumeScore.setText(report.resumeScore + "%");
        tvResumeLabel.setText(TextUtils.getScoreLabel(report.resumeScore));
        tvResumeLabel.setTextColor(TextUtils.getScoreColor(report.resumeScore));

        // ATS score chart
        setupPieChart(chartATS, report.atsScore);
        tvATSScore.setText(report.atsScore + "%");
        tvATSLabel.setText(TextUtils.getScoreLabel(report.atsScore));
        tvATSLabel.setTextColor(TextUtils.getScoreColor(report.atsScore));

        // Buttons
        btnATS.setOnClickListener(v -> {
            Intent i = new Intent(this, ATSDetailActivity.class);
            i.putExtra(Constants.KEY_REPORT_ID, report.id);
            startActivity(i);
        });
        btnSuggestions.setOnClickListener(v -> {
            Intent i = new Intent(this, SuggestionsActivity.class);
            i.putExtra(Constants.KEY_REPORT_ID, report.id);
            startActivity(i);
        });
        btnJobMatch.setOnClickListener(v -> {
            Intent i = new Intent(this, JobMatchActivity.class);
            i.putExtra(Constants.KEY_REPORT_ID, report.id);
            startActivity(i);
        });
    }

    private void setupPieChart(PieChart chart, int score) {
        int color = TextUtils.getScoreColor(score);
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(score, ""));
        entries.add(new PieEntry(100 - score, ""));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(color, 0xFFEEEEEE);
        dataSet.setDrawValues(false);
        dataSet.setSliceSpace(2f);

        chart.setData(new PieData(dataSet));
        chart.setHoleRadius(72f);
        chart.setTransparentCircleRadius(75f);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setDrawEntryLabels(false);
        chart.setTouchEnabled(false);
        chart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
