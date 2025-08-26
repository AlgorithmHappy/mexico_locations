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
    
    /*
     * Endpoint que devuelve todos los codigos postales dependiendo su codigo de estado y municipio.
     * @param stateCode Codigo de estado INEGI.
     * @param municipalityCode Codigo de municipio que le asigno el estado.
     * @param pageable Paginacion de spring.
     * @return Clase generica en donde en el data devuelve el arreglo de codigos postales.
     */
    @GetMapping("/states/{stateCode}/municipalities/{municipalityCode}/zipcodes")
    public ResponseEntity<Response> getAllZipCodesByStateCodeAndMunicipalityCodeAndPageable(
        @PathVariable("stateCode") String stateCode,
        @PathVariable("municipalityCode") String municipalityCode,
        Pageable pageable
    ) {
        Response response = mexicoLocationsService.getAllZipCodesByStateCodeAndMunicipalityCode(pageable, stateCode, municipalityCode);
        return ResponseEntity.ok(response);
    }

    /*
     * Endpoint que devuelve todos los asentamientos dependiendo su codigo de estado, municipio y codigo postal.
     * @param stateCode Codigo de estado INEGI.
     * @param municipalityCode Codigo de municipio que le asigno el estado.
     * @param zipCode Codigo postal.
     * @param pageable Paginacion de spring.
     * @return Clase generica en donde en el data devuelve el arreglo de asentamientos.
     */
    @GetMapping("/states/{stateCode}/municipalities/{municipalityCode}/zipcodes/{zipCode}")
    public ResponseEntity<Response> getAllSuburbsByStatecodeAndMunicipalityCodeAndZipCodeAndPageable(
        @PathVariable("stateCode") String stateCode,
        @PathVariable("municipalityCode") String municipalityCode,
        @PathVariable("zipCode") String zipCode,
        Pageable pageable
    ) {
        Response response = mexicoLocationsService.getAllSuburbsByStateCodeAndMunicipalityCodeAndZipCode(pageable, stateCode, municipalityCode, zipCode);
        return ResponseEntity.ok(response);
    }
    
    /*
     * Endpoint que devuelve todas las ciudades de mexico con su respectiva informacion de municipios.
     * @param pageable Paginacion de spring.
     * @return Clase generica en donde en el data devuelve el arreglo de ciudades.
     */
    @GetMapping("/cities")
    public ResponseEntity<Response> getMethodName(Pageable pageable) {

        Response response = mexicoLocationsService.getAllCities(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/states/{stateCode}/cities")
    public ResponseEntity<Response> getAllCitiesByStatecodeAndPageable(
        @PathVariable("stateCode") String stateCode,
        Pageable pageable
    ) {
        Response response = mexicoLocationsService.getAllCitiesByStateCode(pageable, stateCode);

        return ResponseEntity.ok(response);
    }
    
    /*
     * Endpoint que devuelve todas las ciudades de mexico dependiendo el codigo de estado y de muncipio.
     * @param stateCode Codigo de estado segun la INEGI.
     * @param municipalityCode Codigo de municipio segun el estado
     * @param pageable Paginacion de spring.
     * @return Clase generica en donde en el data devuelve el arreglo de ciudades.
     */
    @GetMapping("/states/{stateCode}/municipalities/{municipalityCode}/cities")
    public ResponseEntity<Response> getMethodName(
        @PathVariable("stateCode") String stateCode,
        @PathVariable("municipalityCode") String municipalityCode,
        Pageable pageable
    ) {
        Response response = mexicoLocationsService.getAllCitiesByStateCodeAndMunicipalityCode(pageable, stateCode, municipalityCode);
        
        return ResponseEntity.ok().body(response);
    }
    
}
