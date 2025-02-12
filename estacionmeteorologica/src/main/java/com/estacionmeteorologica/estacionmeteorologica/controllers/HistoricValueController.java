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

@AllArgsConstructor
@RestController
@RequestMapping("/sensores")
public class HistoricValueController {
    private final HistoricValueService historicValueService;

    
    @GetMapping("/{idSensor}/media/{fechaInincio}/{fechaFin}")
    public ResponseEntity<Optional<Double>> getHistoricValuesBySensorIdAndMeasureDateBetween(@PathVariable("idSensor") Long id, @PathVariable("fechaInincio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date initialDate, @PathVariable("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date finalDate) {
        return new ResponseEntity<>(historicValueService.getAverageValueBySensorIdAndMeasureDateBetween(id, initialDate, finalDate), HttpStatus.OK);
    }

    @GetMapping("/{idSensor}/histórico")
    public ResponseEntity<List<Float>> getHistoricValuesBySensorId(@PathVariable("idSensor") Long id) {
        return new ResponseEntity<>(historicValueService.getHistoricValues(id), HttpStatus.OK);
    }


}
