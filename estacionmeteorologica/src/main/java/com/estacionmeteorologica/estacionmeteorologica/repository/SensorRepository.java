package com.estacionmeteorologica.estacionmeteorologica.repository;

import org.springframework.stereotype.Repository;

import com.estacionmeteorologica.estacionmeteorologica.models.Magnitude;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


@Repository
public interface SensorRepository extends CrudRepository<Sensor, Long>{
    
    @Query(value = "select valor from Sensor where id =?1")
    public Float findSensorValueById(Long id);
    public Boolean existsSensorByMagnitude(Magnitude magnitude);

}
