package com.example.demo.service.ai;

import com.example.demo.domain.dto.parsing.CvDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ParsingModel {
    public CvDTO parseCV(MultipartFile file);
}
