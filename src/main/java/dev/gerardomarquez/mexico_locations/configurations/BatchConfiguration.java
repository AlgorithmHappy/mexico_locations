package dev.gerardomarquez.mexico_locations.configurations;

import java.io.File;
import java.nio.charset.Charset;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import dev.gerardomarquez.mexico_locations.dtos.TextFileMexicoLocationsDto;
import dev.gerardomarquez.mexico_locations.entities.DataTextRaw;
import dev.gerardomarquez.mexico_locations.steps.*;
import dev.gerardomarquez.mexico_locations.utils.Constants;
import jakarta.persistence.EntityManagerFactory;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/*
 * Clase de configuración del batch.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final ItemDistributeStoreProcedureStep itemDistributeStoreProcedureStep;

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
    @Value("${decompress.file.separator}")
    private String fileSeparator;

    @Value("${decompress.file.encoding}")
    private String fileEncoding;

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

    private final EntityManagerFactory entityManagerFactory;

    /*
     * Inyección por constructor de JobRepository y PlatformTransactionManager.
     * @param jobRepository Repositorio de trabajos de Spring Batch.
     * @param transactionManager Administrador de transacciones de Spring.
     */
    public BatchConfiguration(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemDistributeStoreProcedureStep itemDistributeStoreProcedureStep,
        EntityManagerFactory entityManagerFactory
    ) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.itemDistributeStoreProcedureStep = itemDistributeStoreProcedureStep;
        this.entityManagerFactory = entityManagerFactory;
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

    /*
     * Se crea el quinto paso que consiste en cargar los datos leídos en la base de datos
     * @return El paso de carga de datos
     */
    @Bean
    public Step itemDistributeSotreProcedureStepBean() {
        return new StepBuilder("itemDistributeSotreProcedureStepBean", jobRepository)
            .tasklet(itemDistributeStoreProcedureStep, transactionManager )
            .build();
    }

    /*
     * Lectura de las locaciones de mexico desde spring batch con su "Item"
     * @return FlatFileItemReader<TextFileMexicoLocationsDto> Lista de los renglones ya transformados a la clase TextFileMexicoLocationsDto
     */
    @Bean
    public FlatFileItemReader<TextFileMexicoLocationsDto> itemReader() {
        String fullPath = filePath + File.separator + fileName + fileExtension;
        return new FlatFileItemReaderBuilder<TextFileMexicoLocationsDto>()
            .name(Constants.NAME_ITEM_READER)
            .encoding(fileEncoding)
            .resource(new ClassPathResource(fullPath) )
            .linesToSkip(Constants.GLOBAL_LINES_TO_SKIP)
            .delimited()
            .delimiter(fileSeparator)
            .names(
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[0],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[1],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[2],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[3],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[4],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[5],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[6],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[7],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[8],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[9],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[10],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[11],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[12],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[13],
                Constants.TEXT_FILE_MEXICO_LOCATIONS_COLUMNS_ARRAY[14]
            )
            .targetType(TextFileMexicoLocationsDto.class)
            .build();
    }

    /*
     * Metodo que guarda todo las entidades en la base de datos
     * @return JpaItemWriter<DataTextRaw> Lista de entidades
     */
    @Bean
    public JpaItemWriter<DataTextRaw> itemWriter() {
        JpaItemWriter<DataTextRaw> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(true); // usa persist en vez de merge
        return writer;
    }

    /*
     * paso que une el reader y el writer de spring batch para leer los datos del txt e insertarlos a
     * la base de datos
     */
    @Bean
    public Step itemReadAndSaveDataStepBean(
        ItemReader<TextFileMexicoLocationsDto> reader, 
        ItemProcessor<TextFileMexicoLocationsDto, DataTextRaw> processor,
        ItemWriter<DataTextRaw> writer
    ) {
        return new StepBuilder("itemReadAndSaveDataStepBean", jobRepository)
            .<TextFileMexicoLocationsDto, DataTextRaw>chunk(1000, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    /*
     * step para pausar y dejer un tiempo para que el paso de guardar la informacion tome bien el archivo
     * ya que si se hace al instante da un error
     */
    @Bean
    public Step stepPausa() {
        return new StepBuilder("stepPausa", jobRepository)
            .tasklet((contribution, chunkContext) -> {
                Thread.sleep(5000);
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }

     /*
     * Creacion de todo el "Job" todo el procedimiento del batch
     * @return Job Job de spring batch
     */
    @Bean
    public Job jobMexicoLocationsBean(
        FlatFileItemReader<TextFileMexicoLocationsDto> itemReader,
        ItemProcessor<TextFileMexicoLocationsDto, DataTextRaw> processor,
        JpaItemWriter<DataTextRaw> itemWriter
    ) {
        return new JobBuilder("jobMexicoLocationsBean", jobRepository)
            .start(itemPrepareFileSystemStepBean() )
            .next(itemDownloadZipStepBean() )
            .next(itemDecompressFileStepBean() )
            .next(stepPausa() )
            /*.next(itemReadFileStepBean() )
            .next(itemLoadStepBean() )*/
            .next(itemReadAndSaveDataStepBean(itemReader, processor, itemWriter) )
            .next(itemDistributeSotreProcedureStepBean() )
            .build();
    }
}
