package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.AppRatingRequest;
import com.uade.dam.demo.dto.ErrorResponseDTO;
import com.uade.dam.demo.dto.GenericSuccessDTO;
import com.uade.dam.demo.entity.AppRating;
import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.service.AppRatingService;
import com.uade.dam.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app-ratings")
public class AppRatingController {

    private final AppRatingService appRatingService;
    private final UserService userService;

    public AppRatingController(AppRatingService appRatingService, UserService userService) {
        this.appRatingService = appRatingService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createRating(@RequestBody AppRatingRequest request) {
        // Obtener usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        
        var userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorResponseDTO("USER_NOT_FOUND", "Usuario no encontrado"));
        }
        
        User user = userOpt.get();
        
        if (appRatingService.hasUserRated(user)) {
            return ResponseEntity.status(400).body(
                new ErrorResponseDTO("ALREADY_RATED", "Ya has calificado la aplicación"));
        }
        
        if (request.getPuntuacion() < 1 || request.getPuntuacion() > 5) {
            return ResponseEntity.status(400).body(
                new ErrorResponseDTO("INVALID_SCORE", "La puntuación debe estar entre 1 y 5"));
        }
        
        AppRating rating = AppRating.builder()
            .user(user)
            .puntuacion(request.getPuntuacion())
            .aspectosPositivos(request.getAspectosPositivos())
            .aspectosMejorar(request.getAspectosMejorar())
            .comentario(request.getComentario())
            .fecha(LocalDateTime.now())
            .build();
        
        appRatingService.save(rating);
        
        return ResponseEntity.ok(new GenericSuccessDTO("Gracias por tu calificación"));
    }

    @GetMapping("/my-rating")
    public ResponseEntity<?> getMyRating() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        
        var userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorResponseDTO("USER_NOT_FOUND", "Usuario no encontrado"));
        }
        
        var ratingOpt = appRatingService.findByUser(userOpt.get());
        if (ratingOpt.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorResponseDTO("NOT_RATED", "Aún no has calificado la aplicación"));
        }
        
        return ResponseEntity.ok(ratingOpt.get());
    }

    @GetMapping("/has-rated")
    public ResponseEntity<?> hasUserRated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        
        var userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(
                new ErrorResponseDTO("USER_NOT_FOUND", "Usuario no encontrado"));
        }
        
        boolean hasRated = appRatingService.hasUserRated(userOpt.get());
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("hasRated", hasRated);
        
        return ResponseEntity.ok(response);
    }
}
