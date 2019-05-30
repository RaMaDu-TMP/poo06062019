
/**
 * Criando database
 */
CREATE DATABASE poo06062019;

/**
 * Criando tabela Usuários
 */
CREATE TABLE Users
(
	userID SERIAL PRIMARY KEY,
	Nome VARCHAR(255) UNIQUE NOT NULL,
	Login VARCHAR(255) UNIQUE NOT NULL,
	Password VARCHAR(255) NOT NULL -- SHA256(userID + Password)
);

/**
 * Criando usuário ADMIN
 */
INSERT INTO Users(Nome, Login, Password) VALUES('Admin', 'admin', '81b821b680fb4badc82a11b4869b1dae87cbd991a4a1d6c018b436f1d87339da');

/**
 * Criando tabela Marcas
 */
CREATE TABLE Marca 
(
	CodMarca SERIAL PRIMARY KEY,
	NomeMarca VARCHAR(255) NOT NULL
);

/**
 * Criando tabela Shapes
 */
CREATE TABLE Shape
(
	CodShape SERIAL PRIMARY KEY,
	CodMarca INT NOT NULL REFERENCES Marca(CodMarca),
	DescShape VARCHAR(500),
	PrecoShape MONEY NOT NULL
);

/**
 * Criando tabela Trucks
 */
CREATE TABLE Truck
(
	CodTruck SERIAL PRIMARY KEY,
	CodMarca INT NOT NULL REFERENCES Marca(CodMarca),
	DescTruck VARCHAR(500),
	PrecoTruck MONEY NOT NULL
);

/**
 * Criando tabela Rodas
 */
CREATE TABLE Roda
(
	CodRoda SERIAL PRIMARY KEY,
	CodMarca INT NOT NULL REFERENCES Marca(CodMarca),
	DescRoda VARCHAR(500),
	PrecoRoda MONEY NOT NULL
);

/**
 * Criando tabela Rolamentos
 */
CREATE TABLE Rolamento
(
	CodRolamento SERIAL PRIMARY KEY,
	CodMarca INT NOT NULL REFERENCES Marca(CodMarca),
	DescRolamento VARCHAR(500),
	PrecoRolamento MONEY NOT NULL
);

/**
 * Criando tabela Lixas
 */
CREATE TABLE Lixa
(
	CodLixa SERIAL PRIMARY KEY,
	CodMarca INT NOT NULL REFERENCES Marca(CodMarca),
	DescLixa VARCHAR(500),
	PrecoLixa MONEY NOT NULL
);

/**
 * Populando Marcas
 */
INSERT INTO Marca(NomeMarca) VALUES ('SantaCruz');		--1
INSERT INTO Marca(NomeMarca) VALUES ('Flip');			--2
INSERT INTO Marca(NomeMarca) VALUES ('Element');		--3
INSERT INTO Marca(NomeMarca) VALUES ('Thunders');		--4
INSERT INTO Marca(NomeMarca) VALUES ('Independent');	--5
INSERT INTO Marca(NomeMarca) VALUES ('Bones');			--6
INSERT INTO Marca(NomeMarca) VALUES ('Andale');			--7
INSERT INTO Marca(NomeMarca) VALUES ('Grizzly');		--8

/**
 * Populando Shapes
 */
INSERT INTO Shape(CodMarca, DescShape, PrecoShape) VALUES(1, 'Maple 8 polegadas',    120.00);
INSERT INTO Shape(CodMarca, DescShape, PrecoShape) VALUES(2, 'Maple 8.25 polegadas', 220.00);
INSERT INTO Shape(CodMarca, DescShape, PrecoShape) VALUES(3, 'Maple 7.75 polegadas', 160.00);

/**
 * Populando Trucks
 */
INSERT INTO Truck(CodMarca, DescTruck, PrecoTruck) VALUES(4, 'Aço', 520.00);
INSERT INTO Truck(CodMarca, DescTruck, PrecoTruck) VALUES(5, 'Aço', 560.00);

/**
 * Populando Rodas
 */
INSERT INTO Roda(CodMarca, DescRoda, PrecoRoda) VALUES(3, '52mm', 150.00);
INSERT INTO Roda(CodMarca, DescRoda, PrecoRoda) VALUES(6, '53mm', 180.00);

/**
 * Populando Rolamentos
 */
INSERT INTO Rolamento(CodMarca, DescRolamento, PrecoRolamento)VALUES (7, 'Aço',  80.00);
INSERT INTO Rolamento(CodMarca, DescRolamento, PrecoRolamento)VALUES (6, 'Aço', 120.00);

/**
 * Populando Lixas
 */
INSERT INTO Lixa (CodMarca, DescLixa, PrecoLixa) VALUES (3, 'Emborrachada',              60.00);
INSERT INTO Lixa (CodMarca, DescLixa, PrecoLixa) VALUES (8, 'Emborrachada com marcação', 80.00);
