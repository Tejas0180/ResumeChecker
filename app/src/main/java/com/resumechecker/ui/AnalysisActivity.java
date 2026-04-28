package com.resumechecker.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.resumechecker.R;
import com.resumechecker.ResumeCheckerApp;
import com.resumechecker.analyzer.ResumeAnalyzer;
import com.resumechecker.model.ResumeReport;
import com.resumechecker.utils.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnalysisActivity extends AppCompatActivity {

    private TextView tvStatus;
    private ProgressBar progressBar;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        tvStatus = findViewById(R.id.tvStatus);
        progressBar = findViewById(R.id.progressBar);

        String uriStr = getIntent().getStringExtra(Constants.KEY_PDF_URI);
        String fileName = getIntent().getStringExtra("file_name");
        String targetRole = getIntent().getStringExtra("target_role");

        if (uriStr == null) {
            finish();
            return;
        }

        Uri pdfUri = Uri.parse(uriStr);
        startAnalysis(pdfUri, fileName, targetRole);
    }

    private void startAnalysis(Uri pdfUri, String fileName, String targetRole) {
        updateStatus("Extracting text from PDF...");

        ResumeAnalyzer analyzer = new ResumeAnalyzer();
        analyzer.analyze(this, pdfUri, fileName, targetRole, new ResumeAnalyzer.AnalysisCallback() {
            @Override
            public void onSuccess(ResumeReport report) {
                updateStatus("Calculating scores...");
                executor.execute(() -> {
                    long reportId = ResumeCheckerApp.getInstance()
                            .getDatabase().reportDao().insert(report);
                    runOnUiThread(() -> openReport((int) reportId));
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    tvStatus.setText("Error: " + message);
                    progressBar.setVisibility(View.GONE);
                });
            }
        });
    }

    private void updateStatus(String msg) {
        runOnUiThread(() -> tvStatus.setText(msg));
    }

    private void openReport(int reportId) {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra(Constants.KEY_REPORT_ID, reportId);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
