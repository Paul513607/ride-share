CREATE OR REPLACE FUNCTION random_between(low INT ,high INT)
   RETURNS INT AS
$$
BEGIN
   RETURN floor(random()* (high-low + 1) + low);
END;
$$ language 'plpgsql' STRICT;

CREATE OR REPLACE FUNCTION getDistance(x1 DECIMAL(8,2), x2 DECIMAL(8,2), y1 DECIMAL(8,2), y2 DECIMAL(8,2))
	RETURNS DECIMAL AS
$$
BEGIN
     RETURN SQRT((x1 - x2)^2 + (y1 - y2)^2);
END;
$$ language 'plpgsql' STRICT;

CREATE OR REPLACE FUNCTION getAvailableStations()
RETURNS int[] AS
$$
DECLARE
v_available int[] := array[];
v_station stations%rowtype;
BEGIN
    FOR v_station in
		SELECT * FROM stations WHERE total_bike_capacity > bikes_stationed
	LOOP
		v_available := v_available || v_station.id;
    END LOOP;
    RETURN v_available;
END;
$$ language 'plpgsql' STRICT;


CREATE OR REPLACE FUNCTION insert_routes()
RETURNS TRIGGER AS $$
DECLARE
	v_station stations%rowtype;
BEGIN
	FOR v_station in
		SELECT * FROM stations WHERE total_bike_capacity > bikes_stationed AND id <> NEW.id
	LOOP
		INSERT INTO routes(station_src, station_dest, length) VALUES(
                        NEW.id,
                        v_station.id,
                        getDistance(
                            NEW.coordinates_x,
                            v_station.coordinates_x,
                            NEW.coordinates_y,
                            v_station.coordinates_y
                        )
			);
	END LOOP;
	RETURN NULL;
END;
$$ language plpgsql;

CREATE TRIGGER add_routes
AFTER INSERT ON stations
	FOR EACH ROW EXECUTE FUNCTION insert_routes();

--DROP TRIGGER add_routes ON stations;


CREATE OR REPLACE FUNCTION insert_bikes()
RETURNS TRIGGER AS $$
DECLARE
BEGIN
	UPDATE stations set bikes_stationed = bikes_stationed + 1 where id = NEW.route_id;
	RETURN NULL;
END;
$$ language plpgsql;

CREATE TRIGGER after_insert_bikes
AFTER INSERT ON bikes
	FOR EACH ROW EXECUTE FUNCTION insert_bikes();

--DROP TRIGGER after_insert_bikes ON bikes;

CREATE OR REPLACE FUNCTION update_bikes()
RETURNS TRIGGER AS $$
DECLARE
BEGIN
	if(OLD.station_id IS NOT NULL AND OLD.station_id != NEW.station_id) THEN
		UPDATE stations set bikes_stationed = bikes_stationed - 1 where id = OLD.route_id;
	END IF;
	IF (NEW.station_id IS NOT NULL AND OLD.station_id != NEW.station_id) THEN
		UPDATE stations set bikes_stationed = bikes_stationed + 1 where id = NEW.route_id;
	END IF;
	RETURN NULL;
END;
$$ language plpgsql;

CREATE TRIGGER after_update_bikes
AFTER UPDATE ON bikes
	FOR EACH ROW EXECUTE FUNCTION update_bikes();

--DROP TRIGGER after_update_bikes ON bikes;

CREATE OR REPLACE PROCEDURE init_bikes(
	nr_of_bikes int,
	station_name varchar(50)
)
language plpgsql
as $$
declare
	v_station_id int;
begin
	SELECT id INTO v_station_id from stations where name = station_name;
	FOR index in 1..nr_of_bikes LOOP
		INSERT INTO bikes(in_maintenance, station_id, time_used_seconds)
		VALUES(FALSE, v_station_id, 0);
	END LOOP;
end;$$

create or replace procedure clear_tables()
language plpgsql
as $$
begin
	DELETE FROM bikes where TRUE;
	PERFORM setval(pg_get_serial_sequence('bikes', 'id'), 1);
	DELETE FROM cars where TRUE;
	PERFORM setval(pg_get_serial_sequence('cars', 'id'), 1);
	DELETE FROM routes where TRUE;
	PERFORM setval(pg_get_serial_sequence('routes', 'id'), 1);
	DELETE FROM stations where TRUE;
	PERFORM setval(pg_get_serial_sequence('stations', 'id'), 1);
end$$;

DROP PROCEDURE init_tables(out placeholder int);

create or replace procedure init_tables()
language plpgsql
as $$
DECLARE
	station_list varchar(50)[] := '{"Oxted Close","Claverton Close","Hanger Lane","Shetland Road","Brays Road","Marine Approach","Aird Avenue","Moles Lane","Valley Court","Enstone Road","The Hollands","St Ives Avenue","Castleton Grove","Runnymede Way","Hannah Close","St Marks Grove","Romford Place","Apsley Road","Dalby Court","Cunliffe Street","Bracewell Close","Kirkstone Way","Peacock Road","Richards Close","Tallis Close","Gratton Road","Clarks Lane","Jarvis Close","Hitchin Road","Princeway","Churchward Avenue","Oxford Mews","Heathlands Close","Rosemary Court","Thornborough Close","Carol Close","Bellamy Close","Dormy Close","Bishopdale","Sunray Avenue","Armitage Gardens","Muirend Avenue","Buckingham Place","Trafford Street","Thimble Close","Sheraton Street","Moseley Avenue","Gray Close","Boreham Close","Chapelton Gardens","Comet Way","Lock Road","Clay Street","Clark Place","Aston Lane","Gibraltar Road","Forth Close","Dawn Rise","Flag Street","Miles Court","Hendy Close","Welham Close","Saxton Lane","Avenue Street","Southcote","Salcey Close","Wellfield Court","Irvine Close","Bramfield Road","Whitstable Road","Scotts Place","Emmett Street","The Platt","Chapel Hill Road","Cambridge Place","Mayflower Street","Romsley Close","Albany Place","The Sheilings","Asbury Road","Homefield","Kipling Drive","Terry Street","Homer Drive","Diane Close","Nursery Terrace","Galashiels Road","Turners Lane","Ings Lane","Maddock Street","Eastbourne Gardens","Kenya Road","Glossop Close","Pond Close","Rowley Gardens","Gladstone Court","Fountains Avenue","Vermont Close","Forth Crescent","Crags Road"}';
	v_nr_of_stations int;
    v_station_name text;
    v_bike_capacity int;
    v_bikes_stationed int;
    v_optimal_bike_count int;
    v_total_bike_spaces int := 0;
	v_total_bikes_stationed int := 0;
	v_total_optimal_bikes int := 0;
	v_car_capacity int;
	v_station_id int;
BEGIN
	call clear_tables();
	v_nr_of_stations := random_between(5, 8);
    raise notice 'Se insereaza % statii', v_nr_of_stations;
    v_bike_capacity := 0;
    v_total_bike_spaces := v_total_bike_spaces + v_bike_capacity;
    v_optimal_bike_count := 0;
    INSERT INTO stations(
        name,
        coordinates_x,
        coordinates_y,
        total_bike_capacity,
        bikes_stationed,
        optimal_bike_count,
        is_depo)
        VALUES(
        'DEPOT',
        random_between(200, 1400),
        random_between(166, 834),
        0,
        0,
        0,
        TRUE);
	v_car_capacity := random_between(10, 35);
	INSERT INTO cars(
		total_capacity,
    	current_load
	)values(
		v_car_capacity,
		0
	);
	FOR v_i in 2..(v_nr_of_stations - 1) LOOP
        v_station_name := station_list[random_between(1, array_length(station_list,1))];
        raise notice '%', v_station_name;
        v_bike_capacity := random_between(10, 50);
        v_total_bike_spaces := v_total_bike_spaces + v_bike_capacity;
        v_optimal_bike_count := random_between(0, v_bike_capacity);
		v_total_optimal_bikes := v_total_optimal_bikes + v_optimal_bike_count;
		v_bikes_stationed := random_between(0, v_bike_capacity);
        v_total_bikes_stationed := v_total_bikes_stationed + v_bikes_stationed;
        INSERT INTO stations (
        name,
        coordinates_x,
        coordinates_y,
        total_bike_capacity,
        bikes_stationed,
        optimal_bike_count,
		is_depo)
        VALUES(
        v_station_name,
        random_between(200, 1400),
        random_between(166, 834),
        v_bike_capacity,
        v_bikes_stationed,
        v_optimal_bike_count,
		FALSE);
		call init_bikes(v_bikes_stationed, v_station_name);
    END LOOP;
	if(v_total_optimal_bikes < v_total_bikes_stationed) then
		v_bikes_stationed := random_between(1, 20);
		v_total_bikes_stationed := v_total_bikes_stationed + v_bikes_stationed;
		v_optimal_bike_count := v_total_bikes_stationed - v_total_optimal_bikes;
	else
		v_optimal_bike_count := random_between(1, 20);
		v_total_optimal_bikes := v_total_optimal_bikes + v_optimal_bike_count;
		v_bikes_stationed := v_total_optimal_bikes - v_total_bikes_stationed;
	end if;
	v_bike_capacity := GREATEST(v_optimal_bike_count, v_bikes_stationed) + random_between(1, 5);
	v_total_bike_spaces := v_total_bike_spaces + v_bike_capacity;
	v_station_name := station_list[random_between(1, array_length(station_list,1))];
	INSERT INTO stations(
        name,
        coordinates_x,
        coordinates_y,
        total_bike_capacity,
        bikes_stationed,
        optimal_bike_count,
		is_depo)
        VALUES(
        v_station_name,
        random_between(200, 1400),
        random_between(166, 834),
        v_bike_capacity,
        v_bikes_stationed,
        v_optimal_bike_count,
		FALSE);
		call init_bikes(v_bikes_stationed, v_station_name);
END$$;