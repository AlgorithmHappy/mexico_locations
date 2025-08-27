package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityWithoutIdDto implements Serializable{

    /*
     * Codigo de la ciudad segun el municipio
     */
    private String code;

    /*
     * Nombre de la ciudad
     */
    private String name;
}
