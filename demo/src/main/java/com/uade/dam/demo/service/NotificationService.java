package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Notification;
import com.uade.dam.demo.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findByUserId(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    public Optional<Notification> findById(String id) {
        return notificationRepository.findById(id);
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void deleteById(String id) {
        notificationRepository.deleteById(id);
    }
}
