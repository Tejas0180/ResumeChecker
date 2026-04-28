package com.resumechecker.model;

public class Suggestion {

    public enum Priority { HIGH, MEDIUM, LOW }
    public enum Type { STRUCTURE, KEYWORDS, CONTENT, ATS, FORMAT }

    private String title;
    private String message;
    private Priority priority;
    private Type type;

    public Suggestion(String title, String message, Priority priority, Type type) {
        this.title = title;
        this.message = message;
        this.priority = priority;
        this.type = type;
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public Priority getPriority() { return priority; }
    public Type getType() { return type; }
}
