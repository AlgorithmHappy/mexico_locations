package dev.gerardomarquez.mexico_locations.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mexico_locations.entities.CitiesMunicipalitiesMappingTable;

/*
 * Repositorio que se trae las entidades de cruce entre ciudades y municipios
 */
@Repository
public interface CitiesMunicipalitiesMappingRepository extends JpaRepository<CitiesMunicipalitiesMappingTable, Integer>{

    /*
     * Metodo que devuelve las ciudades por codigo de estado.
     * @param Codigo de estado del INEGI.
     * @param pageable de spring.
     * @return Page del cruce de ciudades y municipios.
     */
    public Page<CitiesMunicipalitiesMappingTable> findByMunicipalityStatesStateCode(
        @Param("stateCode") String stateCode,
        Pageable pageable
    );

    /*
     * Metodo que devuelve las ciudades por codigo de estado y municipio.
     * @param Codigo de estado del INEGI.
     * @param Codigo de municipio por cada estado.
     * @param pageable de spring.
     * @return Page del cruce de ciudades y municipios.
     */
    public Page<CitiesMunicipalitiesMappingTable> findByMunicipalityStatesStateCodeAndMunicipalityMunicipalityCode(
        @Param("stateCode") String stateCode,
        @Param("municipalityCode") String municipalityCode,
        Pageable pageable
    );

}
