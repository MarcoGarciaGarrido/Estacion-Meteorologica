package com.estacionmeteorologica.estacionmeteorologica.dto;

import java.util.Date;

import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HistoricValueDTO {
        
    @Positive(message = "El id nunca puede ser inferior a 0")
    private Long id;

    private Sensor sensor;

    private float valor;

    private Date measureDate;
}
