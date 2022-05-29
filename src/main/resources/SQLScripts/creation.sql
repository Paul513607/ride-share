CREATE TABLE station(
    id INT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    coordinates_x DECIMAL(8,2),
    coordinates_y DECIMAL(8,2),
    total_bike_capacity int,
    bikes_stationed int,
    optimal_bike_count int,
    is_depo boolean,
    PRIMARY KEY(id)
);

DROP TABLE station;

CREATE TABLE route(
    id INT GENERATED ALWAYS AS IDENTITY,
    station_src INT,
    station_dest INT,
    length DECIMAL(15,3),
    CONSTRAINT fk_src FOREIGN KEY (station_src) REFERENCES station(id) ON DELETE CASCADE,
    CONSTRAINT fk_dest FOREIGN KEY (station_dest) REFERENCES station(id) ON DELETE CASCADE,
    PRIMARY KEY(id)
);


DROP TABLE route;

CREATE TABLE car(
    id INT GENERATED ALWAYS AS IDENTITY,
    total_capacity int,
    current_load int,
    current_route INT,
    CONSTRAINT fk_route FOREIGN KEY (current_route) REFERENCES route(id) ON DELETE SET NULL,
    PRIMARY KEY(id)
);

DROP TABLE car;

CREATE TABLE bike(
    id INT GENERATED ALWAYS AS IDENTITY,
    in_maintenance boolean,
    station_id INT,
    route_id INT,
    time_used_seconds INT,
    CONSTRAINT fk_bike_route FOREIGN KEY (route_id) REFERENCES route(id) ON DELETE SET NULL,
    CONSTRAINT fk_bike_station FOREIGN KEY (station_id) REFERENCES station(id) ON DELETE SET NULL,
    PRIMARY KEY(id)
);

drop table bike;