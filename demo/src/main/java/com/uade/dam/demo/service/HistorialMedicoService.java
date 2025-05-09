package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.HistorialMedico;
import com.uade.dam.demo.repository.HistorialMedicoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistorialMedicoService {
    private final HistorialMedicoRepository repo;
    public HistorialMedicoService(HistorialMedicoRepository repo) { this.repo = repo; }
    public List<HistorialMedico> findAll() { return repo.findAll(); }
    public HistorialMedico findById(Long id) { return repo.findById(id).orElse(null); }
    public HistorialMedico save(HistorialMedico h) { return repo.save(h); }
    public void delete(Long id) { repo.deleteById(id); }
}