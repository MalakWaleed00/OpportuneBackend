package com.example.demo.domain.dto.job;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDetails {
    private Long id;
    private String title;
    private String companyName;
    private String location;
    private String via;
    private String shareLink;
    private String thumbnail;
    private List<String> extensions;
    private String description;
    private String link;
    private List<ApplyOptionDTO> applyOptions;

    public JobDetails(String title, String company, String location, String via, String shareLink, String thumbnail, List<String> extensions, String description, List<ApplyOptionDTO> applyOptions) {
        this.title = title;
        this.companyName = company;
        this.location = location;
        this.via = via;
        this.shareLink = shareLink;
        this.thumbnail = thumbnail;
        this.extensions = extensions;
        this.description = description;
        this.applyOptions = applyOptions;
    }
}