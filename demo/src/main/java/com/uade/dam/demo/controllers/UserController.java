package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.*;
import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        var userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.status(404).body(
                new ErrorResponseDTO("NOT_FOUND", "User not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserUpdateRequestDTO update) {
        var userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setNombre(update.getNombre());
            user.setTelefono(update.getTelefono());
            user.setObraSocial(update.getObraSocial());
            userService.save(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(404).body(
                new ErrorResponseDTO("NOT_FOUND", "User not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.status(404).body(
                    new ErrorResponseDTO("NOT_FOUND", "User not found"));
        }
        userService.deleteById(id);
        return ResponseEntity.ok(new GenericSuccessDTO("Account deleted"));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable String id, @RequestBody PasswordChangeRequest req) {
        var userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // TODO: Validar la contraseña actual con BCrypt
            user.setPassword(req.getNueva());
            userService.save(user);
            return ResponseEntity.ok(new GenericSuccessDTO("Password updated"));
        }
        return ResponseEntity.status(404).body(
                new ErrorResponseDTO("NOT_FOUND", "User not found"));
    }
}
