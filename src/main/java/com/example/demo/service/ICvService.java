package com.example.demo.service;

import com.example.demo.domain.dto.parsing.CvDTO;

public interface ICvService {
    public void saveCv(Long userId, CvDTO dto);
    public CvDTO getCvByUserId(Long userId);
    }
