package dev.gerardomarquez.mexico_locations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mexico_locations.entities.DataTextRaw;

/*
 * Repositorio para manejar las operaciones de la entidad DataTextRaw.
 */
@Repository
public interface DataTextRawRepository extends JpaRepository<DataTextRaw, Long> {

}
