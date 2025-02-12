package com.estacionmeteorologica.estacionmeteorologica.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.assertj.core.util.IterableUtil;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.hamcrest.Matchers;

import com.estacionmeteorologica.estacionmeteorologica.dto.SensorDTO;
import com.estacionmeteorologica.estacionmeteorologica.models.Magnitude;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;
import com.estacionmeteorologica.estacionmeteorologica.services.sensor.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("Sensor Controller Test")
@WebMvcTest(SensorController.class)
@AutoConfigureMockMvc
public class SensorControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SensorService sensorService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(new SensorController(sensorService)).build();
    }

    @Test
    public void testSensorList() throws Exception{

        BDDMockito.given(sensorService.getAllSensors()).willReturn (IterableUtil.iterable(new Sensor(), new Sensor(), new Sensor()));

        mockMvc.perform(MockMvcRequestBuilders.get("/sensores"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    public void testGetValueFromSensor() throws Exception{
        Sensor sensor = new Sensor(1l);

        BDDMockito.given(sensorService.getValueOfSensorById(sensor.getId())).willReturn(Optional.of(sensor.getValor()));

        mockMvc.perform(MockMvcRequestBuilders.get("/sensores/{idSensor}", sensor.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").value(sensor.getValor()));
    }
    
    @Test
    public void testCreateSensor() throws Exception{
        SensorDTO sensorDTO = new SensorDTO(Magnitude.PREASURE, 100f);
        Sensor sensor = new Sensor(Magnitude.PREASURE, 100f);

        BDDMockito.given(sensorService.createSensor(any(SensorDTO.class))).willReturn(sensor);

        mockMvc.perform(MockMvcRequestBuilders.post("/sensores")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(sensorDTO)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.magnitude").value(sensor.getMagnitude().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(sensor.getValor()));
    }

    @Test
    public void testDeleteSensor() throws Exception{
        Long id = 1l;

        BDDMockito.doNothing().when(sensorService).deleteSensor(id);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/sensores/{idSensor}", id))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").value("Sensor eliminado satisfactoriamente"));
    }
}
