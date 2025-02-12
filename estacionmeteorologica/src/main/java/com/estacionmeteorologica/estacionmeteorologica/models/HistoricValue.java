package com.estacionmeteorologica.estacionmeteorologica.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="historic_value")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HistoricValue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @Column(name = "valor", nullable = false)
    private float valor;

    @Column(name="measure_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date measureDate;
    
    public HistoricValue(Sensor sensor, float valor) {
        this.sensor = sensor;
        this.valor = valor;
        this.measureDate = new Date();
    }
}
