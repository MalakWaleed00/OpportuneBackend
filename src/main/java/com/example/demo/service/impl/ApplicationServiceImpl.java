package com.example.demo.service.impl;

import com.example.demo.common.util.IAuthUtils;
import com.example.demo.domain.dto.application.ApplicationAnalyticsDTO;
import com.example.demo.domain.dto.application.ApplicationRequestDTO;
import com.example.demo.domain.dto.application.ApplicationResponseDTO;
import com.example.demo.domain.dto.application.ApplicationSummaryDTO;
import com.example.demo.repository.IApplicationRepository;
import com.example.demo.service.IApplicationService;
import com.example.demo.southbound.Enum.ApplicationStatus;
import com.example.demo.southbound.entity.Application;
import com.example.demo.southbound.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements IApplicationService {

    private final IApplicationRepository applicationRepository;
    private final IAuthUtils authUtils;

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationResponseDTO> getMyApplications() {
        Long userId = authUtils.getCurrentUserId();
        return applicationRepository.findByUserIdOrderByAppliedDateDesc(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO request) {
        User currentUser = authUtils.getCurrentUser();

        Application application = Application.builder()
                .user(currentUser)
                .jobTitle(request.getJobTitle())
                .company(request.getCompany())
                .location(request.getLocation())
                .salary(request.getSalary())
                .appliedDate(request.getAppliedDate() != null ? request.getAppliedDate() : LocalDate.now())
                .status(request.getStatus() != null ? request.getStatus() : ApplicationStatus.APPLIED)
                .notes(request.getNotes())
                .build();

        return toResponse(applicationRepository.save(application));
    }

    @Override
    public ApplicationResponseDTO updateApplication(Long id, ApplicationRequestDTO request) {
        Application application = findAndVerifyOwner(id);

        if (request.getJobTitle() != null) application.setJobTitle(request.getJobTitle());
        if (request.getCompany() != null) application.setCompany(request.getCompany());
        if (request.getLocation() != null) application.setLocation(request.getLocation());
        if (request.getSalary() != null) application.setSalary(request.getSalary());
        if (request.getAppliedDate() != null) application.setAppliedDate(request.getAppliedDate());
        if (request.getStatus() != null) application.setStatus(request.getStatus());
        if (request.getNotes() != null) application.setNotes(request.getNotes());

        return toResponse(applicationRepository.save(application));
    }

    @Override
    public void deleteApplication(Long id) {
        Application application = findAndVerifyOwner(id);
        applicationRepository.delete(application);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationAnalyticsDTO getMyAnalytics() {
        Long userId = authUtils.getCurrentUserId();
        List<Application> applications = applicationRepository.findByUserIdOrderByAppliedDateDesc(userId);

        int total = applications.size();

        Map<ApplicationStatus, Long> statusBreakdown = applications.stream()
                .collect(Collectors.groupingBy(Application::getStatus, Collectors.counting()));

        int interviews = (int) (getCount(statusBreakdown, ApplicationStatus.INTERVIEW_SCHEDULED)
                + getCount(statusBreakdown, ApplicationStatus.INTERVIEW_DONE));
        int offers = (int) (getCount(statusBreakdown, ApplicationStatus.OFFER_RECEIVED)
                + getCount(statusBreakdown, ApplicationStatus.OFFER_ACCEPTED));
        int accepted = (int) getCount(statusBreakdown, ApplicationStatus.OFFER_ACCEPTED);
        int rejected = (int) getCount(statusBreakdown, ApplicationStatus.REJECTED);
        int withdrawn = (int) getCount(statusBreakdown, ApplicationStatus.WITHDRAWN);

        int active = (int) applications.stream()
                .filter(a -> a.getStatus() != ApplicationStatus.REJECTED
                        && a.getStatus() != ApplicationStatus.WITHDRAWN
                        && a.getStatus() != ApplicationStatus.OFFER_ACCEPTED
                        && a.getStatus() != ApplicationStatus.WISHLIST)
                .count();

        int interviewRate = total > 0 ? (int) Math.round((interviews * 100.0) / total) : 0;
        int offerRate = interviews > 0 ? (int) Math.round((offers * 100.0) / interviews) : 0;
        int successRate = total > 0 ? (int) Math.round((accepted * 100.0) / total) : 0;

        List<ApplicationSummaryDTO> recentApplications = applications.stream()
                .limit(5)
                .map(a -> ApplicationSummaryDTO.builder()
                        .id(a.getId())
                        .jobTitle(a.getJobTitle())
                        .company(a.getCompany())
                        .status(a.getStatus())
                        .appliedDate(a.getAppliedDate())
                        .build())
                .collect(Collectors.toList());

        return ApplicationAnalyticsDTO.builder()
                .total(total)
                .active(active)
                .interviews(interviews)
                .offers(offers)
                .accepted(accepted)
                .rejected(rejected)
                .withdrawn(withdrawn)
                .interviewRate(interviewRate)
                .offerRate(offerRate)
                .successRate(successRate)
                .statusBreakdown(statusBreakdown)
                .recentApplications(recentApplications)
                .build();
    }

    private Application findAndVerifyOwner(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

        Long currentUserId = authUtils.getCurrentUserId();
        if (!application.getUser().getId().equals(currentUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to modify this application");
        }

        return application;
    }

    private long getCount(Map<ApplicationStatus, Long> breakdown, ApplicationStatus status) {
        return breakdown.getOrDefault(status, 0L);
    }

    private ApplicationResponseDTO toResponse(Application app) {
        return ApplicationResponseDTO.builder()
                .id(app.getId())
                .userId(app.getUser().getId())
                .jobTitle(app.getJobTitle())
                .company(app.getCompany())
                .location(app.getLocation())
                .salary(app.getSalary())
                .appliedDate(app.getAppliedDate())
                .status(app.getStatus())
                .notes(app.getNotes())
                .build();
    }
}