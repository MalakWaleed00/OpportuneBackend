package com.example.demo.service.impl;

import com.example.demo.domain.dto.job.RecommendedJobDTO;
import com.example.demo.repository.IJobRepository;
import com.example.demo.service.ai.JobRecommendationModel;
import com.example.demo.southbound.entity.Job;
import com.example.demo.southbound.entity.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobRecommendationService {

    private final JobRecommendationModel model;
    private final IJobRepository jobRepository;

    public List<String> getRecommendations(List<String> userSkills, String userExperience, int topK) {
        List<String> jobs = model.recommendJobs(userSkills, userExperience, topK);
        //List<Job> jobs = jobRepository.findAllById(jobIds);

//        return jobs.stream()
//                .map(this::toDTO)
//                .toList();
        return jobs;
    }


    private RecommendedJobDTO toDTO(Job job) {

        RecommendedJobDTO dto = new RecommendedJobDTO();

        dto.setJobId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        dto.setDescription(job.getDescription());
        dto.setMinSalary(job.getMinSalary());
        dto.setMaxSalary(job.getMaxSalary());

        dto.setSkills(
                job.getSkills()
                        .stream()
                        .map(Skill::getName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
