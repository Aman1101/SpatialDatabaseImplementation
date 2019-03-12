import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Populate {

    static Connection connection;
    public static void insertIncident(String fileName){
        try{
            BufferedReader incident_BR = new BufferedReader(new FileReader(fileName));
            String incidentStr = null;

            String incidentTruncSql = "truncate table incident;";
            Statement statement = connection.createStatement();
            statement.execute(incidentTruncSql);
            while((incidentStr = incident_BR.readLine())!=null){
                String [] values1 = incidentStr.split(",");
                int unique_incident_ID =Integer.parseInt(values1[0].trim());
                String type_of_incident = values1[1].trim().replaceAll("\"","");
                String incident_Coord_Lat = values1[2].trim();
                String incident_Coord_Long = values1[3].trim();


                String incidentSql = "INSERT INTO incident VALUES (?,?,ST_PointFromText(?));";
                PreparedStatement prepStmt = connection.prepareStatement(incidentSql);
                prepStmt.setInt(1, unique_incident_ID);
                prepStmt.setString(2, type_of_incident);
                String pointString = "Point("+incident_Coord_Lat+" "+incident_Coord_Long+")";
                prepStmt.setString(3, pointString);
                prepStmt.executeUpdate();
            }
        }catch (FileNotFoundException fn){
            System.out.println("File not found, please verify the path for incident.txt file ");
        }catch(IOException io){
            System.out.println("Error in accessing the data in file incident.file");
        }catch (SQLException sqe){
            System.out.println("SqlException found");
        }catch (NullPointerException ne){
            System.out.println("Required data objects not found : Either database or table Incident");
        }
    }

    public static void insertOfficer(String fileName) {
        try {
            BufferedReader officer_BR = new BufferedReader(new FileReader(fileName));
            String officerStr = null;

            String officerTruncSql = "truncate table officer;";
            Statement statement = connection.createStatement();
            statement.execute(officerTruncSql);
            while ((officerStr = officer_BR.readLine()) != null) {
                String[] values1 = officerStr.split(",");
                int unique_Badge_Number = Integer.parseInt(values1[0].trim());
                String officer_Name = values1[1].trim().replaceAll("\"","");
                int squad_Number = Integer.parseInt(values1[2].trim());
                String officer_Location_Lat = values1[3].trim();
                String officer_Location_Long = values1[4].trim();


                String officerSql = "INSERT INTO officer VALUES (?,?,?,ST_PointFromText(?));";
                PreparedStatement prepStmt = connection.prepareStatement(officerSql);
                prepStmt.setInt(1, unique_Badge_Number);
                prepStmt.setString(2, officer_Name);
                prepStmt.setInt(3, squad_Number);
                String pointString = "Point(" + officer_Location_Lat + " " + officer_Location_Long + ")";
                prepStmt.setString(4, pointString);
                prepStmt.executeUpdate();
            }
        }catch (FileNotFoundException fn){
            System.out.println("File not found, please verify the path for officer.txt file ");
        }catch(IOException io){
            System.out.println("Error in accessing the data in file officer.file");
        }catch (SQLException sqe){
            System.out.println("SqlException found");
        }catch (NullPointerException ne){
            System.out.println("Required data objects not found : Either database or table Officer");
        }
    }

    public static void insertRoute(String fileName){
        try{
            BufferedReader route_BR = new BufferedReader(new FileReader(fileName));
            String routeStr = null;
            String routeTruncSql = "truncate table route;";
            Statement statement = connection.createStatement();
            statement.execute(routeTruncSql);
            while ((routeStr = route_BR.readLine())!=null){
                String [] values1 = routeStr.split(",");
                int unique_Route_Number = Integer.parseInt(values1[0].trim());
                int number_Of_Vertices = Integer.parseInt(values1[1].trim());
                String route_Coord = "";
                for (int i =2; i<(number_Of_Vertices*2+2);i++){
                    route_Coord += values1[i] + " ";
                }
                route_Coord = route_Coord.trim().replaceAll("(\\s\\s[^\\s\\s]*)\\s\\s", "$1,");


                String routeSql = "INSERT INTO route VALUES (?,?,ST_LineStringFromText(?));";
                PreparedStatement prepStmt = connection.prepareStatement(routeSql);
                prepStmt.setInt(1, unique_Route_Number);
                prepStmt.setInt(2, number_Of_Vertices);
                String routeString = "LINESTRING(" + route_Coord + ")";
                prepStmt.setString(3, routeString);
                prepStmt.executeUpdate();
            }
        }catch (FileNotFoundException fn){
            System.out.println("File not found, please verify the path for route.txt file ");
        }catch(IOException io){
            System.out.println("Error in accessing the data in file route.file");
        }catch (SQLException sqe){
            System.out.println("SqlException found");
        }catch (NullPointerException ne){
            System.out.println("Required data objects not found : : Either database or table Route");
        }
    }

    public static void insertZone(String fileName){
        try {
            BufferedReader zone_BR = new BufferedReader(new FileReader(fileName));
            String zoneStr = null;
            String zoneTruncSql = "truncate table zone;";
            Statement statement = connection.createStatement();
            statement.execute(zoneTruncSql);
            while((zoneStr = zone_BR.readLine())!= null){
                String [] values1 = zoneStr.trim().split(",");
                int zoneID = Integer.parseInt(values1[0].trim());
                String zoneName = values1[1].trim().replaceAll("\"","");
                int squadNumber = Integer.parseInt(values1[2].trim());
                int polygonVertices = Integer.parseInt(values1[3].trim());
                String polygonCoord = "";
                String loopPolygonCoord = values1[4] + " " + values1[5];
                for (int i =4; i<(polygonVertices*2+4);i++){
                    polygonCoord += values1[i] + " ";
                }
                polygonCoord = (polygonCoord + " " + loopPolygonCoord).trim().replaceAll("(\\s\\s[^\\s\\s]*)\\s\\s", "$1,");


                String zoneSql = "INSERT INTO zone VALUES (?,?,?,?,ST_PolygonFromText(?));";
                PreparedStatement prepStmt = connection.prepareStatement(zoneSql);
                prepStmt.setInt(1, zoneID);
                prepStmt.setString(2, zoneName);
                prepStmt.setInt(3, squadNumber);
                prepStmt.setInt(4, polygonVertices);
                String zoneString = "POLYGON((" + polygonCoord + "))";
                prepStmt.setString(5, zoneString);
                prepStmt.executeUpdate();

            }
        }catch (FileNotFoundException fn){
            System.out.println("File not found, please verify the path for zone.txt file ");
        }catch(IOException io){
            System.out.println("Error in accessing the data in file zone.file");
        }catch (SQLException sqe){
            System.out.println("SqlException found");
        }catch (NullPointerException ne){
            System.out.println("Required data objects not found : : Either database or table Zone");
        }
    }

    public static void createConnection(String fileName){

        try {
            Class.forName("com.mysql.jdbc.Driver");

            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            ArrayList<String> list = new ArrayList<String>();
            String str;

            while ((str = bufferedReader.readLine()) != null) {
                list.add(str);
            }

            String host = list.get(0);
            String port = list.get(1);
            String DB = list.get(2);
            String userName = list.get(3);
            String password = list.get(4);

            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + DB + "?useSSL=false", userName  ,  password);
            System.out.println("Database Connection Successful");
        } catch (
                FileNotFoundException fn){
            System.out.println("File not found please verify the path for db.properties file ");
        }catch(
                IOException io){
            System.out.println("Error in accessing the data in file db.properties");
        }catch (
                SQLException sqe){
            System.out.println("SqlException found");
        }catch (ClassNotFoundException cnf){
            System.out.printf("forName class not found");
        }catch (NullPointerException ne){
            System.out.println("Required data objects not found");
        }
    }

    public static void main(String[] args) {

        try {
            createConnection(args[0]);
            insertIncident(args[4]);
            insertOfficer(args[2]);
            insertRoute(args[3]);
            insertZone(args[1]);
		
        }catch (ArrayIndexOutOfBoundsException ae){
            System.out.println("Please pass correct number of arguments");
        }

    }
}
