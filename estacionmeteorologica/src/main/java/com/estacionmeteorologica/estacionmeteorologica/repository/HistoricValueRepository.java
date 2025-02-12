package com.estacionmeteorologica.estacionmeteorologica.repository;

import org.springframework.stereotype.Repository;

import com.estacionmeteorologica.estacionmeteorologica.models.HistoricValue;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;


@Repository
public interface HistoricValueRepository extends CrudRepository<HistoricValue, Long>{
    
    public  List<HistoricValue> findBySensorId(Long id);
    public  List<HistoricValue> findBySensorIdAndMeasureDateBetween(Long idSensor, Date initialMeasureDate, Date finalMeasureDate);
    
    @Query(value = "select valor from HistoricValue where sensor.id =?1")
    public List<Float> getHistoricValues(Long idHistoricValue);

}
