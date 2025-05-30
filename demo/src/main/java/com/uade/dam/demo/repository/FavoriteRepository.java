package com.uade.dam.demo.repository;

import com.uade.dam.demo.entity.Favorite;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, String> {
    List<Favorite> findByUserId(String userId);

    // Proyección para el resultado del conteo
    interface TopFav {
        String getProfessionalId();
        Long getCount();
    }

    // Consulta que agrupa por professionalId y ordena por cantidad de favoritos
    @Query("""
        SELECT f.professionalId AS professionalId,
                COUNT(f) AS count
            FROM Favorite f
        GROUP BY f.professionalId
        ORDER BY COUNT(f) DESC
        """)
    List<TopFav> findTopFavoritedProfessionals(Pageable pageable);
}