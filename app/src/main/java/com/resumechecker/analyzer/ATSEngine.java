package com.resumechecker.analyzer;

import com.resumechecker.model.ATSResult;
import com.resumechecker.model.ResumeReport;
import com.resumechecker.utils.Constants;
import com.resumechecker.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ATS scoring with synonym-aware keyword matching and relaxed thresholds.
 */
public class ATSEngine {

    public ATSResult calculateATS(String resumeText, String targetRole) {
        int score = 0;
        List<String> matched = new ArrayList<>();
        List<String> missing = new ArrayList<>();
        String lower = resumeText.toLowerCase();

        // ── Role skill match via synonym-aware matcher (35 pts) ─────────────
        String[] roleKeywords = getKeywordsForRole(targetRole);
        for (String kw : roleKeywords) {
            if (KeywordMatcher.isKeywordPresent(lower, kw)) {
                matched.add(kw);
            } else {
                missing.add(kw);
            }
        }
        float matchRatio = roleKeywords.length > 0
                ? (float) matched.size() / roleKeywords.length : 0f;
        score += (int) (matchRatio * 35);

        // ── Standard ATS section headings (15 pts) ───────────────────────────
        int headingsFound = 0;
        for (String h : Constants.ATS_HEADINGS) {
            if (lower.contains(h)) headingsFound++;
        }
        score += Math.min(headingsFound * 2, 15);

        // ── Skills/Competencies section present (10 pts) ─────────────────────
        if (lower.contains("skill") || lower.contains("competenc") ||
            lower.contains("technolog") || lower.contains("proficien")) score += 10;

        // ── Date format detected (10 pts) ────────────────────────────────────
        if (TextUtils.containsPattern(resumeText, Constants.DATE_REGEX)) score += 10;

        // ── Word count in ATS-friendly range (10 pts) ─────────────────────────
        int wc = TextUtils.wordCount(resumeText);
        if      (wc >= 150 && wc <= 1000) score += 10;
        else if (wc >= 80)                score += 5;

        // ── Contact info (10 pts) ─────────────────────────────────────────────
        if (TextUtils.containsPattern(resumeText, Constants.EMAIL_REGEX)) score += 5;
        if (TextUtils.containsPattern(resumeText, Constants.PHONE_REGEX)) score += 5;

        // ── Content parseable (10 pts) ────────────────────────────────────────
        if (wc > 50) score += 10;

        return new ATSResult(Math.min(score, 100), matched, missing, matchRatio);
    }

    public void populateATSData(ResumeReport report, ATSResult result) {
        report.atsScore           = result.getScore();
        report.matchedKeywords    = result.getMatchedKeywords();
        report.missingKeywords    = result.getMissingKeywords();
        report.keywordMatchRatio  = result.getMatchRatio();
        report.hasStandardHeadings = result.getScore() >= 25;
        report.hasDateFormats      = true;
    }

    private String[] getKeywordsForRole(String role) {
        for (Map.Entry<String, String[]> entry : Constants.ROLE_KEYWORDS.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(role)) return entry.getValue();
        }
        return Constants.ROLE_KEYWORDS.get("Android Developer");
    }
}
