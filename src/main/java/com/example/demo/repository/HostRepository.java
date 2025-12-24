package com.example.apiproject.repository;

import com.example.apiproject.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {
 
    Optional<Host> findByEmail(String email);
}
