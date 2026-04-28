package com.resumechecker.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.resumechecker.R;
import com.resumechecker.ResumeCheckerApp;
import com.resumechecker.adapter.HistoryAdapter;
import com.resumechecker.model.ResumeReport;
import com.resumechecker.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements HistoryAdapter.OnItemClickListener {

    private RecyclerView rvHistory;
    private TextView tvEmptyState;
    private HistoryAdapter adapter;
    private List<ResumeReport> reportList = new ArrayList<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Temporarily hold the picked PDF uri until role is chosen
    private Uri pendingUri;
    private String pendingFileName;

    private final ActivityResultLauncher<String[]> pdfPickerLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), uri -> {
                if (uri != null) {
                    getContentResolver().takePersistableUriPermission(uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pendingUri      = uri;
                    pendingFileName = getFileName(uri);
                    showRolePickerDialog(); // ← ask role AFTER pdf is picked
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvHistory    = findViewById(R.id.rvHistory);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        MaterialButton btnUpload  = findViewById(R.id.btnUpload);
        MaterialButton btnHistory = findViewById(R.id.btnHistory);

        adapter = new HistoryAdapter(reportList, this);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(adapter);

        btnUpload.setOnClickListener(v -> pickPdf());
        btnHistory.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecentScans();
    }

    private void pickPdf() {
        pdfPickerLauncher.launch(new String[]{"application/pdf"});
    }

    /**
     * Shows a bottom-sheet style dialog letting the user pick their target role
     * before kicking off analysis. Called right after a PDF is picked.
     */
    private void showRolePickerDialog() {
        // Inflate a small dropdown layout inline
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_role_picker, null);
        MaterialAutoCompleteTextView roleDropdown =
                dialogView.findViewById(R.id.actvRole);

        List<String> roles = new ArrayList<>(Constants.ROLE_KEYWORDS.keySet());
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, roles);
        roleDropdown.setAdapter(roleAdapter);
        roleDropdown.setText(roles.get(0), false); // default selection

        new MaterialAlertDialogBuilder(this)
                .setTitle("🎯 Select Target Job Role")
                .setMessage("Choose the role this resume is targeting so we can calculate your ATS match accurately.")
                .setView(dialogView)
                .setPositiveButton("Analyze", (dialog, which) -> {
                    String selectedRole = roleDropdown.getText().toString().trim();
                    if (selectedRole.isEmpty()) selectedRole = roles.get(0);
                    openAnalysis(pendingUri, pendingFileName, selectedRole);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    pendingUri      = null;
                    pendingFileName = null;
                })
                .setCancelable(false) // force user to pick or cancel
                .show();
    }

    private void openAnalysis(Uri uri, String fileName, String targetRole) {
        Intent intent = new Intent(this, AnalysisActivity.class);
        intent.putExtra(Constants.KEY_PDF_URI, uri.toString());
        intent.putExtra("file_name", fileName);
        intent.putExtra("target_role", targetRole);
        startActivity(intent);
    }

    private void loadRecentScans() {
        executor.execute(() -> {
            List<ResumeReport> reports = ResumeCheckerApp.getInstance()
                    .getDatabase().reportDao().getAll();
            List<ResumeReport> recent = reports.size() > 3
                    ? reports.subList(0, 3) : reports;
            runOnUiThread(() -> {
                reportList.clear();
                reportList.addAll(recent);
                adapter.notifyDataSetChanged();
                tvEmptyState.setVisibility(reportList.isEmpty() ? View.VISIBLE  : View.GONE);
                rvHistory.setVisibility   (reportList.isEmpty() ? View.GONE     : View.VISIBLE);
            });
        });
    }

    private String getFileName(Uri uri) {
        String name = "resume.pdf";
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (idx >= 0) name = cursor.getString(idx);
            }
        }
        return name;
    }

    @Override
    public void onItemClick(ResumeReport report) {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra(Constants.KEY_REPORT_ID, report.id);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
