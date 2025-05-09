package com.uade.dam.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.uade.dam.demo.entity.Favorito;


@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioIdAndProfesionalId(Long usuarioId, Long profesionalId);
    void deleteByUsuarioIdAndProfesionalId(Long usuarioId, Long profesionalId);
}
