package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.Favorite;
import com.uade.dam.demo.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public List<Favorite> list() {
        return favoriteService.findAll();
    }

    @GetMapping("/{id}")
    public Favorite get(@PathVariable String id) {
        return favoriteService.findById(id).orElse(null);
    }

    @PostMapping
    public Favorite create(@RequestBody Favorite favorite) {
        return favoriteService.save(favorite);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        favoriteService.deleteById(id);
    }
}
