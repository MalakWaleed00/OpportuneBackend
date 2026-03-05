package com.example.demo.repository;

import com.example.demo.southbound.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String name);
}
