package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.ObraSocial;
import com.uade.dam.demo.service.ObraSocialService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/obrasociales")
public class ObraSocialController {
    private final ObraSocialService service;
    public ObraSocialController(ObraSocialService service) { this.service = service; }
    @GetMapping
    public List<ObraSocial> getAll() { return service.findAll(); }
    @GetMapping("/{id}")
    public ObraSocial getOne(@PathVariable Long id) { return service.findById(id); }
}
