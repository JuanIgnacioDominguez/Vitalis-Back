package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Professional;
import com.uade.dam.demo.repository.ProfessionalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professionals")
public class ProfessionalController {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalController(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @GetMapping
    public List<Professional> list(@RequestParam(required = false) String specialty) {
        if (specialty != null) {
            return professionalRepository.findBySpecialty(specialty);
        }
        return professionalRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable String id) {
        Professional professional = professionalRepository.findById(id)
                .orElse(null);
        if (professional == null) {
            return ResponseEntity.status(404).body(new ErrorResponse("NOT_FOUND", "Professional not found"));
        }
        return ResponseEntity.ok(professional);
    }

    private static class ErrorResponse {
        public String code;
        public String message;
        public ErrorResponse(String c, String m) { code = c; message = m; }
    }
}
