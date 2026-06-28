package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.parsing.CvDTO;
import com.example.demo.service.ai.impl.ParsingAIclient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.service.ICvService;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cv")
public class CVController {

    private final ParsingAIclient parsingAIclient;
    private final ICvService cvService;

    public CVController(ParsingAIclient parsingAIclient, ICvService cvService) {
        this.parsingAIclient = parsingAIclient;
        this.cvService = cvService;
    }

    @PostMapping(value = "/parse", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadCV(@RequestPart("file") MultipartFile file,
                           @RequestParam Long userId) throws IOException {

        CvDTO parsedCv = parsingAIclient.parseCV(file);
        cvService.saveCv(userId, parsedCv);

        return "CV uploaded and saved successfully";
    }



    @GetMapping()
    public ResponseEntity<CvDTO> getUserCvById(@RequestParam Long userId) {
        CvDTO cv = cvService.getCvByUserId(userId);
        return ResponseEntity.ok(cv);
    }
}
