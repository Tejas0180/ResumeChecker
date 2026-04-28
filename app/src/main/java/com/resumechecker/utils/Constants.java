package com.resumechecker.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    // ─── Action Verbs ───────────────────────────────────────────────────────────
    public static final String[] ACTION_VERBS = {
        "achieved", "built", "designed", "developed", "engineered", "implemented",
        "improved", "led", "managed", "optimized", "created", "delivered",
        "established", "executed", "generated", "increased", "launched", "reduced",
        "resolved", "spearheaded", "streamlined", "transformed", "upgraded",
        "collaborated", "coordinated", "trained", "mentored", "analyzed", "deployed",
        "automated", "configured", "maintained", "migrated", "integrated", "tested",
        "built", "shipped", "fixed", "refactored", "documented", "reviewed",
        "researched", "presented", "published", "contributed", "handled", "worked"
    };

    // ─── Buzzwords to flag (relaxed – only truly useless ones) ──────────────────
    public static final String[] BUZZWORDS = {
        "guru", "ninja", "rockstar", "wizard",
        "think outside the box", "synergy", "go-getter"
    };

    // ─── Standard ATS section headings ──────────────────────────────────────────
    public static final String[] ATS_HEADINGS = {
        "experience", "education", "skills", "projects",
        "certifications", "achievements", "summary", "objective",
        "work", "internship", "training", "profile", "about"
    };

    // ─── Technology synonym groups – any one match counts for the group ─────────
    // Format: canonical_name -> synonyms (all lowercase, any match = found)
    public static final Map<String, String[]> TECH_SYNONYMS = new HashMap<String, String[]>() {{
        put("git",           new String[]{"git", "github", "gitlab", "version control", "bitbucket"});
        put("api",           new String[]{"api", "rest", "rest api", "restful", "http", "web service", "endpoint"});
        put("sql",           new String[]{"sql", "mysql", "postgresql", "sqlite", "database", "rdbms", "oracle"});
        put("python",        new String[]{"python", "py", "django", "flask", "fastapi"});
        put("javascript",    new String[]{"javascript", "js", "ecmascript", "es6", "es2015"});
        put("java",          new String[]{"java", "jvm", "spring", "spring boot"});
        put("docker",        new String[]{"docker", "container", "containerization", "dockerfile"});
        put("aws",           new String[]{"aws", "amazon web services", "ec2", "s3", "lambda", "cloud"});
        put("machine learning", new String[]{"machine learning", "ml", "ai", "artificial intelligence", "deep learning"});
        put("react",         new String[]{"react", "reactjs", "react.js", "react native"});
        put("node",          new String[]{"node", "nodejs", "node.js", "express", "expressjs"});
        put("kotlin",        new String[]{"kotlin", "kts"});
        put("android",       new String[]{"android", "android studio", "android sdk"});
        put("firebase",      new String[]{"firebase", "firestore", "realtime database", "fcm"});
        put("testing",       new String[]{"test", "testing", "junit", "espresso", "unit test", "qa", "tdd"});
        put("agile",         new String[]{"agile", "scrum", "kanban", "sprint", "jira"});
        put("ci/cd",         new String[]{"ci/cd", "ci cd", "continuous integration", "continuous delivery", "jenkins", "github actions", "gitlab ci"});
        put("linux",         new String[]{"linux", "unix", "bash", "shell", "terminal", "command line"});
    }};

    // ─── Role keywords database ──────────────────────────────────────────────────
    // Simpler, broader terms so more resumes match naturally
    public static final Map<String, String[]> ROLE_KEYWORDS = new HashMap<String, String[]>() {{

        put("Android Developer", new String[]{
            "android", "kotlin", "java", "xml", "jetpack", "mvvm",
            "retrofit", "room", "firebase", "gradle", "api",
            "material design", "git", "testing", "compose"
        });

        put("Web Developer", new String[]{
            "html", "css", "javascript", "react", "angular", "vue",
            "node", "mongodb", "sql", "git", "api",
            "responsive", "typescript", "bootstrap", "agile"
        });

        put("Data Science", new String[]{
            "python", "machine learning", "sql", "data analysis",
            "statistics", "pandas", "numpy", "jupyter",
            "visualization", "tensorflow", "git", "r"
        });

        put("Backend Developer", new String[]{
            "java", "python", "api", "docker", "sql",
            "git", "linux", "authentication", "microservices", "ci/cd"
        });

        put("UI/UX Designer", new String[]{
            "figma", "prototyping", "wireframing",
            "user research", "usability", "design system",
            "typography", "interaction design", "accessibility", "adobe"
        });

        put("DevOps Engineer", new String[]{
            "docker", "kubernetes", "aws", "ci/cd", "linux",
            "terraform", "ansible", "monitoring", "git", "python"
        });

        put("Full Stack Developer", new String[]{
            "javascript", "react", "node", "html", "css",
            "sql", "api", "git", "docker", "agile"
        });

        put("Machine Learning Engineer", new String[]{
            "python", "machine learning", "tensorflow", "sql",
            "docker", "git", "statistics", "data pipeline", "nlp"
        });
    }};

    // ─── Date format regex ───────────────────────────────────────────────────────
    public static final String DATE_REGEX =
        "(?i)(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]*\\.?\\s*\\d{4}" +
        "|\\d{1,2}/\\d{4}" +
        "|\\d{4}\\s*[-–]\\s*(\\d{4}|present|current|now)";

    // ─── Contact regexes ─────────────────────────────────────────────────────────
    public static final String EMAIL_REGEX   = "[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}";
    public static final String PHONE_REGEX   = "(?:\\+?\\d[\\s\\-.]?){9,14}\\d";
    public static final String LINKEDIN_REGEX= "(?i)linkedin\\.com/in/[\\w\\-]+";
    public static final String GITHUB_REGEX  = "(?i)github\\.com/[\\w\\-]+";

    // ─── Intent keys ────────────────────────────────────────────────────────────
    public static final String KEY_REPORT_ID = "report_id";
    public static final String KEY_PDF_URI   = "pdf_uri";

    // ─── Score thresholds ────────────────────────────────────────────────────────
    public static final int SCORE_EXCELLENT = 75;
    public static final int SCORE_GOOD      = 55;
    public static final int SCORE_AVERAGE   = 35;
}
