CREATE OR REPLACE PACKAGE ride_share_init AS
    TYPE stationarr IS VARRAY(30) OF station.id%type;
    no_available_station EXCEPTION;
    PRAGMA EXCEPTION_INIT(no_available_station, -20001);
    invalid_bike_location EXCEPTION;
    PRAGMA EXCEPTION_INIT(invalid_bike_location, -20002);
    c_coords station.coordinates_x%type := 0;
    c_min_stations CONSTANT NUMBER := 5;
    c_max_stations CONSTANT NUMBER := 15;
    --function for determining the distance between two stations
    FUNCTION getDistance(x1 c_coords%type, x2 c_coords%type, y1 c_coords%type, y2 c_coords%type) RETURN route.length%type;
    PROCEDURE initCars(v_car_capacity car.total_capacity%type, v_#_of_cars station.total_cars%type);
    PROCEDURE initBikes(v_max_allowed NUMBER);
    --Function for determining which stations can still host more bikes
    FUNCTION getAvailableStations RETURN stationarr;
END;
/

CREATE OR REPLACE PACKAGE BODY ride_share_init AS
    FUNCTION getDistance(x1 c_coords%type, x2 c_coords%type, y1 c_coords%type, y2 c_coords%type) RETURN route.length%type AS
    BEGIN
        RETURN SQRT((x1 - x2)**2 + (y1 - y2)**2);
    END;

    PROCEDURE initCars(v_car_capacity car.total_capacity%type, v_#_of_cars station.total_cars%type) AS
    BEGIN
        for v_i in 1..v_#_of_cars LOOP
            INSERT INTO car(total_capacity, current_load) VALUES (v_car_capacity, 0);
        END LOOP;
    END;

    FUNCTION getAvailableStations RETURN stationarr AS
    v_available stationarr := stationarr();
    BEGIN
        FOR v_station_id in (SELECT id FROM station WHERE total_bike_capacity > bikes_stationed) LOOP
            v_available.EXTEND;
            v_available(v_available.LAST) := v_station_id.id;
        END LOOP;
        RETURN v_available;
    END;

    PROCEDURE initBikes(v_max_allowed NUMBER) AS
        v_bikes_to_init NUMBER;
    BEGIN
        v_bikes_to_init := TRUNC(DBMS_RANDOM.VALUE(v_max_allowed/5,v_max_allowed * 0.8));
        FOR i IN 1..v_bikes_to_init LOOP
              INSERT INTO bike (in_maintenance) VALUES(0);
        END LOOP;
    END;
END;
/
--trigger for adding routes from each new station to each ohter existing station
CREATE OR REPLACE TRIGGER add_routes
    FOR INSERT ON station
    COMPOUND TRIGGER

    CURSOR stations_before IS SELECT id, coordinates_x, coordinates_y FROM station;
    TYPE line_station IS TABLE OF stations_before%ROWTYPE;
    list_stations line_station;

    BEFORE STATEMENT IS
    BEGIN
        OPEN stations_before;
        SELECT id, coordinates_x, coordinates_y BULK COLLECT INTO list_stations FROM station;
        CLOSE stations_before;
    END BEFORE STATEMENT;
    AFTER EACH ROW IS
    v_last PLS_INTEGER;
    BEGIN
        IF (list_stations.count > 0) then
            for i in list_stations.first..list_stations.last LOOP
                IF (list_stations.exists(i)) THEN
                    INSERT INTO route(station_src, station_dest, length) VALUES(
                        :NEW.id,
                        list_stations(i).id,
                        ride_share_init.getDistance(
                            :NEW.coordinates_x,
                            list_stations(i).coordinates_x,
                            :NEW.coordinates_y,
                            list_stations(i).coordinates_y)
                        );
                END IF;
            END LOOP;
        END IF;
        list_stations.extend;
        v_last := list_stations.last;
        list_stations(v_last).id := :NEW.id;
        list_stations(v_last).coordinates_x := :NEW.coordinates_x;
        list_stations(v_last).coordinates_y := :NEW.coordinates_y;
    END AFTER EACH ROW;
END;
/

--if we specify an station id when inserting a bike, verify that there are enough spaces there to add a new bike
--can't insert a new bike directly on a route
--if station not specified, add bike to random available station
CREATE OR REPLACE TRIGGER increment_stationed
    BEFORE INSERT ON bike
    FOR EACH ROW
    DECLARE
        v_available ride_share_init.stationarr;
        v_random_station station.id%type;
        v_available_spaces NUMBER;
BEGIN
    IF(:NEW.route_id != NULL) THEN
        raise ride_share_init.invalid_bike_location;
    ELSIF(:NEW.station_id != NULL) THEN
        SELECT total_bike_capacity - bikes_stationed INTO v_available_spaces  from station where id = :NEW.station_id;
        IF (v_available_spaces > 0) THEN
            UPDATE station SET bikes_stationed = bikes_stationed + 1 where id = :NEW.station_id;
        ELSE
            raise ride_share_init.invalid_bike_location;
        END IF;
    ELSE
        v_available := ride_share_init.getavailablestations;
        IF (v_available.count = 0) THEN
            raise ride_share_init.no_available_station;
        END IF;
        v_random_station := v_available(TRUNC(DBMS_RANDOM.VALUE(v_available.first, v_available.last + 1)));
        :NEW.station_id := v_random_station;
        UPDATE station SET bikes_stationed = bikes_stationed + 1 where id = v_random_station;
    END IF;
END;
/

--trigger to assure consistency when updating bike location
CREATE OR REPLACE TRIGGER update_bike_location
    BEFORE UPDATE ON bike
    FOR EACH ROW
DECLARE
    v_available_spaces int;
BEGIN
    IF (:NEW.in_maintenance = 1) THEN
        :NEW.route_id := NULL;
        :NEW.station_id := NULL;
    ELSIF (:OLD.station_id <> :NEW.station_id AND :NEW.station_id IS NOT NULL) THEN
        SELECT total_bike_capacity - bikes_stationed INTO v_available_spaces  from station where id = :NEW.station_id;
        IF (v_available_spaces <= 0) THEN
            raise ride_share_init.invalid_bike_location;
        END IF;
        DBMS_OUTPUT.put_line('In station update case');
        :NEW.route_id := NULL;
        UPDATE station set bikes_stationed = bikes_stationed + 1 where id = :NEW.station_id;
    ELSIF(:NEW.route_id IS NOT NULL) THEN
        DBMS_OUTPUT.put_line('In route update case');
        :NEW.station_id := NULL;
    ELSIF(:NEW.route_id IS NULL AND :NEW.station_id IS NULL) THEN
        raise ride_share_init.invalid_bike_location;
    ELSE
        DBMS_OUTPUT.put_line('No case fits');
    END IF;

    IF (:OLD.station_id IS NOT NULL) then
            UPDATE station set bikes_stationed = bikes_stationed - 1 where id = :OLD.station_id;
        END IF;
END;
/

--Code block for initializing the station, car and bike tables(routes are added from trigger)
DECLARE
    TYPE varr IS VARRAY(100) OF station.name%type;
    station_list varr := varr('Oxted Close','Claverton Close','Hanger Lane','Shetland Road','Brays Road','Marine Approach','Aird Avenue','Moles Lane','Valley Court','Enstone Road','The Hollands','St Ives Avenue','Castleton Grove','Runnymede Way','Hannah Close','St Marks Grove','Romford Place','Apsley Road','Dalby Court','Cunliffe Street','Bracewell Close','Kirkstone Way','Peacock Road','Richards Close','Tallis Close','Gratton Road','Clarks Lane','Jarvis Close','Hitchin Road','Princeway','Churchward Avenue','Oxford Mews','Heathlands Close','Rosemary Court','Thornborough Close','Carol Close','Bellamy Close','Dormy Close','Bishopdale','Sunray Avenue','Armitage Gardens','Muirend Avenue','Buckingham Place','Trafford Street','Thimble Close','Sheraton Street','Moseley Avenue','Gray Close','Boreham Close','Chapelton Gardens','Comet Way','Lock Road','Clay Street','Clark Place','Aston Lane','Gibraltar Road','Forth Close','Dawn Rise','Flag Street','Miles Court','Hendy Close','Welham Close','Saxton Lane','Avenue Street','Southcote','Salcey Close','Wellfield Court','Irvine Close','Bramfield Road','Whitstable Road','Scotts Place','Emmett Street','The Platt','Chapel Hill Road','Cambridge Place','Mayflower Street','Romsley Close','Albany Place','The Sheilings','Asbury Road','Homefield','Kipling Drive','Terry Street','Homer Drive','Diane Close','Nursery Terrace','Galashiels Road','Turners Lane','Ings Lane','Maddock Street','Eastbourne Gardens','Kenya Road','Glossop Close','Pond Close','Rowley Gardens','Gladstone Court','Fountains Avenue','Vermont Close','Forth Crescent','Crags Road');

    v_#_of_stations int;
    v_station_name station.name%type;
    v_bike_capacity station.total_bike_capacity%type;
    v_bikes_stationed station.bikes_stationed%type;
    v_optimal_bike_count station.optimal_bike_count%type;
    v_car_total station.total_cars%type;
    v_car_capacity car.total_capacity%type;
    v_total_bike_spaces int := 0;
BEGIN
    v_#_of_stations := DBMS_RANDOM.VALUE(ride_share_init.c_min_stations + 1, ride_share_init.c_max_stations + 1);
    DBMS_OUTPUT.put_line('Inserarea a ' || v_#_of_stations  || ' statii');
    v_bike_capacity := TRUNC(DBMS_RANDOM.VALUE(5,50+1));
    v_total_bike_spaces := v_total_bike_spaces + v_bike_capacity;
    v_optimal_bike_count := v_bike_capacity/2;
    v_car_total := TRUNC(DBMS_RANDOM.VALUE(1,5+1));

    INSERT INTO station (
        name,
        coordinates_x,
        coordinates_y,
        total_bike_capacity,
        bikes_stationed,
        optimal_bike_count,
        total_cars,
        cars_stationed)
        VALUES(
        'DEPOT',
        TRUNC(DBMS_RANDOM.VALUE(0,1500), 2),
        TRUNC(DBMS_RANDOM.VALUE(0,1500), 2),
        v_bike_capacity,
        0,
        v_optimal_bike_count,
        v_car_total,
        v_car_total);

    v_car_capacity := TRUNC(DBMS_RANDOM.VALUE(3,10));
    ride_share_init.initCars(v_car_capacity, v_car_total);

    FOR v_i in 2..v_#_of_stations LOOP
        v_station_name := station_list(TRUNC(DBMS_RANDOM.VALUE(0,station_list.count))+1);
        DBMS_OUTPUT.put_line(v_station_name);
        v_bike_capacity := TRUNC(DBMS_RANDOM.VALUE(5,50));
        v_total_bike_spaces := v_total_bike_spaces + v_bike_capacity;
        v_optimal_bike_count := TRUNC(DBMS_RANDOM.VALUE(0,v_bike_capacity));

        INSERT INTO station (
        name,
        coordinates_x,
        coordinates_y,
        total_bike_capacity,
        bikes_stationed,
        optimal_bike_count)
        VALUES(
        v_station_name,
        TRUNC(DBMS_RANDOM.VALUE(0,1500), 2),
        TRUNC(DBMS_RANDOM.VALUE(0,1500), 2),
        v_bike_capacity,
        0,
        v_optimal_bike_count);
    END LOOP;
    ride_share_init.initbikes(v_total_bike_spaces);
END;
/

/*
select * from station;
select * from route;
select * from bike;
select * from car;
*/