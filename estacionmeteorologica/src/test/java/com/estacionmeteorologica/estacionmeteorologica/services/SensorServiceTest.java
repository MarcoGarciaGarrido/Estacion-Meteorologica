package com.estacionmeteorologica.estacionmeteorologica.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.estacionmeteorologica.estacionmeteorologica.dto.SensorDTO;
import com.estacionmeteorologica.estacionmeteorologica.models.Magnitude;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;
import com.estacionmeteorologica.estacionmeteorologica.repository.SensorRepository;
import com.estacionmeteorologica.estacionmeteorologica.services.sensor.SensorService;

@DisplayName("Sensor Service Test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SensorServiceTest {

    @MockitoBean
    private SensorRepository sensorRepository;

    @Autowired
    private SensorService sensorService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSensors() {
        Iterable<Sensor> sensors = Arrays.asList(new Sensor(), new Sensor(), new Sensor());

        when(sensorRepository.findAll()).thenReturn(sensors);


        Iterable<Sensor> results = sensorService.getAllSensors();

        assertNotNull(results);
        assertSame(sensors, results);
        verify(sensorRepository).findAll();
    }

    @Test
    public void testGetValueOfSensorById() {
        
        Long id = 1L;
        Optional<Sensor> sensorOptional = Optional.of(new Sensor(id,Magnitude.CELSIOUS,40));
        
        when(sensorRepository.findById(id)).thenReturn(sensorOptional);

        Float result = sensorService.getValueOfSensorById(id).get();

        assertNotNull(result);
        assertEquals(sensorOptional.get().getValor(), result.floatValue());

        verify(sensorRepository).findById(id);
    }

    @Test
    public void testCreateSensor(){

        SensorDTO sensorDTO = new SensorDTO(Magnitude.CELSIOUS,40);
        
        
        when(sensorRepository.existsSensorByMagnitude(sensorDTO.getMagnitude())).thenReturn(false);
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensorDTO.extractSensor());

        Sensor result = sensorService.createSensor(sensorDTO);

        assertNotNull(result);
        assertEquals(sensorDTO.getMagnitude(), result.getMagnitude());
        assertEquals(sensorDTO.getValor(), result.getValor());

        verify(sensorRepository, times(1)).existsSensorByMagnitude(sensorDTO.getMagnitude());
    }

    @Test
    public void deleteSensorFail(){     
        assertThrows(IllegalArgumentException.class,() -> sensorService.deleteSensor(1l));
    }

    @Test
    void testDeleteSensor_WhenSensorExists() {
        Long sensorId = 1L;

        when(sensorRepository.existsById(sensorId)).thenReturn(true);

        sensorService.deleteSensor(sensorId);

        verify(sensorRepository, times(1)).existsById(sensorId);
        verify(sensorRepository, times(1)).deleteById(sensorId);
    }


}
