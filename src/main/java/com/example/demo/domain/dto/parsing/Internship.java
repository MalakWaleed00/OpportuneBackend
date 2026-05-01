package com.example.demo.domain.dto.parsing;

public class Internship {

    private String title;
    private String company;
    private String duration;
    private String summary;

    public Internship() {}

    public Internship(String title, String company, String duration, String summary) {
        this.title = title;
        this.company = company;
        this.duration = duration;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
