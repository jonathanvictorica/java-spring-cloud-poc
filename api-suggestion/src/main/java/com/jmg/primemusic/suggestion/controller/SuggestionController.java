package com.jmg.primemusic.suggestion.controller;


import com.jmg.primemusic.suggestion.service.SugeridoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/v1/suggestions")
public class SuggestionController {

    private final SugeridoService sugeridoService;

    public SuggestionController(SugeridoService sugeridoService) {
        this.sugeridoService = sugeridoService;
    }

    @GetMapping("/online/{popularity}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<GetSugerenciaByPopularidadResponse> getSugerenciaByPopularidadOnline(@PathVariable String popularity) {
        return ResponseEntity.ok(sugeridoService.getSugerenciaByPopularidadOnline(popularity));
    }

    @GetMapping("/offline/{popularity}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<GetSugerenciaByPopularidadResponse> getSugerenciaByPopularidadOffline(@PathVariable String popularity) {
        return ResponseEntity.ok(sugeridoService.getSugerenciaByPopularidadOffline(popularity));
    }
}
