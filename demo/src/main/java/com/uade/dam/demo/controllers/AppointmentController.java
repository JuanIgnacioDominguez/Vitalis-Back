package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.AppointmentCreateRequestDTO;
import com.uade.dam.demo.dto.ErrorResponseDTO;
import com.uade.dam.demo.entity.Appointment;
import com.uade.dam.demo.repository.AppointmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping
    public List<Appointment> list(@RequestParam(required = false) String estado) {
        String userId = "demo-user-id"; // Reemplaza por el userId autenticado
        if (estado != null) {
            return appointmentRepository.findByUserIdAndStatus(userId, estado);
        }
        return appointmentRepository.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AppointmentCreateRequestDTO req) {
        Appointment appointment = Appointment.builder()
                .userId("demo-user-id")
                .professionalId(req.getProfesionalId())
                .date(req.getFecha())
                .time(req.getHora())
                .status("pendiente")
                .build();
        appointmentRepository.save(appointment);
        return ResponseEntity.status(201).body(appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable String id) {
        var appointmentOpt = appointmentRepository.findById(id);
        if (appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            appointment.setStatus("cancelado");
            appointmentRepository.save(appointment);
            return ResponseEntity.ok(appointment);
        }
        return ResponseEntity.status(404).body(
                new ErrorResponseDTO("NOT_FOUND", "Appointment not found"));
    }
}
