package dev.gerardomarquez.mexico_locations.controllers;

import org.springframework.web.bind.annotation.RestController;

import dev.gerardomarquez.mexico_locations.dtos.Response;
import dev.gerardomarquez.mexico_locations.services.MexicoLocationsServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/*
 * Controlador de los endpoints para la informacion de las localidades de méxico
 */
@RestController
@RequestMapping("/api")
public class MexicoLocationsController {

    @Autowired
    private MexicoLocationsServiceI mexicoLocationsService;

    @GetMapping("/states")
    public ResponseEntity<Response> getMethodName(Pageable pageable) {

        Response response = mexicoLocationsService.getAllStates(pageable);

        return ResponseEntity.ok(response);
    }
    
}
