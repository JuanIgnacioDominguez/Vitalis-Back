package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Favorito;
import com.uade.dam.demo.entity.Profesional;
import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.repository.FavoritoRepository;
import com.uade.dam.demo.repository.ProfesionalRepository;
import com.uade.dam.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UserRepository userRepository;
    private final ProfesionalRepository profesionalRepository;

    public List<Favorito> obtenerFavoritos(Long userId) {
        return favoritoRepository.findByUsuarioId(userId);
    }

    public Favorito agregarFavorito(Long userId, Long profesionalId) {
        if (favoritoRepository.existsByUsuarioIdAndProfesionalId(userId, profesionalId)) {
            throw new RuntimeException("Ya es favorito");
        }

        User user = userRepository.findById(userId).orElseThrow();
        Profesional profesional = profesionalRepository.findById(profesionalId).orElseThrow();

        Favorito favorito = Favorito.builder()
                .usuario(user)
                .profesional(profesional)
                .build();

        return favoritoRepository.save(favorito);
    }

    public void eliminarFavorito(Long userId, Long profesionalId) {
        favoritoRepository.deleteByUsuarioIdAndProfesionalId(userId, profesionalId);
    }
}
