package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Appointment;
import com.uade.dam.demo.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> list() {
        return appointmentService.findAll();
    }

    @GetMapping("/{id}")
    public Appointment get(@PathVariable String id) {
        return appointmentService.findById(id).orElse(null);
    }

    @PostMapping
    public Appointment create(@RequestBody Appointment appointment) {
        return appointmentService.save(appointment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        appointmentService.deleteById(id);
    }
}
