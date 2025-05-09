package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Profesional;
import com.uade.dam.demo.service.ProfesionalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professionals")
public class ProfesionalController {

    private final ProfesionalService profesionalService;

    public ProfesionalController(ProfesionalService profesionalService) {
        this.profesionalService = profesionalService;
    }

    @GetMapping
    public ResponseEntity<List<Profesional>> getAll() {
        return ResponseEntity.ok(profesionalService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesional> getById(@PathVariable Long id) {
        return profesionalService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profesional> create(@RequestBody Profesional profesional) {
        return ResponseEntity.ok(profesionalService.save(profesional));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        profesionalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
