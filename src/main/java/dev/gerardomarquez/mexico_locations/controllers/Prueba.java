package dev.gerardomarquez.mexico_locations.controllers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Prueba {

    private final JobLauncher jobLauncher;
    private final Job miJob;

    public Prueba(JobLauncher jobLauncher, Job miJob) {
        this.jobLauncher = jobLauncher;
        this.miJob = miJob;
    }

    // Se ejecuta cada 60 segundos
    @Scheduled(fixedDelay = 36000000)
    public void ejecutarJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis()) // para que cada ejecución sea única
                .toJobParameters();

            jobLauncher.run(miJob, params);
            System.out.println("Job ejecutado correctamente");
        } catch (Exception e) {
            System.out.println("Error al ejecutar el job: " + e.getMessage());
            e.printStackTrace();
        }
        //System.out.println("No se ejecuta solo quiero levantar el servicio rest");
    }
}
