CREATE DATABASE GestionEventos
USE GestionEventos

-- Tabla Area
CREATE TABLE Area (
    idArea VARCHAR(7) PRIMARY KEY,
    nombreArea VARCHAR(20) NOT NULL
);

-- Tabla TipoRecurso
CREATE TABLE TipoRecurso (
    idTipRec VARCHAR(7) PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL
);

-- Tabla TipoEvento
CREATE TABLE TipoEvento (
    idTipEven VARCHAR(7) PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL
);

-- Tabla Local
CREATE TABLE Local (
    idLocal VARCHAR(7) PRIMARY KEY,
    ciudad VARCHAR(25) NOT NULL
);

-- Tabla Evento
CREATE TABLE Evento (
    idEvento VARCHAR(7) PRIMARY KEY,
    titulo VARCHAR(30) NOT NULL,
    fecha DATE NOT NULL,
    estado BIT DEFAULT 1 NOT NULL,
    idTipEven VARCHAR(7) NOT NULL,
    FOREIGN KEY (idTipEven) REFERENCES TipoEvento(idTipEven)
);

-- Tabla Recurso
CREATE TABLE Recurso (
    idRecurso VARCHAR(7) PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    disponibilidad BIT DEFAULT 1 NOT NULL,
    idTipRec VARCHAR(7),
    idLocal VARCHAR(7),
    FOREIGN KEY (idTipRec) REFERENCES TipoRecurso(idTipRec),
    FOREIGN KEY (idLocal) REFERENCES Local(idLocal),
	CONSTRAINT chk_Recurso_TipoXorLocal
	CHECK ((idTipRec IS NOT NULL AND idLocal IS NULL) OR
    (idTipRec IS NULL AND idLocal IS NOT NULL))
);

-- Tabla Comisión
CREATE TABLE Comision (
    codComision VARCHAR(7) PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    idArea VARCHAR(7),
    FOREIGN KEY (idArea) REFERENCES Area(idArea),
);

-- Tabla Persona
CREATE TABLE Persona (
    dni VARCHAR(11) PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    apellido VARCHAR(20) NOT NULL,
    telefono VARCHAR(12)
);

-- Tabla Participante
CREATE TABLE Participante (
    idParticipante VARCHAR(7) PRIMARY KEY,
    dni VARCHAR(11) NOT NULL,
    FOREIGN KEY (dni) REFERENCES Persona(dni)
);

-- Tabla Jurado
CREATE TABLE Jurado (
    idJurado VARCHAR(7) PRIMARY KEY,
    dni VARCHAR(11) NOT NULL,
    idArea VARCHAR(7),
    FOREIGN KEY (dni) REFERENCES Persona(dni),
    FOREIGN KEY (idArea) REFERENCES Area(idArea)
);

-- Tabla TrabajoCientifico
CREATE TABLE TrabajoCientifico (
    idTrabajo VARCHAR(7) PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    idArea VARCHAR(7),
    idParticipante VARCHAR(7) NOT NULL,
    FOREIGN KEY (idArea) REFERENCES Area(idArea),
    FOREIGN KEY (idParticipante) REFERENCES Participante(idParticipante)
);

-- Tabla COMISION_TRABAJO
CREATE TABLE COMISION_TRABAJO (
    idComision VARCHAR(7),
    idTrabajo VARCHAR(7),
    PRIMARY KEY (idComision, idTrabajo),
    FOREIGN KEY (idComision) REFERENCES Comision(codComision),
    FOREIGN KEY (idTrabajo) REFERENCES TrabajoCientifico(idTrabajo)
);

-- Tabla COMISION_JURADO
CREATE TABLE COMISION_JURADO (
    idComision VARCHAR(7),
    idJurado VARCHAR(7),
    PRIMARY KEY (idComision, idJurado),
    FOREIGN KEY (idComision) REFERENCES Comision(codComision),
    FOREIGN KEY (idJurado) REFERENCES Jurado(idJurado)
);

-- Tabla EVENTO_RECURSO
CREATE TABLE EVENTO_RECURSO(
	idEvento VARCHAR(7),
	idRecurso VARCHAR(7),
	PRIMARY KEY (idEvento,idRecurso),
	FOREIGN KEY (idEvento) REFERENCES Evento(idEvento),
	FOREIGN KEY (idRecurso) REFERENCES Recurso(idRecurso)
);

-- Tabla EVENTO_COMISION
CREATE TABLE EVENTO_COMISION(
	idEvento VARCHAR(7),
	idComision VARCHAR(7),
	PRIMARY KEY (idEvento, idComision),
	FOREIGN KEY (idEvento) REFERENCES Evento(idEvento),
	FOREIGN KEY (idComision) REFERENCES Comision(codComision)
);

-- Tabla Usuario
CREATE TABLE Usuario (  
	nombreUsuario VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    apellido VARCHAR(20) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    tipo VARCHAR(15) CHECK (tipo IN ('Administrador', 'Comercial')) NOT NULL
);
