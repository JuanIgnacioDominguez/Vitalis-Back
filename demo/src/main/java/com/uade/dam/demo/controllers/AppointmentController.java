package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Appointment;
import com.uade.dam.demo.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Appointment>> getUserAppointments(@PathVariable String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserId = authentication.getName();
        
        if (!userId.equals(authenticatedUserId)) {
            return ResponseEntity.status(403).build(); 
        }
        
        List<Appointment> userAppointments = appointmentService.findByUserId(userId);
        return ResponseEntity.ok(userAppointments);
    }

    @PutMapping("/user/{userId}/update-expired")
    public ResponseEntity<List<Appointment>> updateExpiredAppointments(
        @PathVariable String userId, 
        @RequestBody Map<String, String> req) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserId = authentication.getName();
        
        if (!userId.equals(authenticatedUserId)) {
            return ResponseEntity.status(403).build(); 
        }
        
        String currentDate = req.get("currentDate"); 
        String currentTime = req.get("currentTime"); 
        
        List<Appointment> updatedAppointments = appointmentService.updateExpiredAppointmentsForUser(userId, currentDate, currentTime);
        
        return ResponseEntity.ok(updatedAppointments);
    }
}
