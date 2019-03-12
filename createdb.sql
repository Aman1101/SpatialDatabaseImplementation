Create Database IF NOT EXISTS PublicSafety;
use PublicSafety;

CREATE TABLE IF NOT EXISTS zone (
 Zone_ID int NOT NULL PRIMARY KEY,
 Zone_Name varchar(50),
 Squad_Number int,
 Polygon_vertices int,
 Zone_Coord Polygon NOT NULL,
 SPATIAL INDEX(Zone_Coord)) ENGINE = MyISAM;
 
 CREATE TABLE IF NOT EXISTS officer (
 Unique_Badge_number int NOT NULL PRIMARY KEY,
 Officer_Name varchar(50),
 Squad_Number int,
 Officer_location Point Not NULL,
 SPATIAL INDEX(Officer_location))ENGINE = MyISAM;
 
 CREATE TABLE IF NOT EXISTS route (
 Unique_route_number int NOT NULL PRIMARY KEY,
 Number_of_vertices int,
 Route_Coord linestring Not NULL,
 SPATIAL INDEX(Route_Coord))ENGINE = MyISAM;
 
 CREATE TABLE IF NOT EXISTS incident (
 Unique_incident_ID decimal NOT NULL PRIMARY KEY,
 Type_of_incident Varchar(50),
 Incident_Coord Point Not NULL,
 SPATIAL INDEX(Incident_Coord))ENGINE = MyISAM;
