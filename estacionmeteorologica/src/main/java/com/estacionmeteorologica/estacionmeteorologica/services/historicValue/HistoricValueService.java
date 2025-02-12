package com.estacionmeteorologica.estacionmeteorologica.services.historicValue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.estacionmeteorologica.estacionmeteorologica.models.HistoricValue;

public interface HistoricValueService {
    void deleteHistoricValuesWithSensorId(Long idSensor);
    List<HistoricValue> getHistoricValuesBySensorId(Long idSensor);
    List<HistoricValue> getHistoricValuesBySensorIdAndMeasureDateBetween(Long idSensor, Date initialMeasureDate, Date finalMeasureDate);
    Optional<Double> getAverageValueBySensorIdAndMeasureDateBetween(Long idSensor, Date initialMeasureDate, Date finalMeasureDate);
    List<Float> getHistoricValues(Long idHistoricValue);
}
