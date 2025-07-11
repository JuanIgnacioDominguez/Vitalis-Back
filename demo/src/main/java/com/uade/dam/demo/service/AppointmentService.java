package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Appointment;
import com.uade.dam.demo.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> findById(String id) {
        return appointmentRepository.findById(id);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteById(String id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> findByUserId(String userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public List<Appointment> updateExpiredAppointmentsForUser(String userId) {
        List<Appointment> userAppointments = appointmentRepository.findByUserId(userId);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Appointment appointment : userAppointments) {
            if ("pending".equals(appointment.getStatus())) {
                try {
                    LocalDate appointmentDate = LocalDate.parse(appointment.getDate(), dateFormatter);
                    LocalTime appointmentTime = LocalTime.parse(appointment.getTime(), timeFormatter);

                    if (appointmentDate.isBefore(today) ||
                            (appointmentDate.equals(today) && appointmentTime.isBefore(now))) {
                        appointment.setStatus("completed");
                        appointmentRepository.save(appointment);
                    }
                } catch (Exception e) {
                    System.err.println("Error parseando fecha/hora para appointment " + appointment.getId());
                }
            }
        }

        return userAppointments;
    }
}