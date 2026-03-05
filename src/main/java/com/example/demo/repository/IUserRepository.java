package com.example.demo.repository;


import com.example.demo.southbound.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User>findByNameIgnoreCase(String name);


    @Query("""
    SELECT u
    FROM User u
    WHERE u.isActive = true
      AND (
          LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
          OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
      )
""")
    Page<User> searchUsers(String keyword, Pageable pageable);

    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<User> findByEmailOrUsername(String email, String username);




}
