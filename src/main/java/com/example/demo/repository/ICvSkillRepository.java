package com.example.demo.repository;

import com.example.demo.southbound.entity.CvSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICvSkillRepository extends JpaRepository<CvSkill, Long> {
    List<CvSkill> findByCvId(Long cvId);
}
