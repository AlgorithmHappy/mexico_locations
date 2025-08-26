package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data transfer para devolver en AllInformationDto la informacion necesaria de la oficina
 */
@Data
@NoArgsConstructor
public class OfficeDto implements Serializable {

    /*
     * Codigo de oficina
     */
    private String code;
}
