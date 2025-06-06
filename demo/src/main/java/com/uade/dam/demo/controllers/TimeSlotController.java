package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.TimeSlotReserveRequest;
import com.uade.dam.demo.entity.TimeSlot;
import com.uade.dam.demo.repository.TimeSlotRepository;
import com.uade.dam.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final TimeSlotRepository timeSlotRepository;
    private final UserService userService;

    public TimeSlotController(TimeSlotRepository timeSlotRepository, UserService userService) {
        this.timeSlotRepository = timeSlotRepository;
        this.userService = userService;
    }

    // Listar horarios reservados por profesional y fecha
    @GetMapping
    public List<TimeSlot> getByProfessionalAndDate(
            @RequestParam String professionalId,
            @RequestParam String date) {
        return timeSlotRepository.findByProfessionalIdAndDate(professionalId, date);
    }

    // Reservar un horario
    @PostMapping("/reserve")
    public ResponseEntity<?> reserveSlot(@RequestBody TimeSlotReserveRequest req) {
        // Validar que el usuario tenga obra social y nro afiliado
        var userOpt = userService.findById(req.getUserId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        var user = userOpt.get();
        if (user.getObraSocial() == null || user.getObraSocial().isBlank() ||
            user.getNroAfiliado() == null || user.getNroAfiliado().isBlank()) {
            return ResponseEntity.status(400).body("Debes cargar tu obra social y número de afiliado para reservar un turno");
        }

        var slotOpt = timeSlotRepository.findByProfessionalIdAndDateAndTime(
            req.getProfessionalId(), req.getDate(), req.getTime()
        );
        if (slotOpt.isPresent()) {
            return ResponseEntity.status(409).body("Horario ya reservado");
        }
        TimeSlot slot = TimeSlot.builder()
            .professionalId(req.getProfessionalId())
            .date(req.getDate())
            .time(req.getTime())
            .appointmentId(req.getUserId())
            .build();
        return ResponseEntity.ok(timeSlotRepository.save(slot));
    }

    // Agregar un horario (solo si el usuario tiene obra social y nro afiliado)
    @PostMapping
    public ResponseEntity<?> createTimeSlot(@RequestBody TimeSlot slot) {
        // Validar que el usuario tenga obra social y nro afiliado
        if (slot.getAppointmentId() == null) {
            return ResponseEntity.status(400).body("Falta el ID de usuario (appointmentId)");
        }
        var userOpt = userService.findById(slot.getAppointmentId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
        var user = userOpt.get();
        if (user.getObraSocial() == null || user.getObraSocial().isBlank() ||
            user.getNroAfiliado() == null || user.getNroAfiliado().isBlank()) {
            return ResponseEntity.status(400).body("Debes cargar tu obra social y número de afiliado para reservar un turno");
        }

        // Validar que no exista ya ese horario reservado
        var slotOpt = timeSlotRepository.findByProfessionalIdAndDateAndTime(
            slot.getProfessionalId(), slot.getDate(), slot.getTime()
        );
        if (slotOpt.isPresent()) {
            return ResponseEntity.status(409).body("Horario ya reservado");
        }

        return ResponseEntity.ok(timeSlotRepository.save(slot));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTimeSlot(@PathVariable String id) {
        timeSlotRepository.deleteById(id);
        return ResponseEntity.ok().build();
}
}