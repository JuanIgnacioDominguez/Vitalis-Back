package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Notification;
import com.uade.dam.demo.service.NotificationService;
import com.uade.dam.demo.service.AppointmentReminderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final AppointmentReminderService appointmentReminderService;

    public NotificationController(NotificationService notificationService, AppointmentReminderService appointmentReminderService) {
        this.notificationService = notificationService;
        this.appointmentReminderService = appointmentReminderService;
    }

    @GetMapping
    public List<Notification> list() {
        return notificationService.findByUserId("demo-user-id");
    }

    @GetMapping("/{id}")
    public Notification get(@PathVariable String id) {
        return notificationService.findById(id).orElse(null);
    }

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return notificationService.save(notification);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        notificationService.deleteById(id);
    }

    @PostMapping("/send-reminders")
    public ResponseEntity<?> sendRemindersManually() {
        try {
            appointmentReminderService.sendAppointmentReminders();
            return ResponseEntity.ok(Map.of("mensaje", "Recordatorios enviados"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error enviando recordatorios"));
        }
    }
}
