package com.estacionmeteorologica.estacionmeteorologica.models;

/**
 * Entidad que representa a las Magnitudes
 */
public enum Magnitude {

    CELSIOUS(){
        @Override
        public String getUnitOfMeasure(){
            return "°C";
        }

        @Override
        public Float getMaxValue() {
            return 50f;
        }

        @Override
        public Float getMinValue() {
            return -20f;
        }
    },
    PERCENTAJE(){
        @Override
        public String getUnitOfMeasure(){
            return "%";
        }

        @Override
        public Float getMaxValue() {
            return 100f;
        }

        @Override
        public Float getMinValue() {
            return 0f;
        }
    },
    PREASURE(){
        @Override
        public String getUnitOfMeasure(){
            return "hPa";
        }

        @Override
        public Float getMaxValue() {
            return 1050.6f;
        }

        @Override
        public Float getMinValue() {
            return 980f;
        }
    },
    SPEED(){
        @Override
        public String getUnitOfMeasure(){
            return "m/s";
        }

        @Override
        public Float getMaxValue() {
            return 30f;
        }

        @Override
        public Float getMinValue() {
            return 10f;
        }
    };

    /**
     * Unidad de medida de las magnitudes
     * @return unidad de medida
     */
    public abstract String getUnitOfMeasure();

    /**
     * Valor mínimo de cada Magnitud
     * @return valor mínimo
     */
    public abstract Float getMinValue();
    
    /**
     * Valor máximo de cada Magnitud
     * @return valor máximo
     */
    public abstract Float getMaxValue();
}
