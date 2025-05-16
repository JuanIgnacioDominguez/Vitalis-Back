package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.*;
import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        var userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.status(404).body(
                new ErrorResponseDTO("NOT_FOUND", "User not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserUpdateRequestDTO update) {
        var userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setNombre(update.getNombre());
            user.setTelefono(update.getTelefono());
            user.setObraSocial(update.getObraSocial());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(404).body(
                new ErrorResponseDTO("NOT_FOUND", "User not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(404).body(
                    new ErrorResponseDTO("NOT_FOUND", "User not found"));
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(new GenericSuccessDTO("Account deleted"));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable String id, @RequestBody PasswordChangeRequest req) {
        var userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // TODO: Validar la contraseña actual con BCrypt
            user.setPassword(req.getNueva());
            userRepository.save(user);
            return ResponseEntity.ok(new GenericSuccessDTO("Password updated"));
        }
        return ResponseEntity.status(404).body(
                new ErrorResponseDTO("NOT_FOUND", "User not found"));
    }
}
