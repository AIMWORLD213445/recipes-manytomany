--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: ingredients_instructions; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE ingredients_instructions (
    id integer NOT NULL,
    name character varying,
    qty double precision,
    type character varying,
    recipe_id integer
);


ALTER TABLE ingredients_instructions OWNER TO "Guest";

--
-- Name: ingredients_instructions_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE ingredients_instructions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ingredients_instructions_id_seq OWNER TO "Guest";

--
-- Name: ingredients_instructions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE ingredients_instructions_id_seq OWNED BY ingredients_instructions.id;


--
-- Name: recipe; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE recipe (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE recipe OWNER TO "Guest";

--
-- Name: recipe_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE recipe_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE recipe_id_seq OWNER TO "Guest";

--
-- Name: recipe_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE recipe_id_seq OWNED BY recipe.id;


--
-- Name: tag; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE tag (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE tag OWNER TO "Guest";

--
-- Name: tag_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tag_id_seq OWNER TO "Guest";

--
-- Name: tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE tag_id_seq OWNED BY tag.id;


--
-- Name: tag_recipe; Type: TABLE; Schema: public; Owner: Guest
--

CREATE TABLE tag_recipe (
    id integer NOT NULL,
    tag_id integer,
    recipe_id integer
);


ALTER TABLE tag_recipe OWNER TO "Guest";

--
-- Name: tag_recipe_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE tag_recipe_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tag_recipe_id_seq OWNER TO "Guest";

--
-- Name: tag_recipe_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE tag_recipe_id_seq OWNED BY tag_recipe.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY ingredients_instructions ALTER COLUMN id SET DEFAULT nextval('ingredients_instructions_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipe ALTER COLUMN id SET DEFAULT nextval('recipe_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tag ALTER COLUMN id SET DEFAULT nextval('tag_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tag_recipe ALTER COLUMN id SET DEFAULT nextval('tag_recipe_id_seq'::regclass);


--
-- Data for Name: ingredients_instructions; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY ingredients_instructions (id, name, qty, type, recipe_id) FROM stdin;
\.


--
-- Name: ingredients_instructions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('ingredients_instructions_id_seq', 1, false);


--
-- Data for Name: recipe; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY recipe (id, name) FROM stdin;
\.


--
-- Name: recipe_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('recipe_id_seq', 1, false);


--
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY tag (id, name) FROM stdin;
\.


--
-- Name: tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('tag_id_seq', 1, false);


--
-- Data for Name: tag_recipe; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY tag_recipe (id, tag_id, recipe_id) FROM stdin;
\.


--
-- Name: tag_recipe_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('tag_recipe_id_seq', 1, false);


--
-- Name: ingredients_instructions_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY ingredients_instructions
    ADD CONSTRAINT ingredients_instructions_pkey PRIMARY KEY (id);


--
-- Name: recipe_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY recipe
    ADD CONSTRAINT recipe_pkey PRIMARY KEY (id);


--
-- Name: tag_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


--
-- Name: tag_recipe_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tag_recipe
    ADD CONSTRAINT tag_recipe_pkey PRIMARY KEY (id);


--
-- Name: ingredients_instructions_recipe_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY ingredients_instructions
    ADD CONSTRAINT ingredients_instructions_recipe_id_fkey FOREIGN KEY (recipe_id) REFERENCES recipe(id) ON DELETE CASCADE;


--
-- Name: tag_recipe_recipe_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tag_recipe
    ADD CONSTRAINT tag_recipe_recipe_id_fkey FOREIGN KEY (recipe_id) REFERENCES recipe(id) ON DELETE CASCADE;


--
-- Name: tag_recipe_tag_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tag_recipe
    ADD CONSTRAINT tag_recipe_tag_id_fkey FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE;


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

