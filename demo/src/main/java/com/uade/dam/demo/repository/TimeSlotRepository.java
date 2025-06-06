package com.uade.dam.demo.repository;

import com.uade.dam.demo.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, String> {
    List<TimeSlot> findByProfessionalIdAndDate(String professionalId, String date);
    List<TimeSlot> findByProfessionalId(String professionalId);
    Optional<TimeSlot> findByProfessionalIdAndDateAndTime(String professionalId, String date, String time);
}