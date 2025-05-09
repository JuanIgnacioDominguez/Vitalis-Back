package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.HistorialMedico;
import com.uade.dam.demo.service.HistorialMedicoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/historiales")
public class HistorialMedicoController {
    private final HistorialMedicoService service;
    public HistorialMedicoController(HistorialMedicoService service) { this.service = service; }
    @GetMapping public List<HistorialMedico> getAll() { return service.findAll(); }
    @GetMapping("/{id}") public HistorialMedico getOne(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public HistorialMedico create(@RequestBody HistorialMedico h) { return service.save(h); }
    @PutMapping("/{id}") public HistorialMedico update(@PathVariable Long id, @RequestBody HistorialMedico h) { h.setId(id); return service.save(h); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
