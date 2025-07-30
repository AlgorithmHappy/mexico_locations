package dev.gerardomarquez.mexico_locations.steps;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.gerardomarquez.mexico_locations.dtos.TextFileMexicoLocationsDto;
import dev.gerardomarquez.mexico_locations.entities.DataTextRaw;
import dev.gerardomarquez.mexico_locations.services.SaveInDatabaseService;
import dev.gerardomarquez.mexico_locations.utils.Constants;
import lombok.extern.log4j.Log4j2;

/*
 * En esta clase se define el quinto paso en el que se cargara lo leido del archivo .txt en la
 * base de datos.
 */
@Component
@Log4j2
public class ItemLoadStep implements Tasklet {

    /*
     * Servicio para guardar los datos en la base de datos.
     */
    @Autowired
    private SaveInDatabaseService saveInDatabaseService;

    /*
     * {@inheritDoc}
     * Este paso se encarga de cargar los datos leídos del archivo .txt a la base de datos.
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<TextFileMexicoLocationsDto> textFileMexicoLocationsList = (List<TextFileMexicoLocationsDto>) chunkContext
            .getStepContext()
            .getStepExecution()
            .getJobExecution()
            .getExecutionContext()
            .get(Constants.CONTEXT_LIST_MEXICO_LOCATIONS);

        List<DataTextRaw> dataTextRawList = textFileMexicoLocationsList.stream()
            .map(TextFileMexicoLocationsDto::toEntity)
            .toList();
        
        saveInDatabaseService.saveDataTextRawAll(dataTextRawList);
        
        return RepeatStatus.FINISHED;
    }
}
