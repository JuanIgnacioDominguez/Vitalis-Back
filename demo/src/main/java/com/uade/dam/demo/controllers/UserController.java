package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.*;
import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender; 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JavaMailSender mailSender; 
    private final BCryptPasswordEncoder passwordEncoder;
    private final Map<String, String> deleteCodes = new ConcurrentHashMap<>();

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
    public ResponseEntity<?> deleteUser(@PathVariable String id, @RequestParam String code, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        System.out.println("Authorization header recibido: " + authHeader);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserId = authentication.getName();

        var userOpt = userService.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponseDTO("NOT_FOUND", "User not found"));
        }
        User user = userOpt.get();

        System.out.println("ID autenticado: " + authenticatedUserId);
        System.out.println("ID del usuario a borrar: " + user.getId());

        if (!user.getId().equals(authenticatedUserId)) {
            return ResponseEntity.status(403).body(new ErrorResponseDTO("FORBIDDEN", "No puedes borrar otro usuario"));
        }

        String savedCode = deleteCodes.get(user.getEmail());
        if (savedCode == null || !savedCode.equals(code)) {
            return ResponseEntity.status(400).body(new ErrorResponseDTO("INVALID_CODE", "Código incorrecto o no solicitado"));
        }
        userService.deleteById(id);
        deleteCodes.remove(user.getEmail());
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

    @PostMapping("/{id}/request-delete-code")
    public ResponseEntity<?> requestDeleteCode(@PathVariable String id) {
        System.out.println("Solicitud de código de borrado recibida para usuario: " + id);
        var userOpt = userService.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponseDTO("NOT_FOUND", "User not found"));
        }
        User user = userOpt.get();
        String code = String.format("%06d", new Random().nextInt(999999));
        deleteCodes.put(user.getEmail(), code);

        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(user.getEmail());
            mail.setSubject("Código de confirmación para eliminar cuenta");
            mail.setText("Tu código de confirmación es: " + code);
            mailSender.send(mail);
            return ResponseEntity.ok(new GenericSuccessDTO("Código enviado al correo"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponseDTO("ERROR", "Error enviando código"));
        }
    }
}
