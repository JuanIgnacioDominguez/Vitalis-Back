package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Especialidad;
import com.uade.dam.demo.service.EspecialidadService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/especialidades")
public class EspecialidadController {
    private final EspecialidadService service;
    public EspecialidadController(EspecialidadService service) { this.service = service; }
    @GetMapping
    public List<Especialidad> getAll() { return service.findAll(); }
    @GetMapping("/{id}") public Especialidad getOne(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public Especialidad create(@RequestBody Especialidad e) { return service.save(e); }
    @PutMapping("/{id}") public Especialidad update(@PathVariable Long id, @RequestBody Especialidad e) { e.setId(id); return service.save(e); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}