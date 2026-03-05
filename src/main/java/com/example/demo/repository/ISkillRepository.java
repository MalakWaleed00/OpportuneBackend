package com.example.demo.repository;

import com.example.demo.southbound.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findByName(String name);

    boolean existsByName(String name);

}
