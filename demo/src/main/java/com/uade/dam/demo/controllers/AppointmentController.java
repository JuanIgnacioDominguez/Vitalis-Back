package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Appointment;
import com.uade.dam.demo.service.AppointmentService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/turnos")
public class AppointmentController {
    private final AppointmentService service;
    public AppointmentController(AppointmentService service) { this.service = service; }
    @GetMapping public List<Appointment> getAll() { return service.findAll(); }
    @GetMapping("/{id}") public Appointment getOne(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public Appointment create(@RequestBody Appointment a) { return service.save(a); }
    @PutMapping("/{id}") public Appointment update(@PathVariable Long id, @RequestBody Appointment a) { a.setId(id); return service.save(a); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}