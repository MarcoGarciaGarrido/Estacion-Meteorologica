package com.estacionmeteorologica.estacionmeteorologica.dto;

import java.util.Date;

import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Objeto de transferencia de datos de HistoricValue
 */
@NoArgsConstructor
@Getter
@Setter
public class HistoricValueDTO {
    /**
     * Id del HistoricValue
     */
    @Positive(message = "El id nunca puede ser inferior a 0")
    private Long id;

    /**
     * Sensor enlazado al HistoricValue
     */
    private Sensor sensor;

    /**
     * Valor del HistoricValue
     */
    private float valor;

    /**
     * Fecha de medición del HistoricValue
     */
    private Date measureDate;
}
