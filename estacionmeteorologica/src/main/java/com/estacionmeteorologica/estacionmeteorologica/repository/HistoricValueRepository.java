package com.estacionmeteorologica.estacionmeteorologica.repository;

import org.springframework.stereotype.Repository;

import com.estacionmeteorologica.estacionmeteorologica.models.HistoricValue;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * Repositorio encargado de gestionar las operaciones de le entidad, HistoricValue
 */
@Repository
public interface HistoricValueRepository extends CrudRepository<HistoricValue, Long>{
    
    /**
     * Encuentra una lista de valores históricos filtrando por el id
     * de un sensor
     * 
     * @param id id del sensor
     * @return listado de históricos sobre el sensor
     */
    public  List<HistoricValue> findBySensorId(Long id);

    /**
     * Devuelve una lista de históricos, comprendidos entre dos fechas, 
     * de un sensor determinado
     * 
     * @param idSensor id del sensor sobre el que se filtra
     * @param initialMeasureDate fecha inicial del filtro
     * @param finalMeasureDate fecha final del filtro
     * @return listado de historicos  del sensor entre las fechas
     */
    public  List<HistoricValue> findBySensorIdAndMeasureDateBetween(Long idSensor, Date initialMeasureDate, Date finalMeasureDate);
    
    /**
     * Devuelve unicamente los valores de los históricos de un sensor
     * 
     * @param idSensor id del sensor
     * @return listado de valores de los historicos
     */
    @Query(value = "select valor from HistoricValue where sensor.id =?1")
    public List<Float> getHistoricValues(Long idSensor);

}
