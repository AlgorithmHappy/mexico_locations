package dev.gerardomarquez.mexico_locations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mexico_locations.entities.Cities;

/*
 * Repositorio que devuelve el catalogo de ciudades
 */
@Repository
public interface CitiesRepository extends JpaRepository<Cities, Integer>{

}
