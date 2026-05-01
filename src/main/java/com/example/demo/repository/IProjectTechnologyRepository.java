package com.example.demo.repository;

import com.example.demo.southbound.entity.ProjectTechnology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProjectTechnologyRepository extends JpaRepository<ProjectTechnology, Long> {
    List<ProjectTechnology> findByProjectId(Long projectId);
}
