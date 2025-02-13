package com.estacionmeteorologica.estacionmeteorologica.services.historicValue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.estacionmeteorologica.estacionmeteorologica.models.HistoricValue;
/**
 * Servicio de la entidad de HistoricValue
 */
public interface HistoricValueService {
    /**
     * Elimina históricos con el id del sensor
     * @param idSensor id del sensor, del que se quieren eliminar
     * los registros
     */
    void deleteHistoricValuesWithSensorId(Long idSensor);

    /**
     * Encuentra una lista de valores históricos filtrando por el id
     * de un sensor
     * 
     * @param id id del sensor
     * @return listado de históricos sobre el sensor
     */
    List<HistoricValue> getHistoricValuesBySensorId(Long idSensor);

    /**
     * Devuelve una lista de históricos, comprendidos entre dos fechas, 
     * de un sensor determinado
     * 
     * @param idSensor id del sensor sobre el que se filtra
     * @param initialMeasureDate fecha inicial del filtro
     * @param finalMeasureDate fecha final del filtro
     * @return listado de historicos  del sensor entre las fechas
     */
    List<HistoricValue> getHistoricValuesBySensorIdAndMeasureDateBetween(Long idSensor, Date initialMeasureDate, Date finalMeasureDate);
    
    /**
    * Devuelve la media de los históricos, comprendidos entre dos fechas, 
    * de un sensor determinado
    * 
    * @param idSensor id del sensor sobre el que se filtra
    * @param initialMeasureDate fecha inicial del filtro
    * @param finalMeasureDate fecha final del filtro
    * @return media de los historicos filtrados
    */
    Optional<Double> getAverageValueBySensorIdAndMeasureDateBetween(Long idSensor, Date initialMeasureDate, Date finalMeasureDate);
    
    /**
     * Devuelve unicamente los valores de los históricos de un sensor
     * 
     * @param idSensor id del sensor
     * @return listado de valores de los historicos
     */
    List<Float> getHistoricValues(Long idSensor);
}
