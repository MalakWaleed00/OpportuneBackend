package com.example.demo.repository;

import com.example.demo.southbound.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IJobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findByRecruiterIdOrderByPostedAtDesc(Long recruiterId);
}