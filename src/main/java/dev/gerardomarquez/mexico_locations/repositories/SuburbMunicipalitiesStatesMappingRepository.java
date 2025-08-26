package dev.gerardomarquez.mexico_locations.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mexico_locations.dtos.SuburbDto;
import dev.gerardomarquez.mexico_locations.dtos.ZipCodeDto;
import dev.gerardomarquez.mexico_locations.entities.SuburbMunicipalitiesStatesMappingTable;
import dev.gerardomarquez.mexico_locations.repositories.projections.AllInformationProjection;

/*
 * Repositorio que se conecta a la base de datos para traer los cruces de los asentamientos
 */
@Repository
public interface SuburbMunicipalitiesStatesMappingRepository extends JpaRepository<SuburbMunicipalitiesStatesMappingTable, Integer>{

    /*
     * Metodo que devuelve los codigos postales a partir de el codigo del estado y el codigo del municipio
     * @param stateCode Codigo de estado.
     * @param municipalityCode Codigo de municipio.
     * @param pageable Objeto de tipo paginacion de spring.
     * @return Devuelve un paginado de spring de los dto de los codigos postales
     */
    @Query("""
        SELECT new dev.gerardomarquez.mexico_locations.dtos.ZipCodeDto(s.zipCode.id, s.zipCode.zipCode, s.zipCode.officeZipCode.officeCode)
        FROM SuburbMunicipalitiesStatesMappingTable s WHERE s.municipalityStateMappingTable.municipalityCode = :municipalityCode AND
        s.municipalityStateMappingTable.states.stateCode = :stateCode
    """)
    public Page<ZipCodeDto> findZipCodesByStateCodeAndMunicipalityCode(
        @Param("stateCode") String stateCode,
        @Param("municipalityCode") String municipalityCode,
        Pageable pageable
    );

    /*
     * Metodo que devuelve todos los asentamientos por el codigo de estado, codigo de municipio y codigo postal
     * @param stateCode codigo de estado.
     * @param municipalityCode Codigo del municipio que le otorga el estado.
     * @param zipCode Codigo postal.
     * @return pageable Paginado de Srping.
     */
    @Query("""
        SELECT new dev.gerardomarquez.mexico_locations.dtos.SuburbDto(
            s.id, s.suburbCode, s.suburb.name, s.kindOfSuburb.kindOfSuburbCode, s.kindOfSuburb.kindOfSuburb, s.zone.id, s.zone.zone
        ) FROM SuburbMunicipalitiesStatesMappingTable s WHERE s.municipalityStateMappingTable.states.stateCode = :stateCode AND
        s.municipalityStateMappingTable.municipalityCode = :municipalityCode AND zipCode.zipCode = :zipCode
    """)
    public Page<SuburbDto> findSuburbsByStateCodeAndMunicipalityCodeAndZipCode(
        @Param("stateCode") String stateCode,
        @Param("municipalityCode") String municipalityCode,
        @Param("zipCode") String zipCode,
        Pageable pageable
    );

    /*
     * Metodo que devuevle la proyeccion con toda la informacion de las localidades de mexico.
     * @param Codigo postal.
     * @param Paginado de spring.
     * @param pageable Paginado de Srping.
     */
    public Page<AllInformationProjection> findByZipCodeZipCode(
        String zipCode,
        Pageable pageable
    );
}
