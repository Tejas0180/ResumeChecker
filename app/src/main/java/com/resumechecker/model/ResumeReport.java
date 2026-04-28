package com.resumechecker.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.resumechecker.db.Converters;

import java.util.List;

@Entity(tableName = "resume_reports")
@TypeConverters(Converters.class)
public class ResumeReport {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fileName;
    public long timestamp;

    // Scores
    public int resumeScore;
    public int atsScore;

    // ATS data
    public String targetRole;
    public List<String> matchedKeywords;
    public List<String> missingKeywords;
    public float keywordMatchRatio;

    // Breakdown flags (for suggestions)
    public boolean hasEmail;
    public boolean hasPhone;
    public boolean hasLinkedIn;
    public boolean hasGitHub;
    public boolean wordCountOk;
    public boolean hasActionVerbs;
    public boolean hasSections;
    public boolean hasBuzzwords;
    public boolean hasQuantifiedImpact;
    public boolean hasStandardHeadings;
    public boolean hasDateFormats;

    public int sectionsFound;
    public int actionVerbCount;
    public int wordCount;

    // Suggestion list
    public List<String> suggestions;
}
