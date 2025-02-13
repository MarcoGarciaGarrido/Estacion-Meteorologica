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

    /**
     * Método encargado de listar todos los sensores
     * @return Lista con todos los sensores
     */
    @Override
    public Iterable<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    /**
     * Método encargado de obtener un sensor en base al id
     * @param id id del sensor
     * @return devuelve el sensor
     */ 
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

    /**
     * Método encargado de crear el sensor
     * @param sensorDTO DTO con los valores, para crear el sensor
     * @param bindingResult 
     * @return sensor creado
     */
    @Override
    public Sensor createSensor(SensorDTO sensorDTO){
        if(sensorRepository.existsSensorByMagnitude(sensorDTO.getMagnitude())) {
            throw new IllegalArgumentException("No puede crear un sensor con una magnitud existente");
        }

        return sensorRepository.save(sensorDTO.extractSensor());
    }

    /**
     * Método encargado de borrar un sensor en por id
     * @param id id del sensor a borrar
     * @return devuelve una respuesta Http completa, con errores 
     * en caso de no haber sido eliminada
     */
    @Override
    public void deleteSensor(Long id){
        if(!sensorRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe ningún sensor con el id introducido");
        }
        sensorRepository.deleteById(id);
    }

    

    

    
}
