package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data transfer para devolver en AllInformationDto la informacion necesaria del estado
 */
@Data
@NoArgsConstructor
public class StateWithoutIdDto implements Serializable {

    /*
     * Codigo del estado al que perteneces
     */
    private String code;

    /*
     * Nombre del estado
     */
    private String name;
}
