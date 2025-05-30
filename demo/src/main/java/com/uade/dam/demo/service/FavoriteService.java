package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Favorite;
import com.uade.dam.demo.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> findAll() {
        return favoriteRepository.findAll();
    }

    public Optional<Favorite> findById(String id) {
        return favoriteRepository.findById(id);
    }

    public Favorite save(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public void deleteById(String id) {
        favoriteRepository.deleteById(id);
    }
}