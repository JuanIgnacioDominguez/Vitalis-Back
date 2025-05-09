package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Profesional;
import com.uade.dam.demo.repository.ProfesionalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesionalService {

    private final ProfesionalRepository professionalRepository;

    public ProfesionalService(ProfesionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public List<Profesional> getAll() {
        return professionalRepository.findAll();
    }

    public Optional<Profesional> getById(Long id) {
        return professionalRepository.findById(id);
    }

    public Profesional save(Profesional professional) {
        return professionalRepository.save(professional);
    }

    public void delete(Long id) {
        professionalRepository.deleteById(id);
    }
}
