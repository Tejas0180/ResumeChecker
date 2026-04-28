package com.resumechecker.model;

public class ResumeSection {
    private String name;
    private String content;

    public ResumeSection(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getName() { return name; }
    public String getContent() { return content; }
}
