package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.AuthRequestDTO;
import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.repository.UserRepository;
import com.uade.dam.demo.security.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO req) {
        if (usuarioRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "Email ya registrado"));
        }
        User usuario = User.builder()
                .email(req.getEmail())
                .nombre(req.getNombre())
                .telefono(req.getTelefono())
                .password(passwordEncoder.encode(req.getPassword()))
                .fechaRegistro(LocalDateTime.now())
                .build();
        usuarioRepository.save(usuario);
        String token = jwtUtil.generateToken(usuario.getId());
        return ResponseEntity.status(201).body(Map.of(
                "token", token,
                "usuario", usuario
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        Optional<User> usuarioOpt = usuarioRepository.findByEmail(req.getEmail());
        if (usuarioOpt.isEmpty() || !passwordEncoder.matches(req.getPassword(), usuarioOpt.get().getPassword())) {
            return ResponseEntity.status(401).body(Map.of("mensaje", "Credenciales inválidas"));
        }
        User usuario = usuarioOpt.get();
        String token = jwtUtil.generateToken(usuario.getId());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "usuario", usuario
        ));
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        Optional<User> userOpt = usuarioRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("codigo", "NOT_FOUND", "mensaje", "User not found"));
        }
        // Simula envío de código (en real, envía por email)
        // Guarda el código en memoria o BD temporalmente
        return ResponseEntity.ok(Map.of("message", "Reset code sent"));
    }

    @PostMapping("/verify-reset-code")
    public ResponseEntity<?> verifyResetCode(@RequestBody Map<String, String> req) {
        // Simula verificación de código
        return ResponseEntity.ok(Map.of("message", "Code valid"));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String newPassword = req.get("nueva");
        Optional<User> userOpt = usuarioRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("codigo", "NOT_FOUND", "mensaje", "User not found"));
        }
        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        usuarioRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
    }

    @Data
    public static class AuthRequest {
        private String email;
        private String password;
        private String nombre;
        private String telefono;
    }
}