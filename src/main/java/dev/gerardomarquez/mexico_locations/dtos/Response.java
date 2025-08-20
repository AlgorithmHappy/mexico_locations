package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Clase base para las respuestas de la API.
 */
@Data
@NoArgsConstructor
public class Response implements Serializable {

    /*
     * Indica si la respuesta fue exitosa o no.
     */
    private Boolean success;

    /*
     * Mensaje de la respuesta, puede ser un mensaje de error o éxito.
     */
    private String message;

    /*
     * Datos de la respuesta, puede ser cualquier objeto que se desee retornar.
     */
    private Object data;
}
