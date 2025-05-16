package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.AppRatingRequest;
import com.uade.dam.demo.entity.AppRating;
import com.uade.dam.demo.repository.AppRatingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/app-ratings")
public class AppRatingController {

    private final AppRatingRepository appRatingRepository;
    public AppRatingController(AppRatingRepository appRatingRepository) {
        this.appRatingRepository = appRatingRepository;
    }

    @PostMapping
    public ResponseEntity<?> rate(@RequestBody AppRatingRequest req) {
        AppRating rating = AppRating.builder()
                .score(req.getPuntuacion())
                .comment(req.getComentario())
                .date(LocalDateTime.now())
                .build();
        appRatingRepository.save(rating);
        return ResponseEntity.status(201).body(rating);
    }
}
