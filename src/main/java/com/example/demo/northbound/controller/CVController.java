package com.example.demo.northbound.controller;

import com.example.demo.domain.dto.parsing.CandidateProfile;
import com.example.demo.service.ai.impl.ParsingAIclient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/parse")
public class CVController {

    // 1. Make it 'final' so it can't be changed after initialization
    private final ParsingAIclient parsingAIclient;

    // 2. Create a constructor. Spring will automatically find the
    // ParsingAIclient bean and pass it in here.
    public CVController(ParsingAIclient parsingAIclient) {
        this.parsingAIclient = parsingAIclient;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CandidateProfile uploadCV(@RequestPart("file") MultipartFile file) {
        return parsingAIclient.parseCV(file);
    }
}
