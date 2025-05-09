package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Profesional;
import com.uade.dam.demo.service.ProfesionalService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/profesionales")
public class ProfesionalController {
    private final ProfesionalService service;
    public ProfesionalController(ProfesionalService service) { this.service = service; }
    @GetMapping public List<Profesional> getAll() { return service.findAll(); }
    @GetMapping("/{id}") public Profesional getOne(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public Profesional create(@RequestBody Profesional p) { return service.save(p); }
    @PutMapping("/{id}") public Profesional update(@PathVariable Long id, @RequestBody Profesional p) { p.setId(id); return service.save(p); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}