package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import dev.gerardomarquez.mexico_locations.entities.DataTextRaw;
import lombok.Builder;
import lombok.Data;

/*
 * Archivo que debe contener la estructura de los datos del archivo de texto de localidades de México.
 */
@Data
@Builder
public class TextFileMexicoLocationsDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
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
        return DataTextRaw.builder()
            .d_codigo(this.d_codigo)
            .d_asenta(this.d_asenta)
            .d_tipo_asenta(this.d_tipo_asenta)
            .D_mnpio(this.D_mnpio)
            .d_estado(this.d_estado)
            .d_ciudad(this.d_ciudad)
            .d_CP(this.d_CP)
            .c_estado(this.c_estado)
            .c_oficina(this.c_oficina)
            .c_tipo_asenta(this.c_tipo_asenta)
            .c_mnpio(this.c_mnpio)
            .id_asenta_cpcons(this.id_asenta_cpcons)
            .d_zona(this.d_zona)
            .c_cve_ciudad(this.c_cve_ciudad)
            .c_CP(this.c_CP)
            .build();
    }
}
