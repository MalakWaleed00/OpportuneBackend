package com.example.demo.domain.dto.job;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JobRequestDTO {
    @JsonProperty("skills")

    private List<String> skills;
    @JsonProperty("experience")
    private String experience;
    @JsonProperty("topK")
    private int topK;

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getTopK() {
        return topK;
    }

    public void setTopK(int topK) {
        this.topK = topK;
    }
}