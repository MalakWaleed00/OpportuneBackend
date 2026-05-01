package com.example.demo.domain.dto.parsing;

public class Job {

    private String title;
    private String company;
    private String summary;

    public Job() {}

    public Job(String title, String company, String summary) {
        this.title = title;
        this.company = company;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
