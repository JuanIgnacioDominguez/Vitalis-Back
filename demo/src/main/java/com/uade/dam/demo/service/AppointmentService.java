package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Appointment;
import com.uade.dam.demo.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository repo;
    public AppointmentService(AppointmentRepository repo) { this.repo = repo; }
    public List<Appointment> findAll() { return repo.findAll(); }
    public Appointment findById(Long id) { return repo.findById(id).orElse(null); }
    public Appointment save(Appointment a) { return repo.save(a); }
    public void delete(Long id) { repo.deleteById(id); }
}