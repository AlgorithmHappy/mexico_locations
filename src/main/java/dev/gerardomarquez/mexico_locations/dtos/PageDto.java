package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Dto que se devolvera en el response en el caso de que sea un get pageable
 */
@Data
@NoArgsConstructor
public class PageDto<T> implements Serializable{
    /*
     * Lista con el contenido del DTO de la entidad
     */
    private List<T> content;

    /*
     * Numero de pagina actual
     */
    private Integer pageNumber;

    /*
     * Tamaño de pagina actual
     */
    private Integer pageSize;

    /*
     * Cantidad total de todos los elementos
     */
    private Long totalElements;

}
