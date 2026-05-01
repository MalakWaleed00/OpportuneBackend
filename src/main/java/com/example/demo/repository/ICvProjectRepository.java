package com.example.demo.repository;

import com.example.demo.southbound.entity.CvProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICvProjectRepository extends JpaRepository<CvProject, Long> {
    List<CvProject> findByCvId(Long cvId);
}
