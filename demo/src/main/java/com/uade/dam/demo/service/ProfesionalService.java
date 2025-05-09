package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Profesional;
import com.uade.dam.demo.repository.ProfesionalRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfesionalService {
    private final ProfesionalRepository repo;
    public ProfesionalService(ProfesionalRepository repo) { this.repo = repo; }
    public List<Profesional> findAll() { return repo.findAll(); }
    public Profesional findById(Long id) { return repo.findById(id).orElse(null); }
    public Profesional save(Profesional p) { return repo.save(p); }
    public void delete(Long id) { repo.deleteById(id); }
}