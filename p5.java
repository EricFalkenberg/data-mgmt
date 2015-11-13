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
    public static void main(String[] args) throws SQLException {
        Connection conn;
        String url = "jdbc:mysql://falkenbr0.student.rit.edu" +
                     "/test?user=t_user&password=hunter2";      
        conn = DriverManager.getConnection(url);
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
}
