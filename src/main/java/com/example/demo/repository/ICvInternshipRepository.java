package com.example.demo.repository;

import com.example.demo.southbound.entity.CvInternship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICvInternshipRepository extends JpaRepository<CvInternship, Long> {
    List<CvInternship> findByCvId(Long cvId);
}
