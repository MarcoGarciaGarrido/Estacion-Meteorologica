package com.estacionmeteorologica.estacionmeteorologica.services.sensor;

import java.util.Optional;

import com.estacionmeteorologica.estacionmeteorologica.dto.SensorDTO;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;

/**
 * Servicio de la entidad de Sensor
 */
public interface SensorService {

    /**
     * Método encargado de listar todos los sensores
     * @return Lista con todos los sensores
     */
    Iterable<Sensor> getAllSensors();
    
    /**
     * Método encargado de obtener un sensor en base al id
     * @param id id del sensor
     * @return devuelve el sensor
     */ 
    Optional<Float> getValueOfSensorById(Long id);
    
    /**
     * Método encargado de crear el sensor
     * @param sensorDTO DTO con los valores, para crear el sensor
     * @param bindingResult 
     * @return sensor creado
     */
    Sensor createSensor(SensorDTO sensorDTO);
    
    /**
     * Método encargado de borrar un sensor en por id
     * @param id id del sensor a borrar
     * @return devuelve una respuesta Http completa, con errores 
     * en caso de no haber sido eliminada
     */
    void deleteSensor(Long id);

}
