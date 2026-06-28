package com.example.demo.service.impl;

import com.example.demo.domain.dto.course.*;
import com.example.demo.domain.dto.parsing.CvDTO;
import com.example.demo.repository.*;
import com.example.demo.service.ICourseRecommendationService;
import com.example.demo.service.ICvService;
import com.example.demo.service.ai.CourseRecommendationModel;
import com.example.demo.service.ai.impl.ParsingAIclient;
import com.example.demo.southbound.entity.Course;
import com.example.demo.southbound.entity.CourseRecommendation;
import com.example.demo.southbound.entity.JobRecommendation;
import com.example.demo.southbound.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseRecommendationServiceImpl implements ICourseRecommendationService {

    private final ICvService cvService;
    private final ParsingAIclient parsingAIclient;
    private final CourseRecommendationModel courseRecommendationModel;
    private final IJobRecommendationRepository jobRecommendationRepository;
    private final ICourseRepository courseRepository;
    private final ICourseRecommendationRepository courseRecommendationRepository;
    private final IUserRepository userRepository;

    @Override
    @Transactional
    public CourseRecommendationResponse recommend(CourseRecommendationControllerRequest request) {

        CvDTO cv = cvService.getCvByUserId(request.getUserId());

        List<String> cvSkillsLower = cv.getSkills().stream()
                .map(String::toLowerCase)
                .toList();

        List<JobRecommendation> jobRecommendations = jobRecommendationRepository.findByUserId(request.getUserId());
        if (jobRecommendations.isEmpty()) {
            throw new RuntimeException("No job recommendations found for user " + request.getUserId());
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getUserId()));

        List<JobCourseRecommendation> results = new ArrayList<>();

        for (JobRecommendation jr : jobRecommendations) {
            String description = jr.getJob().getDescription();
            if (description == null || description.isBlank()) continue;

            List<String> jdSkills = parsingAIclient.parseJobDescriptionSkills(description);

            if (jdSkills == null || jdSkills.isEmpty()) {
                jdSkills = jr.getJob().getSkills().stream()
                        .map(s -> s.getName())
                        .toList();
            }

            if (jdSkills.isEmpty()) continue;

            List<String> missingSkills = new ArrayList<>();
            for (String skill : jdSkills) {
                if (!cvSkillsLower.contains(skill.toLowerCase())) {
                    missingSkills.add(skill);
                }
            }

            if (missingSkills.isEmpty()) continue;

            CourseRecommendationRequest modelRequest = new CourseRecommendationRequest();
            modelRequest.setMissingSkills(missingSkills);
            modelRequest.setExperienceLevel(cv.getExperienceLevel());
            modelRequest.setTopK(request.getTopK());

            List<CourseDTO> courses = courseRecommendationModel.recommendCourses(modelRequest);

            // persist recommendations, reusing existing Course rows when possible
            courseRecommendationRepository.deleteByUserIdAndJobId(user.getId(), jr.getJob().getId());

            LocalDateTime now = LocalDateTime.now();
            for (CourseDTO dto : courses) {
                Course course = courseRepository
                        .findByCourseNameAndUrl(dto.getCourseName(), dto.getUrl())
                        .orElseGet(() -> courseRepository.save(Course.builder()
                                .courseName(dto.getCourseName())
                                .url(dto.getUrl())
                                .difficulty(dto.getDifficulty())
                                .rating(dto.getRating())
                                .build()));

                courseRecommendationRepository.save(CourseRecommendation.builder()
                        .user(user)
                        .job(jr.getJob())
                        .course(course)
                        .missingSkill(dto.getMissingSkill())
                        .matchedSkill(dto.getMatchedSkill())
                        .matchScore(dto.getMatchScore())
                        .recommendedAt(now)
                        .build());
            }

            JobCourseRecommendation jobResult = new JobCourseRecommendation();
            jobResult.setJobId(jr.getJob().getId());
            jobResult.setJobTitle(jr.getJob().getTitle());
            jobResult.setMissingSkills(missingSkills);
            jobResult.setRecommendations(courses);

            results.add(jobResult);
        }

        CourseRecommendationResponse response = new CourseRecommendationResponse();
        response.setJobRecommendations(results);
        return response;
    }
}