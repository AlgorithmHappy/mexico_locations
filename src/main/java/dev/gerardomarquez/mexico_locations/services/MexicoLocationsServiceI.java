package dev.gerardomarquez.mexico_locations.services;

import org.springframework.data.domain.Pageable;

import dev.gerardomarquez.mexico_locations.dtos.Response;

/*
 * Interfaz que define los metodos a utilizar en el servicio para encontrar la direccion del cliente.
 */
public interface MexicoLocationsServiceI {

    /*
     * Metodo que devuelve todos los estados de méxico dependiendo su paginacion.
     * @param pageable Paginacion de spring.
     * @return Clase generica en donde en el data devuelve la "Page" de los estados.
     */
    public Response getAllStates(Pageable pageable);

    /*
     * Metodo que devuelve todos los municipios dependiendo su codigo de estado.
     * @param pageable Paginacion de spring.
     * @param stateCode Codigo de estado INEGI.
     * @return Clase generica en donde en el data devuelve la "PageDto" de los estados.
     */
    public Response getAllMunicipalitiesByStateCode(Pageable pageable, String stateCode);

    /*
     * Metodo que devuelve todos los codigos postales de un municipio en especifico de un estado.
     * @param pageable Paginacion de spring.
     * @param stateCode Codigo de estado INEGI.
     * @param municipalityCode Codigo de municipio que le asigno el estado para el municipio/delegacion.
     * @return Clase generica en donde en el data devuelve la "PageDto" de los codigos postales
     */
    public Response getAllZipCodesByStateCodeAndMunicipalityCode(Pageable pageable, String stateCode, String municipalityCode);

    /*
     * Metodo que devuelve todos los asentamientos de un codigo postal de un municipio y de un estado en especifico.
     * @param pageable Paginacion de spring.
     * @param stateCode Codigo de estado INEGI.
     * @param municipalityCode Codigo de municipio que le asigno el estado para el municipio/delegacion.
     * @param zipCode Codigo postal.
     * @return Clase generica en donde en el data devuelve la "PageDto" de los codigos postales
     */
    public Response getAllSuburbsByStateCodeAndMunicipalityCodeAndZipCode(Pageable pageable, String stateCode, String municipalityCode, String zipCode);

    /*
     * Metodo que devuelve todas las ciudades
     * @param pageable Paginacion de spring.
     */
    public Response getAllCities(Pageable pageable);

    /*
     * Metodo que devuelve todas las ciudades por codigo de estado.
     * @param pageable Paginacion de spring.
     * @param stateCode codigo de estado.
     * @return Clase generica en donde en el data devuelve la "PageDto" de los codigos postales
     */
    public Response getAllCitiesByStateCode(Pageable pageable, String stateCode);

    /*
     * Metodo que devuelve todas las ciudades por codigo de estado y codigo de municipio.
     * @param pageable Paginacion de spring.
     * @param stateCode codigo de estado.
     * @param municipalityCdoe codigo de municipio que le asigna el estado.
     * @return Clase generica en donde en el data devuelve la "PageDto" de los codigos postales
     */
    public Response getAllCitiesByStateCodeAndMunicipalityCode(Pageable pageable, String stateCode, String municipalityCdoe);

    /*
     * Metodo que devuelve todas los asentamientos por codigo postal.
     * @param pageable Paginacion de spring.
     * @param zipCode codigo postal.
     * @return Clase generica en donde en el data devuelve la "PageDto" de los codigos postales
     */
    public Response getAllCitiesByStateCodeAndMunicipalityCode(Pageable pageable, String zipCode);
}
