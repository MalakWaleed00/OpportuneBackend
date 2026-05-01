package com.example.demo.southbound.mapper;

import com.example.demo.domain.dto.job.JobDetails;
import com.example.demo.domain.dto.parsing.JobDTO;
import com.example.demo.southbound.entity.CvJob;
import com.example.demo.southbound.entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobMapper {

    @Mapping(source = "company", target = "companyName")
    JobDetails toDTO(Job job);

    @Mapping(source = "companyName", target = "company")
    Job toEntity(JobDetails dto);

}