package com.estacionmeteorologica.estacionmeteorologica.services.sensor;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.estacionmeteorologica.estacionmeteorologica.repository.HistoricValueRepository;
import com.estacionmeteorologica.estacionmeteorologica.repository.SensorRepository;

import lombok.AllArgsConstructor;

import com.estacionmeteorologica.estacionmeteorologica.dto.SensorDTO;
import com.estacionmeteorologica.estacionmeteorologica.models.HistoricValue;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;

@Service
@AllArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final HistoricValueRepository historicValueRepository;

    @Override
    public Iterable<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    @Override
    public Optional<Float> getValueOfSensorById(Long id) {
        Optional<Sensor> sensorOptional = sensorRepository.findById(id);
        if (!sensorOptional.isPresent()) throw new IllegalArgumentException("No existe ningún sensor con el id introducido");
        
        Sensor sensor = sensorOptional.get();
        sensor.setValor(sensor.generateValue());
        sensorRepository.save(sensor);

        HistoricValue historicValue = new HistoricValue(sensor, sensor.getValor()); 
        historicValueRepository.save(historicValue);

        return Optional.of(sensor.getValor());
    }

    @Override
    public Sensor createSensor(SensorDTO sensorDTO){
        if(sensorRepository.existsSensorByMagnitude(sensorDTO.getMagnitude())) {
            throw new IllegalArgumentException("No puede crear un sensor con una magnitud existente");
        }

        return sensorRepository.save(sensorDTO.extractSensor());
    }

    @Override
    public void deleteSensor(Long id){
        if(!sensorRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe ningún sensor con el id introducido");
        }
        sensorRepository.deleteById(id);
    }

    

    

    
}
