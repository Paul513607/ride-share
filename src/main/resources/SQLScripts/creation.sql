CREATE TABLE station(
    id NUMBER,
    name VARCHAR2(50) NOT NULL,
    coordinates_x NUMBER(8,2),
    coordinates_y NUMBER(8,2),
    total_bike_capacity int,
    bikes_stationed int,
    optimal_bike_count int,
    total_cars int,
    cars_stationed int,
    PRIMARY KEY(id)
);

CREATE SEQUENCE station_seq START WITH 1;

CREATE OR REPLACE TRIGGER station_inc
BEFORE INSERT ON station
FOR EACH ROW
BEGIN
  SELECT station_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

DROP TABLE station;
DROP SEQUENCE station_seq;
DROP TRIGGER station_inc;

CREATE TABLE route(
    id NUMBER PRIMARY KEY,
    station_src NUMBER,
    station_dest NUMBER,
    length NUMBER,
    CONSTRAINT fk_src FOREIGN KEY (station_src) REFERENCES station(id) ON DELETE CASCADE,
    CONSTRAINT fk_dest FOREIGN KEY (station_dest) REFERENCES station(id) ON DELETE CASCADE
);

CREATE SEQUENCE route_seq START WITH 1;

CREATE OR REPLACE TRIGGER route_inc
BEFORE INSERT ON route
FOR EACH ROW
BEGIN
  SELECT route_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

DROP TABLE route;
DROP SEQUENCE route_seq;
DROP TRIGGER route_inc;


CREATE TABLE car(
    id NUMBER PRIMARY KEY,
    total_capacity int,
    current_load int,
    current_route NUMBER,
    CONSTRAINT fk_route FOREIGN KEY (current_route) REFERENCES route(id) ON DELETE SET NULL
);

CREATE SEQUENCE car_seq START WITH 1;

CREATE OR REPLACE TRIGGER car_inc
BEFORE INSERT ON car
FOR EACH ROW
BEGIN
  SELECT car_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

DROP TABLE car;
DROP SEQUENCE car_seq;
DROP TRIGGER car_inc;


CREATE TABLE bike(
    id NUMBER PRIMARY KEY,
    in_maintenance NUMBER(1),
    station_id NUMBER,
    route_id NUMBER,
    time_used_seconds NUMBER,
    CONSTRAINT fk_bike_route FOREIGN KEY (route_id) REFERENCES route(id) ON DELETE SET NULL,
    CONSTRAINT fk_bike_station FOREIGN KEY (station_id) REFERENCES station(id) ON DELETE SET NULL
);

CREATE SEQUENCE bike_seq START WITH 1;

CREATE OR REPLACE TRIGGER bike_inc
BEFORE INSERT ON bike
FOR EACH ROW
BEGIN
  SELECT bike_seq.NEXTVAL
  INTO   :new.id
  FROM   dual;
END;
/

DROP TABLE bike;
DROP SEQUENCE bike_seq;
DROP TRIGGER bike_inc;
