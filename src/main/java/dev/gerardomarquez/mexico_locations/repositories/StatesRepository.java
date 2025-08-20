package dev.gerardomarquez.mexico_locations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mexico_locations.entities.States;

/*
 * Repositorio para conectarse a la base de datos y que devuevla los estados de México.
 */
@Repository
public interface StatesRepository extends JpaRepository<States, Short> {   
}