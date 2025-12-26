package com.uade.dam.demo.repository;

import com.uade.dam.demo.entity.AppRating;
import com.uade.dam.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRatingRepository extends JpaRepository<AppRating, String> {
    Optional<AppRating> findByUser(User user);
    boolean existsByUser(User user);
}
