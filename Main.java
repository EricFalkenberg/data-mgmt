import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

class Main {

    public static void main(String[] args) {
        int id;
        String first;
        String last;
        Connection conn = null;
        try {
            // INSTANTIATE CONNECTION
            conn = DriverManager.getConnection("jdbc:mysql://falkenbr0.student.rit.edu" + 
                "/test?user=t_user&password=hunter2");
            // INSTANTIATE AND EXECUTE A STATEMENT
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM mock");
            // RETRIEVE FIRST RESULT
            rs.first();
            id = rs.getInt(1);
            first = rs.getString(2);
            last = rs.getString(3);
            // PRINT THE RESULTS
            System.out.print(id + " ");
            System.out.print(first + " ");
            System.out.println(last);
        }
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }   
}
