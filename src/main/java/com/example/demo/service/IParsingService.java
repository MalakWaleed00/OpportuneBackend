package com.example.demo.service;

import com.example.demo.domain.dto.parsing.ParsingResponseDTO;

public interface IParsingService
{
    ParsingResponseDTO parse(String cvText, String jobDescription);
}
