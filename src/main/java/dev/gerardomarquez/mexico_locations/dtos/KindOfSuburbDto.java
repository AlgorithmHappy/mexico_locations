package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data transfer para devolver en AllInformationDto la informacion necesaria del tipo de oficina
 */
@Data
@NoArgsConstructor
public class KindOfSuburbDto implements Serializable {

    /*
     * Codigo del tipo de suburbio
     */
    private String code;

    /*
     * Tipo de suburbio (nombre)
     */
    private String name;
}
