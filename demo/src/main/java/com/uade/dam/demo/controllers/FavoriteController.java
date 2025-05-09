package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Favorite;
import com.uade.dam.demo.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Favorite>> getFavorites(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(favoriteService.getFavoritesByUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestParam Long usuarioId,
                                                @RequestParam Long profesionalId) {
        return ResponseEntity.ok(favoriteService.addFavorite(usuarioId, profesionalId));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeFavorite(@RequestParam Long usuarioId,
                                               @RequestParam Long profesionalId) {
        favoriteService.removeFavorite(usuarioId, profesionalId);
        return ResponseEntity.noContent().build();
    }
}
