package com.resumechecker.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    public static int wordCount(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        return text.trim().split("\\s+").length;
    }

    public static boolean containsPattern(String text, String regex) {
        if (text == null) return false;
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return p.matcher(text).find();
    }

    public static int countOccurrences(String text, String[] terms) {
        if (text == null) return 0;
        int count = 0;
        String lower = text.toLowerCase();
        for (String term : terms) {
            if (lower.contains(term.toLowerCase())) count++;
        }
        return count;
    }

    public static boolean hasNumbers(String text) {
        if (text == null) return false;
        return Pattern.compile("\\b\\d+[%+]?\\b").matcher(text).find();
    }

    public static String cleanText(String text) {
        if (text == null) return "";
        return text.replaceAll("\\s+", " ").trim();
    }

    public static String getScoreLabel(int score) {
        if (score >= Constants.SCORE_EXCELLENT) return "Excellent";
        if (score >= Constants.SCORE_GOOD)      return "Good";
        if (score >= Constants.SCORE_AVERAGE)   return "Average";
        return "Needs Work";
    }

    public static int getScoreColor(int score) {
        if (score >= Constants.SCORE_EXCELLENT) return 0xFF4CAF50; // green
        if (score >= Constants.SCORE_GOOD)      return 0xFF2196F3; // blue
        if (score >= Constants.SCORE_AVERAGE)   return 0xFFFF9800; // orange
        return 0xFFF44336;                                          // red
    }
}
