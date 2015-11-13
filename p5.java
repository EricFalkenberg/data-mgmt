import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Random;
import java.lang.Integer;
import java.sql.Date;

class p5 {

    public static void profileSQL(Connection conn) throws SQLException {
        Statement s = conn.createStatement();
        long startTime = System.nanoTime();
        s.executeQuery("SELECT lastName FROM Patient WHERE lastName='last500000' AND zipCode >= 10000 AND" +
                       " zipCode <= 69999");
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Query 1 duration: " + duration + " milliseconds");
        startTime = System.nanoTime();
        s.executeQuery("SELECT lastName FROM Patient WHERE firstName='first169' AND zipCode >= 60000 AND" +
                       " zipCode <= 70000");
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Query 2 duration: " + duration + " milliseconds");
        System.out.println();
        startTime = System.nanoTime();
        s.executeQuery("SELECT lastName FROM Patient WHERE lastName = 'last420' AND birthDate >= 1970-01-01 AND " +
                       "birthDate <= 1970-01-30");
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Query 3 duration: " + duration + " milliseconds");
        startTime = System.nanoTime();
        s.executeQuery("SELECT firstName FROM Patient WHERE lastName = 'first420' AND birthDate >= 1970-01-01 AND " +
                       "birthDate <= 1979-12-01");
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Query 4 duration: " + duration + " milliseconds");
        System.out.println();
        startTime = System.nanoTime();
        String f = "%";
        s.executeQuery(String.format("SELECT zipCode FROM Patient WHERE lastName = 'last169' AND firstName LIKE %s","'first%'"));
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Query 5 duration: " + duration + " milliseconds");
        startTime = System.nanoTime();
        s.executeQuery(String.format("SELECT birthDate FROM Patient WHERE zipCode = 30000 AND lastName LIKE %s", "'last%'"));
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println("Query 6 duration: " + duration + " milliseconds");
    }

    public static void generatePatients(Connection conn) throws SQLException {
        Statement s = conn.createStatement();
        Random gen = new Random();
        long unixtime;
        for (int i=0; i < 1000000; i++) {
            s = conn.createStatement();
            String ssn = String.format("%09d", i); 
            ssn = ssn.substring(0,3) + "-" + ssn.substring(3,5) + "-" + ssn.substring(5);
            String zipCode = Integer.toString(gen.nextInt(99999 - 10000) + 10000);
            String f_name = "first" + Integer.toString(i);
            String l_name = "last" + Integer.toString(i);
            unixtime = (long)(1293861599+gen.nextDouble()*60*60*24*365);
            Date d = new Date(unixtime);
            s.executeUpdate(String.format("INSERT INTO Patient (ssn, firstName, lastName, birthDate, zipCode)" + 
                                          " VALUES ('%s', '%s', '%s', '%s', '%s')", ssn, f_name, l_name, d, zipCode));
        }

    }
    
    public static void main(String[] args) throws SQLException {
        Connection conn;
        String url = "jdbc:mysql://localhost" +
                     "/test?user=t_user&password=hunter2";      
        conn = DriverManager.getConnection(url);
        p5.profileSQL(conn);
    }
}
