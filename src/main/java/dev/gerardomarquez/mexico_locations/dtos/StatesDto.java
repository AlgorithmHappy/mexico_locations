package dev.gerardomarquez.mexico_locations.dtos;

import java.io.Serializable;

import dev.gerardomarquez.mexico_locations.entities.States;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Dto que transfiere los datos de la entidad al cliente de la informacion de los estados
 */
@Data
@NoArgsConstructor
public class StatesDto implements Serializable{

    /*
     * Identificador único de cada Estado de méxico.
     */
    private Short id;

    /*
     * Codigo de estado que le puso el INEGI.
     */
    private String stateCode;

    /*
     * Nombre del estado.
     */
    private String name;

    /*
     * Convierte una entidad States a un dto States.
     * @param state Entidad.
     * @return Dto de states.
     */
    public StatesDto entityToDto(States state){
        if(state == null)
            throw new IllegalArgumentException();
        if(state.getId() == null || state.getName() == null || state.getStateCode() == null)
            throw new IllegalArgumentException();
        this.id = state.getId();
        this.stateCode = state.getStateCode();
        this.name = state.getName();

        return this;
    }
}
