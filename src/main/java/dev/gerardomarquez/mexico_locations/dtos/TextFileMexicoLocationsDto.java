package dev.gerardomarquez.mexico_locations.dtos;

import dev.gerardomarquez.mexico_locations.entities.DataTextRaw;
import lombok.Builder;
import lombok.Data;

/*
 * Archivo que debe contener la estructura de los datos del archivo de texto de localidades de México.
 */
@Data
@Builder
public class TextFileMexicoLocationsDto {
    private String d_codigo; // Código Postal asentamiento
    private String d_asenta; // Nombre asentamiento
    private String d_tipo_asenta; // Tipo de asentamiento (Catálogo SEPOMEX)
    private String D_mnpio; // Nombre Municipio (INEGI, Marzo 2013)
    private String d_estado; // Nombre Entidad (INEGI, Marzo 2013)
    private String d_ciudad; // Nombre Ciudad (Catálogo SEPOMEX)
    private String d_CP; // Código Postal de la Administración Postal que reparte al asentamiento
    private String c_estado; // Clave Entidad (INEGI, Marzo 2013)
    private String c_oficina; // Código Postal de la Administración Postal que reparte al asentamiento
    private String c_tipo_asenta; // Clave Tipo de asentamiento (Catálogo SEPOMEX)
    private String c_mnpio; // Clave Municipio (INEGI, Marzo 2013)
    private String id_asenta_cpcons; // Identificador único del asentamiento (nivel municipal)
    private String d_zona; // Zona en la que se ubica el asentamiento (Urbano/Rural)
    private String c_cve_ciudad; // Clave Ciudad (Catálogo SEPOMEX)
    private String c_CP; // Campo Vacio

    public DataTextRaw toEntity() {
        // Aquí puedes implementar la lógica para convertir este DTO a una entidad si es necesario
        return null; // Placeholder, implementa según tu lógica
    }
}
