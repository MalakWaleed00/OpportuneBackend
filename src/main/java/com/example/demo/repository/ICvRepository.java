package com.example.demo.repository;

import com.example.demo.southbound.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICvRepository extends JpaRepository<Cv, Long> {
    Optional<Cv> findByUserId(Long userId);
}
