package com.estacionmeteorologica.estacionmeteorologica.dto;

import com.estacionmeteorologica.estacionmeteorologica.models.Magnitude;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Objeto de transferencia de datos del Sensor
 */
@NoArgsConstructor
@Getter
@Setter
public class SensorDTO {

    /**
     * Id del Sensor
     */
    @Positive(message = "El id nunca puede ser inferior a 0")
    @JsonIgnore
    private Long id;
    /**
     * Magnitud del Sensor
     */
    @NotNull(message = "El campo de Magnitud es requerido")
    private Magnitude magnitude;

    /**
     * Valor actual del Sensor
     */
    private float valor;

    /**
     * Constructor global del sensor, además de construir el objeto se asegura
     * de que el valor cumpla los parámetros
     * 
     * @param magnitude Magnitud que mide el sensor
     * @param valor Valor que marca el sensor
     */
    public SensorDTO(@NotNull(message = "El campo de Magnitud es requerido") Magnitude magnitude, float valor) {
        this.magnitude = magnitude;
        this.setValor(valor);
    }

    
    /**
     * Constructor global del sensor, además de construir el objeto se asegura
     * de que el valor cumpla los parámetros
     * 
     * @param magnitude Magnitud que mide el sensor
     * @param valor Valor que marca el sensor
     */
    public SensorDTO(Sensor sensor) {
        this.magnitude = sensor.getMagnitude();
        this.setValor(sensor.getValor());
    }

    /**
     * Metodo facilitador de convertir SensorDTO en un Sensor
     * @return devuelve un sensor que encaja con los valores
     */
    public Sensor extractSensor() {
        Sensor sensor = new Sensor();
        sensor.setMagnitude(magnitude);
        sensor.setValor(valor);
        
        return sensor;
    }

    /**
     * Método encargado fijar el valor del sensor y además comprueba 
     * que cumpla los parámetros
     * @param valor valor a fijar
     */
    public void setValor(float valor) {
        validValor(valor);
        this.valor = valor;
    }

    /**
     * Método encargado de comprobar que el valor entre en los parámetros
     * establecidos, en caso de lo contraró devuelve un error
     * 
     * @param valor valor por evaluar entre los valores de la Magnitud
     * del sensor
     */
    public void validValor(float valor) {

        float maxValue = this.getMagnitude().getMaxValue();
        float minValue = this.getMagnitude().getMinValue();
        String unitOfMeasure = this.getMagnitude().getUnitOfMeasure();

        if (valor > maxValue) {
            throw new IllegalArgumentException(String.format(
                "El valor %s%s supera el maximo valor de %s%s", valor, unitOfMeasure, maxValue, unitOfMeasure));
        } else if (valor < minValue) {
            throw new IllegalArgumentException(String.format(
                "El valor %s%s es menor que el minimo de %s%s", valor, unitOfMeasure, minValue, unitOfMeasure));
        }

    }


    
}
