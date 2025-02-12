package com.estacionmeteorologica.estacionmeteorologica.services.historicValue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.estacionmeteorologica.estacionmeteorologica.repository.HistoricValueRepository;
import com.estacionmeteorologica.estacionmeteorologica.models.HistoricValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HistoricValueServiceImpl implements HistoricValueService{
    private final HistoricValueRepository historicValueRepository;

    @Override
    public List<HistoricValue> getHistoricValuesBySensorIdAndMeasureDateBetween(Long idSensor, Date initialMeasureDate,
            Date finalMeasureDate) {
        return historicValueRepository.findBySensorIdAndMeasureDateBetween(idSensor, initialMeasureDate, finalMeasureDate);
    }
    
    @Override
    public Optional<Double> getAverageValueBySensorIdAndMeasureDateBetween(Long idSensor, Date initialMeasureDate,
    Date finalMeasureDate) {
        List<HistoricValue> historicValues = historicValueRepository.findBySensorIdAndMeasureDateBetween(idSensor, initialMeasureDate, finalMeasureDate);
        return Optional.of(historicValues.stream().mapToDouble(x -> x.getValor()).average().getAsDouble());
    }

    @Override
    public List<Float> getHistoricValues(Long idHistoricValue) {
        return historicValueRepository.getHistoricValues(idHistoricValue);
    }

    @Override
    public List<HistoricValue> getHistoricValuesBySensorId(Long idSensor) {
        return historicValueRepository.findBySensorId(idSensor);
    }

    @Override
    public void deleteHistoricValuesWithSensorId(Long idSensor) {
        List<HistoricValue> historicValues= historicValueRepository.findBySensorId(idSensor);
        historicValueRepository.deleteAll(historicValues);
        //historicValueRepository.deleteAll((Iterable<? extends HistoricValue>) historicValueRepository.findBySensorId(idSensor));
    }
    
    

}
