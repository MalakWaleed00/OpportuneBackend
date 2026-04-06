package com.example.demo.domain.dto.job;
import java.util.List;
public class JobResponseDTO {
    private String jobTitle;
    private List<String> contributingSkills;
    private List<JobDetails> jobLinks;

    public JobResponseDTO(String jobTitle, List<String> contributingSkills,List<JobDetails> jobLinks) {
        this.jobTitle = jobTitle;
        this.contributingSkills = contributingSkills;
        this.jobLinks=jobLinks;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public List<String> getContributingSkills() {
        return contributingSkills;
    }

    public List<JobDetails> getJobLinks() {
        return jobLinks;
    }
}
