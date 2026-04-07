package com.example.demo.repository;

import com.example.demo.southbound.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IJobRepository extends JpaRepository<Job, Long> {
    Optional<Job> findByTitleAndCompany(String title, String company);

}
