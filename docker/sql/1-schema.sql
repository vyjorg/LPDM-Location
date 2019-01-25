
CREATE SEQUENCE public.region_id_seq;

CREATE TABLE public.region (
   id INTEGER NOT NULL DEFAULT nextval('public.region_id_seq'),
   code varchar(3) NOT NULL,
   name varchar(255) NOT NULL,
   slug varchar(255) NOT NULL,
 CONSTRAINT region_pk PRIMARY KEY (id)
);

ALTER SEQUENCE public.region_id_seq OWNED BY public.region.id;



CREATE SEQUENCE public.department_id_seq;

CREATE TABLE public.department (
  id INTEGER NOT NULL DEFAULT nextval('public.department_id_seq'),
   region_code varchar(3) NOT NULL,
  code varchar(3) NOT NULL,
  name varchar(255) NOT NULL,
  slug varchar(255) NOT NULL,
  CONSTRAINT department_pk PRIMARY KEY (id)
 
);

ALTER SEQUENCE public.department_id_seq OWNED BY public.department.id;


CREATE SEQUENCE public.city_id_seq;

CREATE TABLE public.city (
  id INTEGER NOT NULL DEFAULT nextval('public.city_id_seq'),
  department_code varchar(3) NOT NULL,
  insee_code varchar(5) NULL,
  zip_code varchar(5) NULL,
  name varchar(255) NOT NULL,
  slug varchar(255) NOT NULL,
  gps_lat  DOUBLE PRECISION NOT NULL,
  gps_lng  DOUBLE PRECISION NOT NULL,
  CONSTRAINT city_pk PRIMARY KEY (id)
  
);


ALTER SEQUENCE public.city_id_seq OWNED BY public.city.id;

CREATE SEQUENCE public.address_id_seq;

CREATE TABLE public.address (
                id INTEGER NOT NULL DEFAULT nextval('public.address_id_seq'),
                street_name VARCHAR NOT NULL,
                street_number VARCHAR NOT NULL,
                complement VARCHAR,
                city_id INTEGER NOT NULL,
                CONSTRAINT address_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.address_id_seq OWNED BY public.address.id;

ALTER TABLE public.address ADD CONSTRAINT city_address_fk
FOREIGN KEY (city_id)
REFERENCES public.city (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
