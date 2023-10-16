--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cuota; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuota (
    fecha_cuota date,
    fecha_de_pago date,
    monto integer NOT NULL,
    monto_descuentoi integer NOT NULL,
    monto_descuentop integer NOT NULL,
    numero_cuota integer NOT NULL,
    id_cuota bigint NOT NULL,
    pagada character varying(255),
    rut_cuota character varying(255)
);


ALTER TABLE public.cuota OWNER TO postgres;

--
-- Name: cuota_id_cuota_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cuota_id_cuota_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cuota_id_cuota_seq OWNER TO postgres;

--
-- Name: cuota_id_cuota_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuota_id_cuota_seq OWNED BY public.cuota.id_cuota;


--
-- Name: estudiantes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estudiantes (
    anio_egreso integer NOT NULL,
    fecha_nacimiento date,
    apellidos character varying(255),
    nombre character varying(255),
    nombre_colegio character varying(255),
    rut character varying(255) NOT NULL,
    tipo_colegio character varying(255)
);


ALTER TABLE public.estudiantes OWNER TO postgres;

--
-- Name: pruebas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pruebas (
    fecha_examen date,
    puntaje integer NOT NULL,
    id_prueba bigint NOT NULL,
    rut_prueba character varying(255)
);


ALTER TABLE public.pruebas OWNER TO postgres;

--
-- Name: pruebas_id_prueba_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pruebas_id_prueba_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pruebas_id_prueba_seq OWNER TO postgres;

--
-- Name: pruebas_id_prueba_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pruebas_id_prueba_seq OWNED BY public.pruebas.id_prueba;


--
-- Name: resumen; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.resumen (
    fecha_ultimo_pago date,
    monto_total_arancel double precision NOT NULL,
    monto_total_pagado double precision NOT NULL,
    numero_cuotas_con_retraso integer NOT NULL,
    numero_examenes_rendidos integer NOT NULL,
    numero_total_cuotas integer NOT NULL,
    promedio_examenes double precision NOT NULL,
    saldo_por_pagar double precision NOT NULL,
    id_resumen bigint NOT NULL,
    rut_estudiante character varying(255),
    tipo_pago character varying(255)
);


ALTER TABLE public.resumen OWNER TO postgres;

--
-- Name: resumen_id_resumen_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.resumen_id_resumen_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.resumen_id_resumen_seq OWNER TO postgres;

--
-- Name: resumen_id_resumen_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.resumen_id_resumen_seq OWNED BY public.resumen.id_resumen;


--
-- Name: cuota id_cuota; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuota ALTER COLUMN id_cuota SET DEFAULT nextval('public.cuota_id_cuota_seq'::regclass);


--
-- Name: pruebas id_prueba; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pruebas ALTER COLUMN id_prueba SET DEFAULT nextval('public.pruebas_id_prueba_seq'::regclass);


--
-- Name: resumen id_resumen; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resumen ALTER COLUMN id_resumen SET DEFAULT nextval('public.resumen_id_resumen_seq'::regclass);


--
-- Data for Name: cuota; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cuota (fecha_cuota, fecha_de_pago, monto, monto_descuentoi, monto_descuentop, numero_cuota, id_cuota, pagada, rut_cuota) FROM stdin;
2023-04-01	2023-10-10	120000	120000	120000	7	7	Pendiente	20627890-0
2023-04-01	2023-11-10	120000	120000	120000	8	8	Pendiente	20627890-0
2023-04-01	2023-12-10	120000	120000	120000	9	9	Pendiente	20627890-0
2023-04-01	2024-01-10	120000	120000	120000	10	10	Pendiente	20627890-0
2023-04-01	2023-04-10	120000	138000	120000	1	1	Pendiente	20627890-0
2023-04-01	2023-05-10	120000	138000	120000	2	2	Pendiente	20627890-0
2023-04-01	2023-06-10	120000	138000	120000	3	3	Pendiente	20627890-0
2023-04-01	2023-07-10	120000	130800	120000	4	4	Pendiente	20627890-0
2023-04-01	2023-08-10	120000	127200	120000	5	5	Pendiente	20627890-0
2023-04-01	2023-09-10	120000	123600	120000	6	6	Pendiente	20627890-0
2023-04-01	2023-05-10	255000	293250	255000	2	12	Pendiente	22436818-6
2023-04-01	2023-06-10	255000	293250	255000	3	13	Pendiente	22436818-6
2023-04-01	2023-07-10	255000	277950	255000	4	14	Pendiente	22436818-6
2023-04-01	2023-04-10	255000	263925	229500	1	11	Pendiente	22436818-6
2023-04-01	2023-05-10	259200	298080	259200	2	16	Pendiente	RUT1
2023-04-01	2023-06-10	259200	298080	259200	3	17	Pendiente	RUT1
2023-04-01	2023-07-10	259200	282528	259200	4	18	Pendiente	RUT1
2023-04-01	2023-08-10	259200	274752	259200	5	19	Pendiente	RUT1
2023-04-01	2023-04-10	259200	268272	233280	1	15	Pendiente	RUT1
2023-04-01	2023-05-10	450000	517499	450000	2	21	Pendiente	17989639-7
2023-04-01	2023-06-10	450000	517499	450000	3	22	Pendiente	17989639-7
2023-04-01	2023-04-10	450000	465749	405000	1	20	Pendiente	17989639-7
2023-04-01	2023-04-10	637500	733125	637500	1	23	Pendiente	RUT2
2023-04-01	2023-05-10	637500	733125	637500	2	24	Pendiente	RUT2
\.


--
-- Data for Name: estudiantes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.estudiantes (anio_egreso, fecha_nacimiento, apellidos, nombre, nombre_colegio, rut, tipo_colegio) FROM stdin;
2018	2001-01-31	Muñoz Ramirez	Pablo	Liceo de Aplicacion	20627890-0	1
2023	2007-06-26	Ahumada Ahumada	Diego Benjamin	Liceo de Santa Cruz	22436818-6	1
2020	2001-01-31	misom	Yo	Liceo	RUT1	2
2009	1991-12-01	Betancur Ramirez	Rodrigo Matias	Irfe	17989639-7	2
2023	2000-12-29	Avaca	Ricardo	Las condes	RUT2	3
\.


--
-- Data for Name: pruebas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pruebas (fecha_examen, puntaje, id_prueba, rut_prueba) FROM stdin;
2023-04-10	1000	1	RUT1
2023-04-10	1000	2	22436818-6
2023-04-10	1000	3	17989639-7
\.


--
-- Data for Name: resumen; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.resumen (fecha_ultimo_pago, monto_total_arancel, monto_total_pagado, numero_cuotas_con_retraso, numero_examenes_rendidos, numero_total_cuotas, promedio_examenes, saldo_por_pagar, id_resumen, rut_estudiante, tipo_pago) FROM stdin;
2023-04-10	1275600	0	7	0	10	0	1275600	1	20627890-0	Cuotas
2023-04-10	1128375	0	4	1	4	1000	1128375	2	22436818-6	Cuotas
2023-04-10	1421712	0	5	1	5	1000	1421712	3	RUT1	Cuotas
2023-04-10	1500747	0	3	1	3	1000	1500747	4	17989639-7	Cuotas
2023-04-10	1466250	0	2	0	2	0	1466250	5	RUT2	Cuotas
\.


--
-- Name: cuota_id_cuota_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuota_id_cuota_seq', 24, true);


--
-- Name: pruebas_id_prueba_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pruebas_id_prueba_seq', 3, true);


--
-- Name: resumen_id_resumen_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.resumen_id_resumen_seq', 5, true);


--
-- Name: cuota cuota_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuota
    ADD CONSTRAINT cuota_pkey PRIMARY KEY (id_cuota);


--
-- Name: estudiantes estudiantes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estudiantes
    ADD CONSTRAINT estudiantes_pkey PRIMARY KEY (rut);


--
-- Name: pruebas pruebas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pruebas
    ADD CONSTRAINT pruebas_pkey PRIMARY KEY (id_prueba);


--
-- Name: resumen resumen_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.resumen
    ADD CONSTRAINT resumen_pkey PRIMARY KEY (id_resumen);


--
-- PostgreSQL database dump complete
--

