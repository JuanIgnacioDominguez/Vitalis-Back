package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.AppRating;
import com.uade.dam.demo.repository.AppRatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppRatingService {
    private final AppRatingRepository appRatingRepository;

    public AppRatingService(AppRatingRepository appRatingRepository) {
        this.appRatingRepository = appRatingRepository;
    }

    public List<AppRating> findAll() {
        return appRatingRepository.findAll();
    }

    public Optional<AppRating> findById(String id) {
        return appRatingRepository.findById(id);
    }

    public AppRating save(AppRating appRating) {
        return appRatingRepository.save(appRating);
    }

    public void deleteById(String id) {
        appRatingRepository.deleteById(id);
    }
}