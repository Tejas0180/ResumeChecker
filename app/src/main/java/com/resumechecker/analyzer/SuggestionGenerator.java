package com.resumechecker.analyzer;

import com.resumechecker.model.ResumeReport;
import com.resumechecker.model.Suggestion;

import java.util.ArrayList;
import java.util.List;

public class SuggestionGenerator {

    public List<Suggestion> generate(ResumeReport report) {
        List<Suggestion> list = new ArrayList<>();

        // ── Contact & Profile ────────────────────────────────────────────────
        if (!report.hasEmail) {
            list.add(new Suggestion(
                "Add Email Address",
                "Your resume is missing an email address. ATS systems and recruiters need this to contact you.",
                Suggestion.Priority.HIGH, Suggestion.Type.STRUCTURE));
        }
        if (!report.hasPhone) {
            list.add(new Suggestion(
                "Add Phone Number",
                "Include a phone number so recruiters can reach you quickly.",
                Suggestion.Priority.HIGH, Suggestion.Type.STRUCTURE));
        }
        if (!report.hasLinkedIn) {
            list.add(new Suggestion(
                "Add LinkedIn Profile",
                "A LinkedIn URL boosts credibility and gives recruiters more context about your background.",
                Suggestion.Priority.MEDIUM, Suggestion.Type.STRUCTURE));
        }
        if (!report.hasGitHub) {
            list.add(new Suggestion(
                "Add GitHub / Portfolio",
                "For tech roles, a GitHub link demonstrates real skills. Add it to your contact section.",
                Suggestion.Priority.MEDIUM, Suggestion.Type.STRUCTURE));
        }

        // ── Word Count ───────────────────────────────────────────────────────
        if (!report.wordCountOk) {
            if (report.wordCount < 250) {
                list.add(new Suggestion(
                    "Resume Too Short",
                    "Your resume has only " + report.wordCount + " words. Aim for 300–700 words to give enough context about your experience.",
                    Suggestion.Priority.HIGH, Suggestion.Type.CONTENT));
            } else {
                list.add(new Suggestion(
                    "Resume Too Long",
                    "Your resume has " + report.wordCount + " words. For freshers, keep it to 1 page (~300–700 words). Remove redundant sections.",
                    Suggestion.Priority.MEDIUM, Suggestion.Type.CONTENT));
            }
        }

        // ── Action Verbs ─────────────────────────────────────────────────────
        if (report.actionVerbCount < 5) {
            list.add(new Suggestion(
                "Use More Action Verbs",
                "You have only " + report.actionVerbCount + " action verbs. Use words like 'built', 'led', 'designed', 'optimized' to describe your work powerfully.",
                Suggestion.Priority.HIGH, Suggestion.Type.CONTENT));
        }

        // ── Sections ─────────────────────────────────────────────────────────
        if (report.sectionsFound < 3) {
            list.add(new Suggestion(
                "Add More Sections",
                "Only " + report.sectionsFound + " sections detected. A strong resume has: Summary, Experience/Projects, Education, Skills, and Certifications.",
                Suggestion.Priority.HIGH, Suggestion.Type.STRUCTURE));
        }

        // ── Buzzwords ────────────────────────────────────────────────────────
        if (report.hasBuzzwords) {
            list.add(new Suggestion(
                "Remove Filler Words",
                "Words like 'hardworking', 'team player', or 'passionate' are vague and ignored by recruiters. Replace them with specific achievements.",
                Suggestion.Priority.MEDIUM, Suggestion.Type.CONTENT));
        }

        // ── Quantified Impact ────────────────────────────────────────────────
        if (!report.hasQuantifiedImpact) {
            list.add(new Suggestion(
                "Quantify Your Impact",
                "Add numbers to your bullet points. E.g., 'Improved app load time by 40%' or 'Managed a team of 5 developers'.",
                Suggestion.Priority.HIGH, Suggestion.Type.CONTENT));
        }

        // ── ATS Keyword Gap ──────────────────────────────────────────────────
        if (report.missingKeywords != null && report.missingKeywords.size() > 5) {
            int missing = report.missingKeywords.size();
            list.add(new Suggestion(
                "Add Missing Skills",
                missing + " skills from your target role are missing. Go to 'ATS Detail' to see which ones to add.",
                Suggestion.Priority.HIGH, Suggestion.Type.ATS));
        }

        if (report.keywordMatchRatio < 0.4f) {
            list.add(new Suggestion(
                "Low Role Skills Match",
                "Only " + (int)(report.keywordMatchRatio * 100) + "% of required skills are in your resume. Tailor it specifically for your target job.",
                Suggestion.Priority.HIGH, Suggestion.Type.ATS));
        }

        // ── ATS Headings ─────────────────────────────────────────────────────
        if (!report.hasStandardHeadings) {
            list.add(new Suggestion(
                "Use Standard Section Headings",
                "Use exact headings like 'Experience', 'Education', 'Skills'. Creative names confuse ATS parsers.",
                Suggestion.Priority.MEDIUM, Suggestion.Type.ATS));
        }

        // ── Date Format ──────────────────────────────────────────────────────
        if (!report.hasDateFormats) {
            list.add(new Suggestion(
                "Add Dates to Sections",
                "Include dates in 'Jan 2023 – Present' or 'MM/YYYY' format for all experience and education entries.",
                Suggestion.Priority.MEDIUM, Suggestion.Type.FORMAT));
        }

        // ── Positive reinforcement ───────────────────────────────────────────
        if (list.isEmpty()) {
            list.add(new Suggestion(
                "Great Resume!",
                "Your resume looks strong across all checked dimensions. Keep tailoring your skills section for each job application.",
                Suggestion.Priority.LOW, Suggestion.Type.CONTENT));
        }

        return list;
    }

    public List<String> generateAsStrings(ResumeReport report) {
        List<Suggestion> suggestions = generate(report);
        List<String> result = new ArrayList<>();
        for (Suggestion s : suggestions) {
            result.add(s.getTitle() + ": " + s.getMessage());
        }
        return result;
    }
}
