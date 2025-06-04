package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        if (user.getImagen() == null) {
            try {
                user.setImagen(Files.readAllBytes(Paths.get("uploads/defaultUser.jpg")));
            } catch (Exception e) {
                user.setImagen(null); 
            }
        }
        return userRepository.save(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
