package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Professional;
import com.uade.dam.demo.repository.ProfessionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {
    private final ProfessionalRepository professionalRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public List<Professional> findAll() {
        return professionalRepository.findAll();
    }

    public Optional<Professional> findById(String id) {
        return professionalRepository.findById(id);
    }

    public Professional save(Professional professional) {
        return professionalRepository.save(professional);
    }

    public void deleteById(String id) {
        professionalRepository.deleteById(id);
    }
}