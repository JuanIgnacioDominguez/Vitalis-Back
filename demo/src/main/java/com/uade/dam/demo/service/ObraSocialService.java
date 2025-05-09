package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.ObraSocial;
import com.uade.dam.demo.repository.ObraSocialRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ObraSocialService {
    private final ObraSocialRepository repo;
    public ObraSocialService(ObraSocialRepository repo) { this.repo = repo; }
    public List<ObraSocial> findAll() { return repo.findAll(); }
    public ObraSocial findById(Long id) { return repo.findById(id).orElse(null); }
}
