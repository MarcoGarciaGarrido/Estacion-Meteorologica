CREATE TABLE IF NOT EXISTS sensor (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    magnitude VARCHAR(50) NOT NULL UNIQUE,
    valor FLOAT
);

CREATE TABLE IF NOT EXISTS historic_value (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    valor FLOAT,
    measure_date Date,
    sensor_id INT,
    FOREIGN KEY (sensor_id) REFERENCES sensor(id)
);
