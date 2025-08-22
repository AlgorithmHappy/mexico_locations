package dev.gerardomarquez.mexico_locations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.gerardomarquez.mexico_locations.entities.CitiesMunicipalitiesMappingTable;

@Repository
public interface CitiesMunicipalitiesMappingRepository extends JpaRepository<CitiesMunicipalitiesMappingTable, Integer>{

}
