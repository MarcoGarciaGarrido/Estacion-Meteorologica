package com.estacionmeteorologica.estacionmeteorologica.controllers;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
     * @return Lista con todos los sensores en formato DTO
     */
    @Operation(summary = "Proporciona lista de sensores",
    description = "Devuelve la lista completa de sensores de la BBDD",
    responses = {@ApiResponse(responseCode = "200", description = "Lista de Sensores", 
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = Sensor.class))))})
    @GetMapping
    public ResponseEntity<?> sensorList(){
        Iterable<SensorDTO> sensorDTOIterable = StreamSupport
            .stream(sensorService.getAllSensors().spliterator(), false)
            .map(SensorDTO::new)
            .collect(Collectors.toList());
        

        return new ResponseEntity<>(sensorDTOIterable, HttpStatus.OK);
    }

    /**
     * Método encargado de obtener un sensor en base al id
     * @param id id del sensor
     * @return devuelve el sensor
     */
    @Operation(summary = "Devuelve un sensor en base al id",
    description = "Devuelve el sensor al cual pertenece el id introducido, previamente, por parámetro",
    responses = {
        @ApiResponse(responseCode = "200", description = "Sensor del parámetro", 
            content = @Content(schema = @Schema(implementation = Sensor.class))),
        @ApiResponse(responseCode = "400", description = "Datos mal introducidos", 
            content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Sensor no existente", 
                content = @Content(schema = @Schema(implementation = String.class)))})
    @GetMapping("/{idSensor}")
    public ResponseEntity<?> getValueFromSensor(@PathVariable("idSensor") Long id) {
        try {
            return new ResponseEntity<>(sensorService.getValueOfSensorById(id).get(), HttpStatus.OK);            
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
    @Operation(summary = "Crea un sensor",
    description = "Crea un sensor, con los datos introducidos. Comprobando antes que la magnitud no este repetida ",
    responses = {
        @ApiResponse(responseCode = "201", description = "Sensor creado exitosamente", 
            content = @Content(schema = @Schema(implementation = Sensor.class))),
        @ApiResponse(responseCode = "400", description = "Datos mal introducidos", 
            content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "502", description = "Perdida conexión", 
                content = @Content(schema = @Schema(implementation = String.class)))})
    @PostMapping
    public ResponseEntity<?> createSensor(@Valid @RequestBody SensorDTO sensorDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(new SensorDTO(sensorService.createSensor(sensorDTO)), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }

    /**
     * Método encargado de borrar un sensor en por id
     * @param id id del sensor a borrar
     * @return devuelve una respuesta Http completa, con errores 
     * en caso de no haber sido eliminada
     */
    @Operation(summary = "Eliminar un sensor",
    description = "Elimina el sensor, al cual le pertenezva el id introducido",
    responses = {
        @ApiResponse(responseCode = "200", description = "Sensor eliminado exitosamente", 
            content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Datos mal introducidos", 
            content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "502", description = "Perdida conexión", 
                content = @Content(schema = @Schema(implementation = String.class)))})
    @DeleteMapping("/{idSensor}")
    public ResponseEntity<String> deleteSensor(@PathVariable("idSensor") Long id) {
        try {
            sensorService.deleteSensor(id);
            return new ResponseEntity<>("Sensor eliminado satisfactoriamente", HttpStatus.OK);            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

        
}
