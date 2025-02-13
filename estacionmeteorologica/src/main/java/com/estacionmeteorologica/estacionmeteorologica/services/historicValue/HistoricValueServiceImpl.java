package com.estacionmeteorologica.estacionmeteorologica.services.historicValue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.estacionmeteorologica.estacionmeteorologica.repository.HistoricValueRepository;
import com.estacionmeteorologica.estacionmeteorologica.models.HistoricValue;

import lombok.AllArgsConstructor;

/**
 * Implementación del servicio de {@link HistoricValueService}. Es la encargada
 * de gestionar las operaciones de la entidad HistoricValue
 */
@AllArgsConstructor
@Service
public class HistoricValueServiceImpl implements HistoricValueService{
    private final HistoricValueRepository historicValueRepository;

    /**
     * Devuelve una lista de históricos, comprendidos entre dos fechas, 
     * de un sensor determinado
     * 
     * @param idSensor id del sensor sobre el que se filtra
     * @param initialMeasureDate fecha inicial del filtro
     * @param finalMeasureDate fecha final del filtro
     * @return listado de historicos  del sensor entre las fechas
     */
    @Override
    public List<HistoricValue> getHistoricValuesBySensorIdAndMeasureDateBetween(Long idSensor, Date initialMeasureDate,
            Date finalMeasureDate) {
        return historicValueRepository.findBySensorIdAndMeasureDateBetween(idSensor, initialMeasureDate, finalMeasureDate);
    }
    
    /**
    * Devuelve la media de los históricos, comprendidos entre dos fechas, 
    * de un sensor determinado
    * 
    * @param idSensor id del sensor sobre el que se filtra
    * @param initialMeasureDate fecha inicial del filtro
    * @param finalMeasureDate fecha final del filtro
    * @return media de los historicos filtrados
    */
    @Override
    public Optional<Double> getAverageValueBySensorIdAndMeasureDateBetween(Long idSensor, Date initialMeasureDate,
    Date finalMeasureDate) {
        List<HistoricValue> historicValues = historicValueRepository.findBySensorIdAndMeasureDateBetween(idSensor, initialMeasureDate, finalMeasureDate);
        return Optional.of(historicValues.stream().mapToDouble(x -> x.getValor()).average().getAsDouble());
    }

    /**
     * Devuelve unicamente los valores de los históricos de un sensor
     * 
     * @param idSensor id del sensor
     * @return listado de valores de los historicos
     */
    @Override
    public List<Float> getHistoricValues(Long idHistoricValue) {
        return historicValueRepository.getHistoricValues(idHistoricValue);
    }

    /**
     * Encuentra una lista de valores históricos filtrando por el id
     * de un sensor
     * 
     * @param id id del sensor
     * @return listado de históricos sobre el sensor
     */
    @Override
    public List<HistoricValue> getHistoricValuesBySensorId(Long idSensor) {
        return historicValueRepository.findBySensorId(idSensor);
    }

    /**
     * Elimina históricos con el id del sensor
     * @param idSensor id del sensor, del que se quieren eliminar
     * los registros
     */
    @Override
    public void deleteHistoricValuesWithSensorId(Long idSensor) {
        List<HistoricValue> historicValues= historicValueRepository.findBySensorId(idSensor);
        historicValueRepository.deleteAll(historicValues);
    }
    
    

}
