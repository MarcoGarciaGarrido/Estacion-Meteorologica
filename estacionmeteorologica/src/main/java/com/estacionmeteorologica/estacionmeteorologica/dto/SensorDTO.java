package com.estacionmeteorologica.estacionmeteorologica.dto;

import com.estacionmeteorologica.estacionmeteorologica.models.Magnitude;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@NoArgsConstructor
@Getter
@Setter
public class SensorDTO {

    @Positive(message = "El id nunca puede ser inferior a 0")
    private Long id;

    @NotNull(message = "El campo de Magnitud es requerido")
    private Magnitude magnitude;

    private float valor;

    public SensorDTO(@NotNull(message = "El campo de Magnitud es requerido") Magnitude magnitude, float valor) {
        this.magnitude = magnitude;
        this.setValor(valor);
    }

    public Sensor extractSensor() {
        Sensor sensor = new Sensor();
        sensor.setMagnitude(magnitude);
        sensor.setValor(valor);
        
        return sensor;
    }

    public void setValor(float valor) {
        validValor(valor);
        this.valor = valor;
    }

    public boolean validValor(float valor) {

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

        return true;
    }


    
}
