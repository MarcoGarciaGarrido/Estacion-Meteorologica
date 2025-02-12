package com.estacionmeteorologica.estacionmeteorologica.models;

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

    public abstract String getUnitOfMeasure();
    public abstract Float getMinValue();
    public abstract Float getMaxValue();
}
