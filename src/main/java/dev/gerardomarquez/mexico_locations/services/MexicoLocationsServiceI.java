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
}
