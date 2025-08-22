package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityDto implements Serializable{

    /*
     * Id de la ciudad del catalogo
     */
    private Integer id;

    /*
     * Nombre de la ciudad
     */
    private String name;

    /*
     * Informacion de la ciudad para cada municipio
     */
    private List<CityMunicipalityMappingDto> municipalityData;

}
