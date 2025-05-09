package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Favorite;
import com.uade.dam.demo.entity.Profesional;
import com.uade.dam.demo.entity.Usuario;
import com.uade.dam.demo.repository.FavoriteRepository;
import com.uade.dam.demo.repository.ProfesionalRepository;
import com.uade.dam.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository usuarioRepository;
    private final ProfesionalRepository profesionalRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                            UserRepository usuarioRepository,
                            ProfesionalRepository profesionalRepository) {
        this.favoriteRepository = favoriteRepository;
        this.usuarioRepository = usuarioRepository;
        this.profesionalRepository = profesionalRepository;
    }

    public List<Favorite> getFavoritesByUsuario(Long usuarioId) {
        return favoriteRepository.findByUsuarioId(usuarioId);
    }

    public Favorite addFavorite(Long usuarioId, Long profesionalId) {
        favoriteRepository.findByUsuarioIdAndProfesionalId(usuarioId, profesionalId).ifPresent(f -> {
            throw new RuntimeException("Ya está en favoritos");
        });

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Profesional profesional = profesionalRepository.findById(profesionalId)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        Favorite favorite = new Favorite();
        favorite.setUsuario(usuario);
        favorite.setProfesional(profesional);
        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(Long usuarioId, Long profesionalId) {
        Favorite favorite = favoriteRepository.findByUsuarioIdAndProfesionalId(usuarioId, profesionalId)
                .orElseThrow(() -> new RuntimeException("Favorito no encontrado"));
        favoriteRepository.delete(favorite);
    }
}