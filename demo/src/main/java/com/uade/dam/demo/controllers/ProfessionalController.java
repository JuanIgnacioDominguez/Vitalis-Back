package com.uade.dam.demo.controllers;

import com.uade.dam.demo.dto.TopFavoriteDTO;
import com.uade.dam.demo.entity.Professional;
import com.uade.dam.demo.repository.FavoriteRepository;
import com.uade.dam.demo.repository.ProfessionalRepository;
import com.uade.dam.demo.service.ProfessionalService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/professionals")
public class ProfessionalController {

    private final ProfessionalService professionalService;
    private final FavoriteRepository favoriteRepository;
    private final ProfessionalRepository professionalRepository;

    public ProfessionalController(ProfessionalService professionalService, FavoriteRepository favoriteRepository, ProfessionalRepository professionalRepository) {
        this.professionalService = professionalService;
        this.favoriteRepository = favoriteRepository;
        this.professionalRepository = professionalRepository;
    }

    @GetMapping
    public List<Professional> list() {
        return professionalService.findAll();
    }

    @GetMapping("/{id}")
    public Professional get(@PathVariable String id) {
        return professionalService.findById(id).orElse(null);
    }

    @PostMapping
    public Professional create(@RequestBody Professional professional) {
        return professionalService.save(professional);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        professionalService.deleteById(id);
    }

    @GetMapping("/top-favorites")
    public ResponseEntity<List<TopFavoriteDTO>> getTopFavoritedProfessionals(@RequestParam(defaultValue = "4") int limit) {
        var topFavs = favoriteRepository.findTopFavoritedProfessionals(PageRequest.of(0, limit));
        List<TopFavoriteDTO> result = topFavs.stream().map(fav -> {
            var profOpt = professionalRepository.findById(fav.getProfessionalId());
            if (profOpt.isPresent()) {
                var prof = profOpt.get();
                return new TopFavoriteDTO(
                    prof.getId(),
                    prof.getName(),
                    prof.getSpecialty(), 
                    fav.getCount()
                );
            }
            return null;
        }).filter(Objects::nonNull).toList();

        if (result.size() < limit) {
            List<String> usedIds = result.stream().map(TopFavoriteDTO::getId).toList();
            List<Professional> others = professionalRepository.findAll().stream()
                .filter(p -> !usedIds.contains(p.getId()))
                .collect(java.util.stream.Collectors.toList());
            java.util.Collections.shuffle(others);
            for (Professional p : others) {
                if (result.size() >= limit) break;
                result.add(new TopFavoriteDTO(
                    p.getId(),
                    p.getName(),
                    p.getSpecialty(), 
                    0L 
                ));
            }
        }
        return ResponseEntity.ok(result);
    }
}
