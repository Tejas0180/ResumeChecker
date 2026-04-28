package com.resumechecker.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.resumechecker.R;
import com.resumechecker.ResumeCheckerApp;
import com.resumechecker.adapter.SuggestionAdapter;
import com.resumechecker.analyzer.SuggestionGenerator;
import com.resumechecker.model.ResumeReport;
import com.resumechecker.model.Suggestion;
import com.resumechecker.utils.Constants;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SuggestionsActivity extends AppCompatActivity {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private RecyclerView rvSuggestions;
    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Suggestions");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvSuggestions = findViewById(R.id.rvSuggestions);
        tvCount       = findViewById(R.id.tvCount);
        rvSuggestions.setLayoutManager(new LinearLayoutManager(this));

        int reportId = getIntent().getIntExtra(Constants.KEY_REPORT_ID, -1);
        if (reportId == -1) { finish(); return; }

        executor.execute(() -> {
            ResumeReport report = ResumeCheckerApp.getInstance()
                    .getDatabase().reportDao().getById(reportId);
            List<Suggestion> suggestions = new SuggestionGenerator().generate(report);
            runOnUiThread(() -> {
                tvCount.setText(suggestions.size() + " suggestions");
                rvSuggestions.setAdapter(new SuggestionAdapter(suggestions));
            });
        });
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
