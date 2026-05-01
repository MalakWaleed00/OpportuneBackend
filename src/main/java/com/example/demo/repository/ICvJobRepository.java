package com.example.demo.repository;

import com.example.demo.southbound.entity.CvJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICvJobRepository extends JpaRepository<CvJob, Long> {
    List<CvJob> findByCvId(Long cvId);
}
