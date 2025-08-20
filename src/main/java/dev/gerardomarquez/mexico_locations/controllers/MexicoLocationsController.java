package dev.gerardomarquez.mexico_locations.controllers;

import org.springframework.web.bind.annotation.RestController;

import dev.gerardomarquez.mexico_locations.dtos.Response;
import dev.gerardomarquez.mexico_locations.services.MexicoLocationsServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



/*
 * Controlador de los endpoints para la informacion de las localidades de méxico
 */
@RestController
@RequestMapping("/api")
public class MexicoLocationsController {

    /*
     * Servicio que contiene la logica de negocio para las localidades de méxico
     */
    @Autowired
    private MexicoLocationsServiceI mexicoLocationsService;

    /*
     * Endpoint que devuelve todos los estados de méxico dependiendo su paginacion.
     * @param pageable Paginacion de spring.
     * @return Clase generica en donde en el data devuelve el arreglo de datos de los estados.
     */
    @GetMapping("/states")
    public ResponseEntity<Response> getAllStatesByPageable(Pageable pageable) {

        Response response = mexicoLocationsService.getAllStates(pageable);

        return ResponseEntity.ok(response);
    }
    
    /*
     * Endpoint que devuelve todos los municipios dependiendo su codigo de estado.
     * @param stateCode Codigo de estado INEGI.
     * @param pageable Paginacion de spring.
     * @return Clase generica en donde en el data devuelve el arreglo de datos de los municipios.
     */
    @GetMapping("/states/{stateCode}/municipalities")
    public ResponseEntity<Response> getAllMunicipalitiesByStateCodeAndPageable(@PathVariable("stateCode") String stateCode, Pageable pageable) {
        Response response = mexicoLocationsService.getAllMunicipalitiesByStateCode(pageable, stateCode);
        return ResponseEntity.ok(response);
    }
    
}
