package com.resumechecker.analyzer;

import android.content.Context;
import android.net.Uri;

import com.resumechecker.model.ATSResult;
import com.resumechecker.model.ResumeReport;
import com.resumechecker.model.ResumeSection;
import com.resumechecker.utils.PdfExtractor;

import java.util.List;

public class ResumeAnalyzer {

    private final SectionDetector sectionDetector = new SectionDetector();
    private final ScoreEngine scoreEngine = new ScoreEngine();
    private final ATSEngine atsEngine = new ATSEngine();
    private final SuggestionGenerator suggestionGenerator = new SuggestionGenerator();

    public interface AnalysisCallback {
        void onSuccess(ResumeReport report);
        void onError(String message);
    }

    public void analyze(Context context, Uri pdfUri, String fileName,
                        String targetRole, AnalysisCallback callback) {

        new Thread(() -> {
            try {
                // 1. Extract text
                String text = PdfExtractor.extractText(context, pdfUri);
                if (text == null || text.trim().length() < 50) {
                    callback.onError("Could not extract text from PDF. Make sure it is not a scanned/image PDF.");
                    return;
                }

                // 2. Detect sections
                List<ResumeSection> sections = sectionDetector.detect(text);

                // 3. Build report object
                ResumeReport report = new ResumeReport();
                report.fileName = fileName;
                report.timestamp = System.currentTimeMillis();
                report.targetRole = targetRole;

                // 4. Score engines
                scoreEngine.populateResumeScore(report, text, sections);
                ATSResult atsResult = atsEngine.calculateATS(text, targetRole);
                atsEngine.populateATSData(report, atsResult);

                // 5. Generate suggestions
                report.suggestions = suggestionGenerator.generateAsStrings(report);

                callback.onSuccess(report);

            } catch (Exception e) {
                callback.onError("Analysis failed: " + e.getMessage());
            }
        }).start();
    }
}
