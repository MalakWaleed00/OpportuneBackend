package com.example.demo.repository;

import com.example.demo.southbound.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCourseNameAndUrl(String courseName, String url);
}