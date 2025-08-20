package dev.gerardomarquez.mexico_locations.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mexico_locations.dtos.MunicipalityDto;
import dev.gerardomarquez.mexico_locations.entities.MunicipalitiesStatesMappingTable;

/*
 * Repositorio para conectarse a la base de datos y que devuevla los cruces de municipios y estados.
 */
@Repository
public interface MunicipalitiesStatesMappingRepository extends JpaRepository<MunicipalitiesStatesMappingTable, Integer> {

    /*
     * Metodo que devuelve todos los municipios dependiendo su codigo de estado.
     * @param stateCode Codigo de estado INEGI.
     * @param pageable Paginacion de spring.
     * @return Lista de municipios que pertenecen al estado devuelto en su DTO.
     */
    @Query("SELECT new dev.gerardomarquez.mexico_locations.dtos.MunicipalityDto(c.id, c.municipalityCode, c.municipalities.name) FROM MunicipalitiesStatesMappingTable c WHERE c.states.stateCode = :stateCode")
    Page<MunicipalityDto> findMunicipiosByClaveDeEstado(@Param("stateCode") String stateCode, Pageable pageable);

}
