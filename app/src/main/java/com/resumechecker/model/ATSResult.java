package com.resumechecker.model;

import java.util.List;

public class ATSResult {

    private int score;
    private List<String> matchedKeywords;
    private List<String> missingKeywords;
    private float matchRatio;

    public ATSResult(int score, List<String> matchedKeywords,
                     List<String> missingKeywords, float matchRatio) {
        this.score = score;
        this.matchedKeywords = matchedKeywords;
        this.missingKeywords = missingKeywords;
        this.matchRatio = matchRatio;
    }

    public int getScore() { return score; }
    public List<String> getMatchedKeywords() { return matchedKeywords; }
    public List<String> getMissingKeywords() { return missingKeywords; }
    public float getMatchRatio() { return matchRatio; }
}
