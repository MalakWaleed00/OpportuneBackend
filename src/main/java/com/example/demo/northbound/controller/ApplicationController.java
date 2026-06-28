package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.application.ApplicationAnalyticsDTO;
import com.example.demo.domain.dto.application.ApplicationRequestDTO;
import com.example.demo.domain.dto.application.ApplicationResponseDTO;
import com.example.demo.service.IApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final IApplicationService applicationService;

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponseDTO>> getMyApplications() {
        return ResponseEntity.ok(applicationService.getMyApplications());
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> createApplication(@RequestBody ApplicationRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.createApplication(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateApplication(
            @PathVariable Long id,
            @RequestBody ApplicationRequestDTO request) {
        return ResponseEntity.ok(applicationService.updateApplication(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my/analytics")
    public ResponseEntity<ApplicationAnalyticsDTO> getMyAnalytics() {
        return ResponseEntity.ok(applicationService.getMyAnalytics());
    }
}