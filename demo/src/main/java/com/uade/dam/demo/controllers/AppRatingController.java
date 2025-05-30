package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.AppRating;
import com.uade.dam.demo.service.AppRatingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app-ratings")
public class AppRatingController {

    private final AppRatingService appRatingService;

    public AppRatingController(AppRatingService appRatingService) {
        this.appRatingService = appRatingService;
    }

    @GetMapping
    public List<AppRating> list() {
        return appRatingService.findAll();
    }

    @GetMapping("/{id}")
    public AppRating get(@PathVariable String id) {
        return appRatingService.findById(id).orElse(null);
    }

    @PostMapping
    public AppRating create(@RequestBody AppRating appRating) {
        return appRatingService.save(appRating);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        appRatingService.deleteById(id);
    }
}
