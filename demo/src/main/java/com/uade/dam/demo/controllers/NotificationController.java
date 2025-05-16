package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Notification;
import com.uade.dam.demo.repository.NotificationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping
    public List<Notification> list() {
        return notificationRepository.findByUserId("demo-user-id");
    }
}
