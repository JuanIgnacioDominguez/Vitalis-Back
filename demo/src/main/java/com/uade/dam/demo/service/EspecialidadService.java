package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Especialidad;
import com.uade.dam.demo.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EspecialidadService {
    private final EspecialidadRepository repo;
    public EspecialidadService(EspecialidadRepository repo) { this.repo = repo; }
    public List<Especialidad> findAll() { return repo.findAll(); }
    public Especialidad findById(Long id) { return repo.findById(id).orElse(null); }
    public Especialidad save(Especialidad e) { return repo.save(e); }
    public void delete(Long id) { repo.deleteById(id); }
}