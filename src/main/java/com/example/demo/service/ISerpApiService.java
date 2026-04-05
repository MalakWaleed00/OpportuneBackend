package com.example.demo.service;
import java.util.List;
public interface ISerpApiService {
    List<String> searchJobs(String jobTitle, List<String> skills);
}

