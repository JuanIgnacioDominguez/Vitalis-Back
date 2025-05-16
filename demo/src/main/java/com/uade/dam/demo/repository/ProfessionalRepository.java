package com.uade.dam.demo.repository;

import com.uade.dam.demo.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProfessionalRepository extends JpaRepository<Professional, String> {
    List<Professional> findBySpecialty(String specialty);
}
