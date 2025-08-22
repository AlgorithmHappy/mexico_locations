package dev.gerardomarquez.mexico_locations.services;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import dev.gerardomarquez.mexico_locations.dtos.MunicipalityDto;
import dev.gerardomarquez.mexico_locations.dtos.PageDto;
import dev.gerardomarquez.mexico_locations.dtos.Response;
import dev.gerardomarquez.mexico_locations.dtos.StatesDto;
import dev.gerardomarquez.mexico_locations.dtos.SuburbDto;
import dev.gerardomarquez.mexico_locations.dtos.ZipCodeDto;
import dev.gerardomarquez.mexico_locations.entities.States;
import dev.gerardomarquez.mexico_locations.repositories.MunicipalitiesStatesMappingRepository;
import dev.gerardomarquez.mexico_locations.repositories.StatesRepository;
import dev.gerardomarquez.mexico_locations.repositories.SuburbMunicipalitiesStatesMappingRepository;
import dev.gerardomarquez.mexico_locations.utils.Constants;

/*
 * {@inheritDoc}
 */
@Service
public class MexicoLocationsServiceImplementation implements MexicoLocationsServiceI {

    /*
     * Mensajes de messages.properties
     */
    @Autowired
    private MessageSource messageSource;

    /*
     * Repositorio de los estados
     */
    @Autowired
    private StatesRepository statesRepository;

    /*
     * Repositorio de los estados
     */
    @Autowired
    private MunicipalitiesStatesMappingRepository municipalitiesStatesMappingRepository;

    @Autowired
    private SuburbMunicipalitiesStatesMappingRepository suburbMunicipalitiesStatesMappingRepository;

    /*
    * {@inheritDoc}
    */
    @Override
    public Response getAllStates(Pageable pageable) {
        Page<States> pageStates = statesRepository.findAll(pageable);

        List<StatesDto> listStatesDto = pageStates
            .getContent()
            .stream()
            .map(
                it -> {
                    StatesDto stateDto = new StatesDto();
                    stateDto.entityToDto(it);
                    return stateDto;
                }
            )
            .collect(Collectors.toList() );

        PageDto<StatesDto> pageDto = new PageDto();
        pageDto.setContent(listStatesDto);
        pageDto.setPageNumber(pageStates.getNumber() );
        pageDto.setPageSize(pageStates.getSize() );
        pageDto.setTotalElements(pageStates.getTotalElements() );

        Response response = new Response();
        response.setData(pageDto);
        response.setSuccess(Boolean.TRUE);
        response.setMessage(messageSource.getMessage(Constants.MSG_SUCCESS, null, Locale.getDefault() ) );

        return response;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public Response getAllMunicipalitiesByStateCode(Pageable pageable, String stateCode) {
        Page<MunicipalityDto> pageMunicipalitiesDto = municipalitiesStatesMappingRepository.findMunicipiosByClaveDeEstado(stateCode, pageable);

        PageDto<MunicipalityDto> pageDto = new PageDto();
        pageDto.setContent(pageMunicipalitiesDto.getContent() );
        pageDto.setPageNumber(pageMunicipalitiesDto.getNumber() );
        pageDto.setPageSize(pageMunicipalitiesDto.getSize() );
        pageDto.setTotalElements(pageMunicipalitiesDto.getTotalElements() );

        Response response = new Response();
        response.setData(pageDto);
        response.setSuccess(Boolean.TRUE);
        response.setMessage(messageSource.getMessage(Constants.MSG_SUCCESS, null, Locale.getDefault() ) );

        return response;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public Response getAllZipCodesByStateCodeAndMunicipalityCode(
        Pageable pageable,
        String stateCode,
        String municipalityCode
    ) {
        Page<ZipCodeDto> pageZipCodeDto = suburbMunicipalitiesStatesMappingRepository.findZipCodesByStateCodeAndMunicipalityCode(
            stateCode,
            municipalityCode,
            pageable
        );

        PageDto<ZipCodeDto> pageDto = new PageDto();
        pageDto.setContent(pageZipCodeDto.getContent() );
        pageDto.setPageNumber(pageZipCodeDto.getNumber() );
        pageDto.setPageSize(pageZipCodeDto.getSize() );
        pageDto.setTotalElements(pageZipCodeDto.getTotalElements() );

        Response response = new Response();
        response.setData(pageDto);
        response.setSuccess(Boolean.TRUE);
        response.setMessage(messageSource.getMessage(Constants.MSG_SUCCESS, null, Locale.getDefault() ) );

        return response;
    }

    /*
    * {@inheritDoc}
    */
    @Override
    public Response getAllSuburbsByStateCodeAndMunicipalityCodeAndZipCode(
        Pageable pageable,
        String stateCode,
        String municipalityCode,
        String zipCode
    ) {
        Page<SuburbDto> pageSuburbDto = suburbMunicipalitiesStatesMappingRepository.findSuburbsByStateCodeAndMunicipalityCodeAndZipCode(
            stateCode,
            municipalityCode,
            zipCode,
            pageable
        );
    
        PageDto<SuburbDto> pageDto = new PageDto();
        pageDto.setContent(pageSuburbDto.getContent() );
        pageDto.setPageNumber(pageSuburbDto.getNumber() );
        pageDto.setPageSize(pageSuburbDto.getSize() );
        pageDto.setTotalElements(pageSuburbDto.getTotalElements() );

        Response response = new Response();
        response.setData(pageDto);
        response.setSuccess(Boolean.TRUE);
        response.setMessage(messageSource.getMessage(Constants.MSG_SUCCESS, null, Locale.getDefault() ) );

        return response;
    }

}
