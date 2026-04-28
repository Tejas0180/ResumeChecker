package com.resumechecker.analyzer;

import com.resumechecker.model.ResumeReport;
import com.resumechecker.model.ResumeSection;
import com.resumechecker.utils.Constants;
import com.resumechecker.utils.TextUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Scores a resume generously – rewards positives, minimal penalties.
 */
public class ScoreEngine {

    public void populateResumeScore(ResumeReport report, String resumeText, List<ResumeSection> sections) {
        int score = 0;
        String lower = resumeText.toLowerCase();

        // ── Contact info (up to 30 pts) ──────────────────────────────────────
        report.hasEmail    = TextUtils.containsPattern(resumeText, Constants.EMAIL_REGEX);
        report.hasPhone    = TextUtils.containsPattern(resumeText, Constants.PHONE_REGEX);
        report.hasLinkedIn = TextUtils.containsPattern(resumeText, Constants.LINKEDIN_REGEX);
        report.hasGitHub   = TextUtils.containsPattern(resumeText, Constants.GITHUB_REGEX);

        if (report.hasEmail)    score += 10;
        if (report.hasPhone)    score += 10;
        if (report.hasLinkedIn) score += 5;
        if (report.hasGitHub)   score += 5;

        // ── Word count – relaxed range 150–1000 (10 pts) ─────────────────────
        report.wordCount   = TextUtils.wordCount(resumeText);
        report.wordCountOk = report.wordCount >= 150 && report.wordCount <= 1000;
        if (report.wordCountOk) score += 10;
        else if (report.wordCount >= 100) score += 5; // partial credit

        // ── Action verbs – lower bar (up to 20 pts) ──────────────────────────
        report.actionVerbCount = TextUtils.countOccurrences(lower, Constants.ACTION_VERBS);
        report.hasActionVerbs  = report.actionVerbCount >= 3;
        if      (report.actionVerbCount >= 8)  score += 20;
        else if (report.actionVerbCount >= 4)  score += 15;
        else if (report.actionVerbCount >= 2)  score += 10;
        else if (report.actionVerbCount >= 1)  score += 5;

        // ── Sections found (up to 20 pts) ─────────────────────────────────────
        report.sectionsFound = sections.size();
        report.hasSections   = sections.size() >= 2;
        if      (sections.size() >= 4) score += 20;
        else if (sections.size() >= 2) score += 14;
        else if (sections.size() >= 1) score += 7;

        // ── Buzzword check – only small penalty now ───────────────────────────
        report.hasBuzzwords = Arrays.stream(Constants.BUZZWORDS)
                .anyMatch(b -> lower.contains(b.toLowerCase()));
        if (!report.hasBuzzwords) score += 10;

        // ── Quantified impact (10 pts) ─────────────────────────────────────────
        report.hasQuantifiedImpact = TextUtils.hasNumbers(resumeText);
        if (report.hasQuantifiedImpact) score += 10;

        report.resumeScore = Math.min(score, 100);
    }
}
