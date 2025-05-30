package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Professional;
import com.uade.dam.demo.service.ProfessionalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professionals")
public class ProfessionalController {

    private final ProfessionalService professionalService;

    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @GetMapping
    public List<Professional> list() {
        return professionalService.findAll();
    }

    @GetMapping("/{id}")
    public Professional get(@PathVariable String id) {
        return professionalService.findById(id).orElse(null);
    }

    @PostMapping
    public Professional create(@RequestBody Professional professional) {
        return professionalService.save(professional);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        professionalService.deleteById(id);
    }
}
