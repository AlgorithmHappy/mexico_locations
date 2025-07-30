package dev.gerardomarquez.mexico_locations.configurations;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import dev.gerardomarquez.mexico_locations.steps.ItemDecompressFileStep;
import dev.gerardomarquez.mexico_locations.steps.ItemDownloadZipStep;
import dev.gerardomarquez.mexico_locations.steps.ItemLoadStep;
import dev.gerardomarquez.mexico_locations.steps.ItemPrepareFileSystemStep;
import dev.gerardomarquez.mexico_locations.steps.ItemReadFileStep;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * Clase de configuración del batch.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    /*
     * Paso para preparar el sistema de archivos, asegurando que las carpetas estén limpias
     */
    @Autowired
    private ItemPrepareFileSystemStep itemPrepareFileSystemStep;

    /*
     * Paso para descargar el archivo .zip de las localidades de México
     */
    @Autowired
    private ItemDownloadZipStep itemDownloadZipStep;

    /*
     * Paso para descomprimir el archivo descargado de las localidades de México
     */
    @Autowired
    private ItemDecompressFileStep itemDecompressFileStep;

    /*
     * Paso para leer el archivo .txt de las localidades de México
     */
    @Autowired
    private ItemReadFileStep itemReadFileStep;

    /*
     * Paso para cargar los datos leídos en la base de datos
     */
    @Autowired
    private ItemLoadStep itemLoadStep;

    /*
     * Repositorio de trabajos de Spring Batch.
     */
    private final JobRepository jobRepository;

    /*
     * Administrador de transacciones de Spring.
     */
    private final PlatformTransactionManager transactionManager;

    /*
     * Inyección por constructor de JobRepository y PlatformTransactionManager.
     * @param jobRepository Repositorio de trabajos de Spring Batch.
     * @param transactionManager Administrador de transacciones de Spring.
     */
    public BatchConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    /*
     * Se crea el primer paso que consiste en dejar limpia las carpetas donde se van a descargar
     * @return El paso de preparación del sistema de archivos
     */
    @Bean
    public Step itemPrepareFileSystemStepBean() {
        return new StepBuilder("itemPrepareFileSystemStepBean", jobRepository)
            .tasklet(itemPrepareFileSystemStep, transactionManager )
            .build();
    }

    /*
     * Se crea el segundo paso que consiste en descargar el archivo .zip de las localidades de México
     * @return El paso de descarga del archivo zip
     */
    @Bean
    public Step itemDownloadZipStepBean() {
        return new StepBuilder("itemDownloadZipStepBean", jobRepository)
            .tasklet(itemDownloadZipStep, transactionManager )
            .build();
    }

    /*
     * Se crea el tercer paso que consiste en descomprimir el archivo descargado de las localidades de México
     * @return El paso de descompresión del archivo zip
     */
    @Bean
    public Step itemDecompressFileStepBean() {
        return new StepBuilder("itemDecompressFileStepBean", jobRepository)
            .tasklet(itemDecompressFileStep, transactionManager )
            .build();
    }

    /*
     * Se crea el cuarto paso que consiste en leer el archivo .txt de las localidades de México
     * @return El paso de lectura del archivo de texto
     */
    @Bean
    public Step itemReadFileStepBean() {
        return new StepBuilder("itemReadFileStepBean", jobRepository)
            .tasklet(itemReadFileStep, transactionManager )
            .build();
    }

    /*
     * Se crea el quinto paso que consiste en cargar los datos leídos en la base de datos
     * @return El paso de carga de datos
     */
    @Bean
    public Step itemLoadStepBean() {
        return new StepBuilder("itemLoadStepBean", jobRepository)
            .tasklet(itemLoadStep, transactionManager )
            .build();
    }

    @Bean
    public Job jobMexicoLocationsBean() {
        return new JobBuilder("jobMexicoLocationsBean", jobRepository)
            .start(itemPrepareFileSystemStepBean() )
            .next(itemDownloadZipStepBean() )
            .next(itemDecompressFileStepBean() )
            .next(itemReadFileStepBean() )
            .next(itemLoadStepBean() )
            .build();
    }
}
