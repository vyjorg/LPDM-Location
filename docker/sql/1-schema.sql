
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

CREATE SEQUENCE public.adress_id_seq;

CREATE TABLE public.adress (
                id INTEGER NOT NULL DEFAULT nextval('public.adress_id_seq'),
                street_name VARCHAR NOT NULL,
                street_number INTEGER NOT NULL,
                complement VARCHAR,
                city_id INTEGER NOT NULL,
                CONSTRAINT adress_pk PRIMARY KEY (id)
);


ALTER SEQUENCE public.adress_id_seq OWNED BY public.adress.id;

ALTER TABLE public.adress ADD CONSTRAINT city_adress_fk
FOREIGN KEY (city_id)
REFERENCES public.city (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
