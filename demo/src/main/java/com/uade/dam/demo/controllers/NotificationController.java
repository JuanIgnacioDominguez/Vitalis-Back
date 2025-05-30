package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Notification;
import com.uade.dam.demo.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
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
}
