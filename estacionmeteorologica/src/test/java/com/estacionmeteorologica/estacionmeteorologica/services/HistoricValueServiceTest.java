package com.estacionmeteorologica.estacionmeteorologica.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

import com.estacionmeteorologica.estacionmeteorologica.models.HistoricValue;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;
import com.estacionmeteorologica.estacionmeteorologica.repository.HistoricValueRepository;
import com.estacionmeteorologica.estacionmeteorologica.services.historicValue.HistoricValueService;
/**
 * Clase encargada de las pruebas de {@link HistoricValueService}
 */
@DisplayName("HistoricValueService Service Test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class HistoricValueServiceTest {

    
    @MockitoBean
    private HistoricValueRepository historicValueRepository;

    @Autowired
    private HistoricValueService historicValueService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test encargado de verificar que se obtiene un historico de valores
     * de un sensor entre dos fechas determinadas
     */
    @Test
    public void testGetHistoricValuesBySensorIdAndMeasureDateBetween() {

        Long idSensor = 1L;
        Date initialMeasureDate = new Date(System.currentTimeMillis() - 86400000);
        Date finalMeasureDate = new Date();
        Sensor sensor = new Sensor();
        Sensor sensor2 = new Sensor();

        HistoricValue historicValue = new HistoricValue(sensor, 25.5f);
        HistoricValue historicValue2 = new HistoricValue(sensor2, 26.0f);

        historicValue.setMeasureDate(initialMeasureDate);
        historicValue2.setMeasureDate(finalMeasureDate);
        

        List<HistoricValue> expectedValues = Arrays.asList(historicValue, historicValue2);

        when(historicValueRepository.findBySensorIdAndMeasureDateBetween(idSensor, initialMeasureDate, finalMeasureDate))
                .thenReturn(expectedValues);

        List<HistoricValue> result = historicValueService.getHistoricValuesBySensorIdAndMeasureDateBetween(idSensor, initialMeasureDate, finalMeasureDate);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(25.5, result.get(0).getValor());
        assertEquals(26.0, result.get(1).getValor());

        verify(historicValueRepository, times(1)).findBySensorIdAndMeasureDateBetween(idSensor, initialMeasureDate, finalMeasureDate);
    }
    
    @Test
    void testGetAverageValueBySensorIdAndMeasureDateBetween() {
        Long idSensor = 1L;
        Date initialMeasureDate = new Date(System.currentTimeMillis() - 86400000);
        Date finalMeasureDate = new Date();

        
        Sensor sensor = new Sensor();
        Sensor sensor2 = new Sensor();

        HistoricValue historicValue = new HistoricValue(sensor, 20.2f);
        HistoricValue historicValue2 = new HistoricValue(sensor2, 30.7f);

        List<HistoricValue> values = Arrays.asList(historicValue, historicValue2);

        when(historicValueRepository.findBySensorIdAndMeasureDateBetween(idSensor, initialMeasureDate, finalMeasureDate))
                .thenReturn(values);

        Optional<Double> result = historicValueService.getAverageValueBySensorIdAndMeasureDateBetween(idSensor, initialMeasureDate, finalMeasureDate);

        assertTrue(result.isPresent());
        assertEquals(25.45f, result.get()); 

        verify(historicValueRepository, times(1)).findBySensorIdAndMeasureDateBetween(idSensor, initialMeasureDate, finalMeasureDate);
    }

    /**
     * Test encargado de verificar que se obtiene correctamente el histórico
     * de valores
     */
    @Test
    void testGetHistoricValues() {
        Long id = 1L;
        List<Float> expectedValues = Arrays.asList(20.5f, 30.2f, 25.8f);

        when(historicValueRepository.getHistoricValues(id)).thenReturn(expectedValues);

        List<Float> result = historicValueService.getHistoricValues(id);

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(20.5f, result.get(0));
        assertEquals(30.2f, result.get(1));
        assertEquals(25.8f, result.get(2));

        verify(historicValueRepository, times(1)).getHistoricValues(id);
    }


    /**
     * Test encargado de verificar que se obtiene correctamente el histórico
     * de valores de un id concreto
     */
    @Test
    void testGetHistoricValuesBySensorId() {
        Long idSensor = 1L;        
        Sensor sensor = new Sensor();
        Sensor sensor2 = new Sensor();

        HistoricValue historicValue = new HistoricValue(sensor, 20.2f);
        HistoricValue historicValue2 = new HistoricValue(sensor2, 30.7f);
        List<HistoricValue> expectedValues = Arrays.asList(historicValue, historicValue2);

        when(historicValueRepository.findBySensorId(idSensor)).thenReturn(expectedValues);

        List<HistoricValue> result = historicValueService.getHistoricValuesBySensorId(idSensor);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(20.2f, result.get(0).getValor());
        assertEquals(30.7f, result.get(1).getValor());

        verify(historicValueRepository, times(1)).findBySensorId(idSensor);
    }

    /**
     * Test encargado de comprobar que se eliminan corectamente los Historicvalues
     * de un sensor por id
     */
    @Test
    void testDeleteHistoricValuesWithSensorId() {
        Long idSensor = 1L;
        Sensor sensor = new Sensor();
        Sensor sensor2 = new Sensor();

        HistoricValue historicValue = new HistoricValue(sensor, 20.2f);
        HistoricValue historicValue2 = new HistoricValue(sensor2, 30.7f);
        List<HistoricValue> historicValues = Arrays.asList(historicValue, historicValue2);

        when(historicValueRepository.findBySensorId(idSensor)).thenReturn(historicValues);

        historicValueService.deleteHistoricValuesWithSensorId(idSensor);

        verify(historicValueRepository, times(1)).findBySensorId(idSensor);
        verify(historicValueRepository, times(1)).deleteAll(historicValues);
    }

    
}
