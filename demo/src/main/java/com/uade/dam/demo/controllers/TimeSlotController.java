package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.TimeSlotReserveRequest;
import com.uade.dam.demo.entity.TimeSlot;
import com.uade.dam.demo.repository.TimeSlotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotController(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
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
}