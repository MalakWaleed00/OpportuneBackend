package com.example.demo.service.ai;

import com.example.demo.domain.dto.parsing.CandidateProfile;
import org.springframework.web.multipart.MultipartFile;

public interface ParsingModel {
    public CandidateProfile parseCV(MultipartFile file);
}
