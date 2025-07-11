package dev.gerardomarquez.mexico_locations.steps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import dev.gerardomarquez.mexico_locations.dtos.TextFileMexicoLocationsDto;
import dev.gerardomarquez.mexico_locations.utils.Constants;
import lombok.extern.log4j.Log4j2;

/*
 * En esta clase se define el cuarto paso en el que se leera el archivo .txt de las localidades
 * de México
 */
@Log4j2
public class ItemReadFileStep implements Tasklet {

    /*
     * Ruta al directorio donde se guardarán los archivos descomprimidos.
     */
    @Value("${decompress.file.path}")
    private String filePath;

    /*
     * Nombre del archivo descargado que se descomprimirá.
     */
    @Value("${decompress.file.name}")
    private String fileName;

    /*
     * Extensión del archivo descargado que se descomprimirá.
     */
    @Value("${decompress.file.extension}")
    private String fileExtension;

    /*
     * Separador de archivos utilizado en el archivo de texto.
     */
    @Value("${decompress.files.separator}")
    private String fileSeparator;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String fullFilePathName = filePath + File.separator + fileName + fileExtension; // Asegúrate de que este archivo esté en la misma carpeta que tu clase Java o proporciona la ruta completa
        List<TextFileMexicoLocationsDto> locations = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fullFilePathName))) {
            String line;
            boolean isFirstLine = Boolean.TRUE; // Para saltar la línea de descripción
            boolean isSecondLine = Boolean.TRUE; // Para saltar la línea del encabezado

            Integer lineNumber = Constants.GLOBAL_ZERO; // Contador de líneas para advertencias
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (isFirstLine) {
                    isFirstLine = Boolean.FALSE;
                    continue; // Saltar la primera línea (descripción)
                }
                if( isSecondLine) {
                    isSecondLine = Boolean.FALSE;
                    continue; // Saltar la segunda línea (encabezado)
                }

                String[] parts = line.split(fileSeparator);

                if (parts.length >= Constants.TEXT_FILE_SIZE_COLUMNS) {
                    TextFileMexicoLocationsDto location = TextFileMexicoLocationsDto.builder()
                        .d_codigo(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("d_codigo")].trim() ) // d_codigo
                        .d_asenta(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("d_asenta")].trim() ) // d_asenta
                        .d_tipo_asenta(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("d_tipo_asenta")].trim() ) // d_tipo_asenta
                        .D_mnpio(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("D_mnpio")].trim() ) // D_mnpio
                        .d_estado(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("d_estado")].trim() ) // d_estado
                        .d_ciudad(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("d_ciudad")].trim() ) // d_ciudad
                        .d_CP(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("d_CP")].trim() ) // d_CP
                        .c_estado(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("c_estado")].trim() ) // c_estado
                        .c_oficina(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("c_oficina")].trim() ) // c_oficina
                        .c_tipo_asenta(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("c_tipo_asenta")].trim() ) // c_tipo_asenta
                        .c_mnpio(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("c_mnpio")].trim() ) // c_mnpio
                        .id_asenta_cpcons(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("id_asenta_cpcons")].trim() ) // id_asenta_cpcons
                        .d_zona(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("d_zona")].trim() ) // d_zona
                        .c_cve_ciudad(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("c_cve_ciudad")].trim() ) // c_cve_ciudad
                        .c_CP(parts[Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS.get("c_CP")].trim() ) // c_CP_final
                        .build();
                    
                    locations.add(location);
                } else {
                    log.warn(String.format(Constants.MSG_WARNING_INVALID_LINE, ++lineNumber, line) );
                }
            }
        } catch (IOException e) {
            log.error(String.format(Constants.MSG_ERROR_READ_TEXT_FILE, e.getMessage() ) );
        }

        return RepeatStatus.FINISHED;
    }

}
