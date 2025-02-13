package com.estacionmeteorologica.estacionmeteorologica.controllers;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindingResult;

import com.estacionmeteorologica.estacionmeteorologica.dto.SensorDTO;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;
import com.estacionmeteorologica.estacionmeteorologica.services.sensor.SensorService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador de Sensor
 */
@AllArgsConstructor
@RestController
@RequestMapping("/sensores")
public class SensorController {

    private final SensorService sensorService;
    /**
     * Método encargado de listar todos los sensores
     * @return Lista con todos los sensores
     */
    @GetMapping
    public ResponseEntity<Iterable<Sensor>> sensorList(){
        return new ResponseEntity<>(sensorService.getAllSensors(), HttpStatus.OK);
    }

    /**
     * Método encargado de obtener un sensor en base al id
     * @param id id del sensor
     * @return devuelve el sensor
     */
    @GetMapping("/{idSensor}")
    public ResponseEntity<?> getValueFromSensor(@PathVariable("idSensor") Long id) {
        try {
            return new ResponseEntity<>(sensorService.getValueOfSensorById(id), HttpStatus.OK);            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Método encargado de crear el sensor
     * @param sensorDTO DTO con los valores, para crear el sensor
     * @param bindingResult 
     * @return sensor creado
     */
    @PostMapping
    public ResponseEntity<?> createSensor(@Valid @RequestBody SensorDTO sensorDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(sensorService.createSensor(sensorDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Método encargado de borrar un sensor en por id
     * @param id id del sensor a borrar
     * @return devuelve una respuesta Http completa, con errores 
     * en caso de no haber sido eliminada
     */
    @DeleteMapping("/{idSensor}")
    public ResponseEntity<?> deleteSensor(@PathVariable("idSensor") Long id) {
        try {
            sensorService.deleteSensor(id);
            return new ResponseEntity<>("Sensor eliminado satisfactoriamente", HttpStatus.OK);            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

        
}
