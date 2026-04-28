package com.resumechecker.analyzer;

import com.resumechecker.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class BuzzwordDetector {

    public static List<String> detect(String resumeText) {
        List<String> found = new ArrayList<>();
        String lower = resumeText.toLowerCase();
        for (String buzz : Constants.BUZZWORDS) {
            if (lower.contains(buzz.toLowerCase())) {
                found.add(buzz);
            }
        }
        return found;
    }

    public static boolean hasBuzzwords(String resumeText) {
        return !detect(resumeText).isEmpty();
    }
}
