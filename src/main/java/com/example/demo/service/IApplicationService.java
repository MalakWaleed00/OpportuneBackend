package com.example.demo.service;

import com.example.demo.domain.dto.application.ApplicationAnalyticsDTO;
import com.example.demo.domain.dto.application.ApplicationRequestDTO;
import com.example.demo.domain.dto.application.ApplicationResponseDTO;

import java.util.List;

public interface IApplicationService {

    List<ApplicationResponseDTO> getMyApplications();

    ApplicationResponseDTO createApplication(ApplicationRequestDTO request);

    ApplicationResponseDTO updateApplication(Long id, ApplicationRequestDTO request);

    void deleteApplication(Long id);

    ApplicationAnalyticsDTO getMyAnalytics();
}