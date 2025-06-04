package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.TimeSlot;
import com.uade.dam.demo.repository.TimeSlotRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotController(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    // Listar horarios por profesional y fecha
    @GetMapping
    public List<TimeSlot> getByProfessionalAndDate(
            @RequestParam String professionalId,
            @RequestParam String date) {
        return timeSlotRepository.findByProfessionalIdAndDate(professionalId, date);
    }

    // Reservar un horario (cambiar a RESERVED)
    @PostMapping("/{id}/reserve")
    public TimeSlot reserve(@PathVariable String id, @RequestParam String appointmentId) {
        Optional<TimeSlot> slotOpt = timeSlotRepository.findById(id);
        if (slotOpt.isPresent()) {
            TimeSlot slot = slotOpt.get();
            if (slot.getStatus() == TimeSlot.Status.AVAILABLE) {
                slot.setStatus(TimeSlot.Status.RESERVED);
                slot.setAppointmentId(appointmentId);
                return timeSlotRepository.save(slot);
            }
        }
        throw new RuntimeException("No disponible para reservar");
    }

    // Cancelar un horario (volver a AVAILABLE)
    @PostMapping("/{id}/cancel")
    public TimeSlot cancel(@PathVariable String id) {
        Optional<TimeSlot> slotOpt = timeSlotRepository.findById(id);
        if (slotOpt.isPresent()) {
            TimeSlot slot = slotOpt.get();
            slot.setStatus(TimeSlot.Status.AVAILABLE);
            slot.setAppointmentId(null);
            return timeSlotRepository.save(slot);
        }
        throw new RuntimeException("No encontrado");
    }
}