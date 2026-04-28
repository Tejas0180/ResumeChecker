package com.resumechecker.analyzer;

import com.resumechecker.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Matches role keywords against resume text using synonym groups.
 * A keyword is considered matched if ANY of its synonyms appear in the resume.
 */
public class KeywordMatcher {

    public static String[] getKeywordsForRole(String role) {
        for (Map.Entry<String, String[]> entry : Constants.ROLE_KEYWORDS.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(role)) return entry.getValue();
        }
        return Constants.ROLE_KEYWORDS.get("Android Developer");
    }

    /**
     * Check if a keyword (or any of its synonyms) is present in the resume text.
     */
    public static boolean isKeywordPresent(String resumeLower, String keyword) {
        // Direct match first
        if (resumeLower.contains(keyword.toLowerCase())) return true;

        // Check synonym groups
        String kwLower = keyword.toLowerCase();
        for (Map.Entry<String, String[]> entry : Constants.TECH_SYNONYMS.entrySet()) {
            String canonical = entry.getKey();
            String[] synonyms = entry.getValue();
            // If this keyword maps to a synonym group
            boolean belongsToGroup = canonical.equalsIgnoreCase(kwLower);
            if (!belongsToGroup) {
                for (String syn : synonyms) {
                    if (syn.equalsIgnoreCase(kwLower)) { belongsToGroup = true; break; }
                }
            }
            if (belongsToGroup) {
                // Check if any synonym in this group is in the resume
                for (String syn : synonyms) {
                    if (resumeLower.contains(syn.toLowerCase())) return true;
                }
            }
        }
        return false;
    }

    public static List<String> getMatchedKeywords(String resumeText, String role) {
        List<String> matched = new ArrayList<>();
        String lower = resumeText.toLowerCase();
        for (String kw : getKeywordsForRole(role)) {
            if (isKeywordPresent(lower, kw)) matched.add(kw);
        }
        return matched;
    }

    public static List<String> getMissingKeywords(String resumeText, String role) {
        List<String> missing = new ArrayList<>();
        String lower = resumeText.toLowerCase();
        for (String kw : getKeywordsForRole(role)) {
            if (!isKeywordPresent(lower, kw)) missing.add(kw);
        }
        return missing;
    }

    public static float getMatchRatio(String resumeText, String role) {
        String[] keywords = getKeywordsForRole(role);
        if (keywords.length == 0) return 0f;
        return (float) getMatchedKeywords(resumeText, role).size() / keywords.length;
    }

    public static List<String> getAllRoles() {
        return new ArrayList<>(Constants.ROLE_KEYWORDS.keySet());
    }
}
