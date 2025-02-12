package com.estacionmeteorologica.estacionmeteorologica.models;

import java.util.List;
import java.util.Random;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="sensor")
@NoArgsConstructor
@Getter
@Setter
public class Sensor {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Magnitude magnitude;

    @Column(name = "valor")
    private float valor;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricValue> historicValues;

    public Sensor(Long id) {
        this.id = id;
    }

    public Sensor(Magnitude magnitude, float valor) {
        this.magnitude = magnitude;
        this.valor = valor;
    }

    public Sensor(Long id, Magnitude magnitude, float valor) {
        this.id = id;
        this.magnitude = magnitude;
        this.valor = valor;
    }

    public float generateValue() {
        return new Random().nextFloat(this.magnitude.getMinValue(), this.magnitude.getMaxValue());
    }
}
