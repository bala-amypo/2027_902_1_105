package com.example.demo.repository;

import com.example.demo.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostRepository extends JpaRepository<Host, Long> {

    // for uniqueness checks
    Optional<Host> findByEmail(String email);
}
