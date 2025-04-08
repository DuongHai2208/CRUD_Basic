package com.example.test.repository;

import com.example.test.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Client, String> {
    List<Client> findByNameContainingIgnoreCase(String keyword);
    Optional<Client> findByUsername(String username);
}
