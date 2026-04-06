package com.example.demo.domain.dto.job;

import java.util.List;
import  com.example.demo.domain.dto.job.ApplyOptionDTO;
public class JobDetails {

    private String title;
    private String companyName;
    private String location;
    private String via;
    private String shareLink;
    private String thumbnail;
    private List<String> extensions;
    private String description;
    private List<ApplyOptionDTO> applyOptions;

    public JobDetails(String title, String companyName, String location,
                      String via, String shareLink, String thumbnail,
                      List<String> extensions, String description,
                      List<ApplyOptionDTO> applyOptions) {

        this.title = title;
        this.companyName = companyName;
        this.location = location;
        this.via = via;
        this.shareLink = shareLink;
        this.thumbnail = thumbnail;
        this.extensions = extensions;
        this.description = description;
        this.applyOptions = applyOptions;
    }

    public String getTitle() {
        return title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    public String getVia() {
        return via;
    }

    public String getShareLink() {
        return shareLink;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public String getDescription() {
        return description;
    }

    public List<ApplyOptionDTO> getApplyOptions() {
        return applyOptions;
    }
}

