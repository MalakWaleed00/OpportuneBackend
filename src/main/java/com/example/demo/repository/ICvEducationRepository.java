package com.example.demo.repository;

import com.example.demo.southbound.entity.CvEducation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICvEducationRepository extends JpaRepository<CvEducation, Long> {
    List<CvEducation> findByCvId(Long cvId);
}