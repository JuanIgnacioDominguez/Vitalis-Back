package com.uade.dam.demo.repository;

import com.uade.dam.demo.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    List<Appointment> findByUserId(String userId);
}
