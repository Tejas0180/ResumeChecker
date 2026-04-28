package com.resumechecker.analyzer;

import com.resumechecker.model.ResumeSection;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SectionDetector {

    private static final String[] SECTION_HEADERS = {
        "experience", "work experience", "professional experience", "employment",
        "education", "academic background", "qualifications",
        "skills", "technical skills", "core competencies", "key skills",
        "projects", "personal projects", "academic projects",
        "certifications", "certificates", "achievements", "awards",
        "summary", "profile", "objective", "about me",
        "internships", "volunteering", "extracurricular"
    };

    public List<ResumeSection> detect(String resumeText) {
        List<ResumeSection> sections = new ArrayList<>();
        String[] lines = resumeText.split("\n");

        String currentSection = null;
        StringBuilder currentContent = new StringBuilder();

        for (String line : lines) {
            String trimmed = line.trim();
            String matched = matchesHeader(trimmed);

            if (matched != null) {
                if (currentSection != null) {
                    sections.add(new ResumeSection(currentSection, currentContent.toString().trim()));
                }
                currentSection = matched;
                currentContent = new StringBuilder();
            } else if (currentSection != null) {
                currentContent.append(trimmed).append("\n");
            }
        }

        if (currentSection != null) {
            sections.add(new ResumeSection(currentSection, currentContent.toString().trim()));
        }

        return sections;
    }

    private String matchesHeader(String line) {
        if (line.isEmpty() || line.length() > 50) return null;
        String lower = line.toLowerCase().replaceAll("[^a-z\\s]", "").trim();
        for (String header : SECTION_HEADERS) {
            if (lower.equals(header) || lower.startsWith(header)) {
                return capitalize(header);
            }
        }
        return null;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
