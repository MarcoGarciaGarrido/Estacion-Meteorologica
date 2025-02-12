package com.estacionmeteorologica.estacionmeteorologica.services.sensor;

import java.util.Optional;

import com.estacionmeteorologica.estacionmeteorologica.dto.SensorDTO;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;


public interface SensorService {

    Iterable<Sensor> getAllSensors();
    Optional<Float> getValueOfSensorById(Long id);
    Sensor createSensor(SensorDTO sensorDTO);
    void deleteSensor(Long id);

}
