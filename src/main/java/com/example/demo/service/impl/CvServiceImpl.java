package com.example.demo.service.impl;

import com.example.demo.domain.dto.parsing.*;
import com.example.demo.domain.dto.parsing.InternshipDTO;
import com.example.demo.domain.dto.parsing.JobDTO;
import com.example.demo.domain.dto.parsing.ProjectDTO;
import com.example.demo.service.ICvService;
import com.example.demo.southbound.entity.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CvServiceImpl implements ICvService {

    private final ICvRepository cvRepository;
    private final ICvSkillRepository skillRepository;
    private final ICvEducationRepository educationRepository;
    private final ICvJobRepository jobRepository;
    private final ICvProjectRepository projectRepository;
    private final IProjectTechnologyRepository techRepository;
    private final ICvInternshipRepository internshipRepository;

    private final IUserRepository userRepository;

    @Transactional
    public void saveCv(Long userId, CvDTO dto) {

        // Delete any existing CV rows for this user before saving the new one
        cvRepository.findAllByUserId(userId).forEach(existing -> {
            Long oldId = existing.getId();
            skillRepository.deleteAll(skillRepository.findByCvId(oldId));
            educationRepository.deleteAll(educationRepository.findByCvId(oldId));
            jobRepository.deleteAll(jobRepository.findByCvId(oldId));
            projectRepository.findByCvId(oldId).forEach(p ->
                techRepository.deleteAll(techRepository.findByProjectId(p.getId())));
            projectRepository.deleteAll(projectRepository.findByCvId(oldId));
            internshipRepository.deleteAll(internshipRepository.findByCvId(oldId));
            cvRepository.delete(existing);
        });

        Cv cv = Cv.builder()
                .userId(userId)
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .experienceLevel(dto.getExperienceLevel())
                .build();

        cv = cvRepository.save(cv);

        Long cvId = cv.getId();

        // Skills
        Optional.ofNullable(dto.getSkills()).orElse(Collections.emptyList())
                .forEach(skill -> skillRepository.save(CvSkill.builder()
                        .cvId(cvId).skill(skill).build()));

        // Education
        Optional.ofNullable(dto.getEducation()).orElse(Collections.emptyList())
                .forEach(e -> educationRepository.save(CvEducation.builder()
                        .cvId(cvId).degree(e.getDegree())
                        .institution(e.getInstitution()).year(e.getYear()).build()));

        // Jobs
        Optional.ofNullable(dto.getJobs()).orElse(Collections.emptyList())
                .forEach(j -> jobRepository.save(CvJob.builder()
                        .cvId(cvId).title(j.getTitle())
                        .company(j.getCompany()).summary(j.getSummary()).build()));

        // Projects + technologies
        Optional.ofNullable(dto.getProjects()).orElse(Collections.emptyList())
                .forEach(p -> {
                    CvProject project = projectRepository.save(CvProject.builder()
                            .cvId(cvId).name(p.getName()).description(p.getDescription()).build());

                    Optional.ofNullable(p.getTechnologies()).orElse(Collections.emptyList())
                            .forEach(t -> techRepository.save(ProjectTechnology.builder()
                                    .projectId(project.getId()).technology(t).build()));
                });

        // Internships
        Optional.ofNullable(dto.getInternships()).orElse(Collections.emptyList())
                .forEach(i -> internshipRepository.save(CvInternship.builder()
                        .cvId(cvId).title(i.getTitle()).company(i.getCompany())
                        .duration(i.getDuration()).summary(i.getSummary()).build()));
    }



    public CvDTO getCvByUserId(Long userId) {

        // 1. Get main CV
        Cv cv = cvRepository.findFirstByUserIdOrderByIdDesc(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CV not found for user " + userId));

        Long cvId = cv.getId();

        // 2. Load all child tables
        List<String> skills = skillRepository.findByCvId(cvId)
                .stream().map(CvSkill::getSkill).toList();

        List<EducationDTO> education = educationRepository.findByCvId(cvId)
                .stream().map(e -> {
                    EducationDTO dto = new EducationDTO();
                    dto.setDegree(e.getDegree());
                    dto.setInstitution(e.getInstitution());
                    dto.setYear(e.getYear());
                    return dto;
                }).toList();

        List<JobDTO> jobs = jobRepository.findByCvId(cvId)
                .stream().map(j -> {
                    JobDTO dto = new JobDTO();
                    dto.setTitle(j.getTitle());
                    dto.setCompany(j.getCompany());
                    dto.setSummary(j.getSummary());
                    return dto;
                }).toList();

        List<ProjectDTO> projects = projectRepository.findByCvId(cvId)
                .stream().map(p -> {
                    ProjectDTO dto = new ProjectDTO();
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());

                    List<String> techs = techRepository.findByProjectId(p.getId())
                            .stream()
                            .map(ProjectTechnology::getTechnology)
                            .toList();

                    dto.setTechnologies(techs);
                    return dto;
                }).toList();

        List<InternshipDTO> internships = internshipRepository.findByCvId(cvId)
                .stream().map(i -> {
                    InternshipDTO dto = new InternshipDTO();
                    dto.setTitle(i.getTitle());
                    dto.setCompany(i.getCompany());
                    dto.setDuration(i.getDuration());
                    dto.setSummary(i.getSummary());
                    return dto;
                }).toList();

        // 3. Build final DTO
        CvDTO dto = new CvDTO();
        dto.setName(cv.getName());
        dto.setEmail(cv.getEmail());
        dto.setPhone(cv.getPhone());
        dto.setExperienceLevel(cv.getExperienceLevel());

        dto.setSkills(skills);
        dto.setEducation(education);
        dto.setJobs(jobs);
        dto.setProjects(projects);
        dto.setInternships(internships);

        return dto;
    }
}
