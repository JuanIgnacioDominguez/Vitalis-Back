package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.FavoriteCreateRequest;
import com.uade.dam.demo.dto.ErrorResponseDTO;
import com.uade.dam.demo.dto.GenericSuccessDTO;
import com.uade.dam.demo.entity.Favorite;
import com.uade.dam.demo.repository.FavoriteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    public FavoriteController(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @GetMapping
    public List<Favorite> list() {
        return favoriteRepository.findByUserId("demo-user-id");
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody FavoriteCreateRequest req) {
        Favorite favorite = Favorite.builder()
                .userId("demo-user-id")
                .professionalId(req.getProfesionalId())
                .build();
        favoriteRepository.save(favorite);
        return ResponseEntity.status(201).body(favorite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable String id) {
        if (!favoriteRepository.existsById(id)) {
            return ResponseEntity.status(404).body(
                    new ErrorResponseDTO("NOT_FOUND", "Favorite not found"));
        }
        favoriteRepository.deleteById(id);
        return ResponseEntity.ok(new GenericSuccessDTO("Favorite removed"));
    }
}
