package com.estacionmeteorologica.estacionmeteorologica.controllers;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estacionmeteorologica.estacionmeteorologica.services.historicValue.HistoricValueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;


/**
 * Controlador de HistoricValue
 */
@AllArgsConstructor
@RestController
@RequestMapping("/sensores")
@Tag(name = "Controlador de valores historicos",  description = "Endpoints encargados de gestionar los valores históricos")
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
    @Operation(summary = "Devuelve la media historica de un sesor",
    description = "Proporciona una media, con los valores históricos de un sensor comprendidos, entre dos fechas",
    responses = {@ApiResponse(responseCode = "200", description = "Valor de la media", 
        content = @Content(schema = @Schema(implementation = Double.class))),
        @ApiResponse(responseCode = "400", description = "Datos mal introducidos", 
            content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Sensor no existente", 
            content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{idSensor}/media/{fechaInincio}/{fechaFin}")
    public ResponseEntity<?> getHistoricValuesBySensorIdAndMeasureDateBetween(@PathVariable("idSensor") Long id, @PathVariable("fechaInincio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date initialDate, @PathVariable("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date finalDate) {
        try {
        return new ResponseEntity<>(historicValueService.getAverageValueBySensorIdAndMeasureDateBetween(id, initialDate, finalDate), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Proporciona una lista de valores historicos de un sensor
     * @param id id del sensor
     * @return devuelve una respuesta Http completa, con la lista de historicos
     */
    @Operation(summary = "Proporciona una lista de valores del histórico",
    description = "Devuelve la lista de valores del histórico de un sensor",
    responses = {
        @ApiResponse(responseCode = "200", description = "Lista de valores del histórico", 
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Float.class)))),
        @ApiResponse(responseCode = "400", description = "Datos mal introducidos", 
            content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Sensor no existente", 
            content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{idSensor}/histórico")
    public ResponseEntity<?> getHistoricValuesBySensorId(@PathVariable("idSensor") Long id) {
        try {
            return new ResponseEntity<>(historicValueService.getHistoricValues(id), HttpStatus.OK);            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
