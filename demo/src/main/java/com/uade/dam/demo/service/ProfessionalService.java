package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Professional;
import com.uade.dam.demo.entity.TimeSlot;
import com.uade.dam.demo.repository.ProfessionalRepository;
import com.uade.dam.demo.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {
    private final ProfessionalRepository professionalRepository;
    private final TimeSlotRepository timeSlotRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository, TimeSlotRepository timeSlotRepository) {
        this.professionalRepository = professionalRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<Professional> findAll() {
        return professionalRepository.findAll();
    }

    public Optional<Professional> findById(String id) {
        return professionalRepository.findById(id);
    }

    public Professional save(Professional professional) {
        Professional saved = professionalRepository.save(professional);

        List<TimeSlot> slots = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) { // 7 días desde hoy
            String date = today.plusDays(i).toString();
            LocalTime start = LocalTime.of(9, 0);
            LocalTime end = LocalTime.of(18, 0);
            while (!start.isAfter(end.minusMinutes(30))) {
                slots.add(TimeSlot.builder()
                        .professionalId(saved.getId())
                        .date(date)
                        .time(start.format(DateTimeFormatter.ofPattern("HH:mm")))
                        .status(TimeSlot.Status.AVAILABLE)
                        .build());
                start = start.plusMinutes(30);
            }
        }
        timeSlotRepository.saveAll(slots);

        return saved;
    }

    public void deleteById(String id) {
        professionalRepository.deleteById(id);
    }
}