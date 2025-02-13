package com.estacionmeteorologica.estacionmeteorologica.controllers;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import com.estacionmeteorologica.estacionmeteorologica.services.historicValue.HistoricValueService;

/**
 * Clase encargada de las pruebas de {@link HistoricValueController}
 */
@DisplayName("Historic Value Controller Test")
@WebMvcTest(HistoricValueController.class)
@AutoConfigureMockMvc
public class HistoricValueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HistoricValueService historicValueService;


    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(new HistoricValueController(historicValueService)).build();
    }

    /**
     * Test encargado de obtener una lista de valores históricos de un 
     * sensor, en el periodo comprendido entre dos fechas
     * @throws Exception
     */
    @Test
    public void testGetHistoricValuesBySensorIdAndMeasureDateBetween() throws Exception{
    Long id = 1L;
    Date initialDate = Date.from(Instant.parse("2025-02-01T00:00:00.00Z"));
    Date finalDate = Date.from(Instant.parse("2025-02-10T00:00:00.00Z"));
    Optional<Double> result = Optional.of(26.5);


    BDDMockito.given(historicValueService.getAverageValueBySensorIdAndMeasureDateBetween(
            id, initialDate,finalDate))
            .willReturn(result);
    
    String url = UriComponentsBuilder
                    .fromPath("/sensores/{idSensor}/media/{fechaInincio}/{fechaFin}")
                    .buildAndExpand(id,"2025-02-01","2025-02-10").toUriString();

    mockMvc.perform(MockMvcRequestBuilders.get(url))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk()) ;
    }

    /**
     * Test encargado de verificar que se obtienen los valores
     * de los historicos de un sensor correctamente
     * @throws Exception
    */
    @Test
    public void testGetHistoricValuesBySensorId() throws Exception{

        Long id = 1L;


        BDDMockito.given(
            historicValueService.getHistoricValues(id))
            .willReturn(List.of(26.7f,33f));

        mockMvc.perform(MockMvcRequestBuilders.get("/sensores/{idSensor}/histórico", id))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasItems(26.7,33.0)));
    }


}
