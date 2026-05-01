package com.example.demo.domain.dto.auth;

import com.example.demo.domain.dto.parsing.EducationDTO;
import com.example.demo.domain.dto.parsing.InternshipDTO;
import com.example.demo.domain.dto.parsing.JobDTO;
import com.example.demo.domain.dto.parsing.ProjectDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {

    private Long id;

    private String name;

    private String username;

    private String email;

    private String phone;

    private String experienceLevel;

    private List<String> skills;

    private List<EducationDTO> education;

    private List<JobDTO> jobs;

    private List<ProjectDTO> projects;

    private List<InternshipDTO> internships;

    private String profilePicLink;

    private String cvLink;
}
