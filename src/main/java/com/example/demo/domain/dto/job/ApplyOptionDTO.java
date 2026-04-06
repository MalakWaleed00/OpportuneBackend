package com.example.demo.domain.dto.job;

public class ApplyOptionDTO {
    private String title;
    private String link;

    public ApplyOptionDTO(String title, String link) {
        this.title = title;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}