package com.uade.dam.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.uade.dam.demo.entity.Favorito;
import com.uade.dam.demo.service.FavoritoService;


@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Favorito>> getFavoritos(@PathVariable Long userId) {
        return ResponseEntity.ok(favoritoService.obtenerFavoritos(userId));
    }

    @PostMapping("/{userId}/{profesionalId}")
    public ResponseEntity<Favorito> addFavorito(@PathVariable Long userId, @PathVariable Long profesionalId) {
        return ResponseEntity.ok(favoritoService.agregarFavorito(userId, profesionalId));
    }

    @DeleteMapping("/{userId}/{profesionalId}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Long userId, @PathVariable Long profesionalId) {
        favoritoService.eliminarFavorito(userId, profesionalId);
        return ResponseEntity.noContent().build();
    }
}
