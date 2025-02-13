package com.estacionmeteorologica.estacionmeteorologica.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estacionmeteorologica.estacionmeteorologica.services.historicValue.HistoricValueService;

import lombok.AllArgsConstructor;


/**
 * Controlador de HistoricValue
 */
@AllArgsConstructor
@RestController
@RequestMapping("/sensores")
public class HistoricValueController {
    private final HistoricValueService historicValueService;

    /**
     * Proporciona una media, con los valores de un sensor comprendidos,
     * entre dos fechas
     * @param id id del Sensor
     * @param initialDate fecha inicial de los valores por los que se hace la media
     * @param finalDate fecha final de los valores por los que se hace la media
     * @return devuelve una respuesta Http completa, con la media
     */
    @GetMapping("/{idSensor}/media/{fechaInincio}/{fechaFin}")
    public ResponseEntity<Optional<Double>> getHistoricValuesBySensorIdAndMeasureDateBetween(@PathVariable("idSensor") Long id, @PathVariable("fechaInincio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date initialDate, @PathVariable("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date finalDate) {
        return new ResponseEntity<>(historicValueService.getAverageValueBySensorIdAndMeasureDateBetween(id, initialDate, finalDate), HttpStatus.OK);
    }

    /**
     * Proporciona una lista de valores historicos de un sensor
     * @param id id del sensor
     * @return devuelve una respuesta Http completa, con la lista de historicos
     */
    @GetMapping("/{idSensor}/histórico")
    public ResponseEntity<List<Float>> getHistoricValuesBySensorId(@PathVariable("idSensor") Long id) {
        return new ResponseEntity<>(historicValueService.getHistoricValues(id), HttpStatus.OK);
    }


}
