package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.*;
import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JavaMailSender mailSender; 
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserService userService, JavaMailSender mailSender, BCryptPasswordEncoder passwordEncoder) { 
        this.userService = userService;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
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
            user.setEmail(update.getEmail());
            user.setDni(update.getDni());
            user.setObraSocial(update.getObraSocial());
            user.setNroAfiliado(update.getNroAfiliado());
            user.setTelefono(update.getTelefono());
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
            boolean passwordMatch = passwordEncoder.matches(req.getActual(), user.getPassword());
            if (!passwordMatch) {
                return ResponseEntity.status(401).body(new ErrorResponseDTO("INVALID_PASSWORD", "La contraseña actual es incorrecta"));
            }
            user.setPassword(passwordEncoder.encode(req.getNueva()));
            userService.save(user);
            return ResponseEntity.ok(new GenericSuccessDTO("Password updated"));
        }
        return ResponseEntity.status(404).body(
                new ErrorResponseDTO("NOT_FOUND", "User not found"));
    }

    @PostMapping("/contact")
    public ResponseEntity<?> contact(@RequestBody ContactRequestDTO req) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo("chatgptlanga@gmail.com"); 
            mail.setSubject("Nuevo mensaje de Contactanos");
            mail.setText("Nombre: " + req.getNombre() + "\nEmail: " + req.getEmail() + "\nMensaje:\n" + req.getMensaje());
            mailSender.send(mail);
            return ResponseEntity.ok(new GenericSuccessDTO("Mensaje enviado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseDTO("ERROR", "Error enviando mensaje"));
        }
    }
}
