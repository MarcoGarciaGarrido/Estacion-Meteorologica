package com.estacionmeteorologica.estacionmeteorologica.repository;

import org.springframework.stereotype.Repository;

import com.estacionmeteorologica.estacionmeteorologica.models.Magnitude;
import com.estacionmeteorologica.estacionmeteorologica.models.Sensor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio encargado de gestionar las operaciones de le entidad, Sensor
 */
@Repository
public interface SensorRepository extends CrudRepository<Sensor, Long>{
    /**
     * Encuentra el valor de un sensor
     * @param id id del sensor
     * @return valor del sensor
     */
    @Query(value = "select valor from Sensor where id =?1")
    public Float findSensorValueById(Long id);

    /**
     * Método encargado de comprobar, si existe, previamente
     * un sensor con una magnitud
     * @param magnitude Magnitud sobre la que se comprueba
     * @return devuelve true en caso de existir y false en caso contrario
     */
    public Boolean existsSensorByMagnitude(Magnitude magnitude);

}
