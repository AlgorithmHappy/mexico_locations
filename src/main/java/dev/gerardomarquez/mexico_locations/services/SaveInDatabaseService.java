package dev.gerardomarquez.mexico_locations.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.gerardomarquez.mexico_locations.entities.DataTextRaw;
import dev.gerardomarquez.mexico_locations.repositories.DataTextRawRepository;

/*
 * Clase de servicio que guarda los datos en la base de datos.
 */
@Service
public class SaveInDatabaseService {

    /*
     * Repositorio para manejar las operaciones de la entidad DataTextRaw.
     */
    @Autowired
    private DataTextRawRepository dataTextRawRepository;

    /*
     * Método para guardar una lista de entidades DataTextRaw en la base de datos.
     * @param dataTextRawList Lista de entidades DataTextRaw a guardar.
     */
    public void saveDataTextRawAll(List<DataTextRaw> dataTextRawList) {
        dataTextRawRepository.saveAll(dataTextRawList);
    }
}
