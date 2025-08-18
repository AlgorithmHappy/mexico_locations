package dev.gerardomarquez.mexico_locations.dtos;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import dev.gerardomarquez.mexico_locations.entities.DataTextRaw;

/*
 * Clase que implementa de spring batch para convertir el dto a entity y que pase transparente en spring batch
 */
@Component
public class TextFileMexicoDtoToEntityDataTextRawItem implements ItemProcessor<TextFileMexicoLocationsDto, DataTextRaw> {

    /*
     * @{inheritDoc}
     * Metodo que convierte el dto a entidad
     */
    @Override
    public DataTextRaw process(TextFileMexicoLocationsDto item) throws Exception {
        return item.toEntity();
    }

}
