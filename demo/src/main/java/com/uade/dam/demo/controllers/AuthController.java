package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.AuthRequestDTO;
import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.repository.UserRepository;
import com.uade.dam.demo.security.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStream;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JavaMailSender mailSender;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // Guardar códigos en memoria (email -> código)
    private Map<String, String> resetCodes = new ConcurrentHashMap<>();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO req) {
        logger.info("Intentando registrar usuario con email: {}", req.getEmail());
        try {
            if (usuarioRepository.findByEmail(req.getEmail()).isPresent()) {
                logger.warn("El email ya está registrado: {}", req.getEmail()); 
                return ResponseEntity.badRequest().body(Map.of(
                    "codigo", "EMAIL_EXISTS",
                    "mensaje", "Email ya registrado"
                ));
            }
            
            byte[] defaultImage = null;
            try {
                InputStream imageStream = getClass().getClassLoader().getResourceAsStream("uploads/defaultUser.jpg");
                if (imageStream != null) {
                    defaultImage = imageStream.readAllBytes();
                    imageStream.close();
                } else {
                    logger.warn("No se encontró la imagen por defecto en uploads/defaultUser.jpg");
                }
            } catch (Exception e) {
                logger.warn("No se pudo cargar la imagen por defecto: {}", e.getMessage());
            }
            
            User usuario = User.builder()
                    .email(req.getEmail())
                    .nombre(req.getNombre())
                    .telefono(req.getTelefono())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .fechaRegistro(LocalDateTime.now())
                    .imagen(defaultImage)  
                    .build();
            usuarioRepository.save(usuario);
            String token = jwtUtil.generateToken(usuario.getId());
            logger.info("Usuario registrado correctamente: {}", usuario.getEmail());
            return ResponseEntity.status(201).body(Map.of(
                    "token", token,
                    "usuario", usuario
            ));
        } catch (Exception e) {
            logger.error("Error al registrar usuario", e);
            return ResponseEntity.status(500).body(Map.of(
                "codigo", "INTERNAL_ERROR",
                "mensaje", "Error interno del servidor"
            ));
        }
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
        logger.info("Recibida solicitud de recuperación para: {}", email);

        Optional<User> userOpt = usuarioRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            logger.warn("No se encontró usuario con email: {}", email);
            return ResponseEntity.status(404).body(Map.of("codigo", "NOT_FOUND", "mensaje", "User not found"));
        }
        String code = String.format("%06d", new Random().nextInt(999999));
        resetCodes.put(email, code);
        logger.info("Código generado para {}: {}", email, code);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Código de recuperación de contraseña");
            message.setText("Tu código de recuperación es: " + code);
            mailSender.send(message);
            logger.info("Correo enviado a {}", email);
        } catch (Exception e) {
            logger.error("Error enviando correo a {}: {}", email, e.getMessage());
            return ResponseEntity.status(500).body(Map.of("message", "Error enviando correo"));
        }

        return ResponseEntity.ok(Map.of("message", "Reset code sent"));
    }

    @PostMapping("/verify-reset-code")
    public ResponseEntity<?> verifyResetCode(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String code = req.get("code");
        String savedCode = resetCodes.get(email);
        if (savedCode != null && savedCode.equals(code)) {
            return ResponseEntity.ok(Map.of("message", "Code valid"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "Invalid code"));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String newPassword = req.get("nueva");
        String code = req.get("code");
        String savedCode = resetCodes.get(email);

        if (savedCode == null || !savedCode.equals(code)) {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid or missing code"));
        }

        Optional<User> userOpt = usuarioRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("codigo", "NOT_FOUND", "mensaje", "User not found"));
        }
        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        usuarioRepository.save(user);

        resetCodes.remove(email);

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