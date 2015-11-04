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

class DatabaseConnection {
    
    private Connection conn = null;    

    public DatabaseConnection(String url) {
        try {
            this.conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.out.println("Error: could not connect to Database at " + url);
        }
    }

    private String readFile(String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("An error occurred while reading from file");
            return null;
        }
    }

    public void run(String sql, String type) {
        ResultSet rs;
        if (sql.trim().length() == 0) {
            return;
        }
        try {
            switch (type) {
                case "command":  this.command(sql);  
                                 break;            
                case "query"  :  rs = this.query(sql);
                                 while (rs.next()) {
                                     int i = 1;
                                     System.out.print("| ");
                                     while (i <= rs.getMetaData().getColumnCount()) {
                                         System.out.print(rs.getString(i++)+ " | ");
                                     }   System.out.println();
                                 }            
                                 break;
                default:         System.out.println("Method not found"); 
                                 break;
            }
        } catch (SQLException ex) {
            System.out.println(sql);
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState:     " + ex.getSQLState());
            System.out.println("VendorError:  " + ex.getErrorCode());
        }
    }

    private void command(String sql) throws SQLException {
        Statement s = conn.createStatement();
        s.executeUpdate(sql);
    }
    
    private ResultSet query(String sql)   throws SQLException {
        Statement s = conn.createStatement();
        return s.executeQuery(sql);
    }

    public void populate_relations() {
        // RUN THE POPULATION QUERIES
        System.out.println("Populating relations....");
        String scm = readFile("schema.inf");
        String[] cmds;
        if (scm != null) {
            cmds = Pattern.compile(";", Pattern.LITERAL).split(scm);
            for (int i = 0; i < cmds.length; i++) {
                this.run(cmds[i], "command");
            }
        }
        String dat = readFile("data.inf") ;
        if (dat != null) {
            cmds = Pattern.compile(";", Pattern.LITERAL).split(dat);
            for (int i = 0; i < cmds.length; i++) {
                this.run(cmds[i], "command");
            }
        }
    }

    public void delete_from_relations() {
        System.out.println("Wiping relations....");
        this.run("DELETE FROM Mounts"    , "command");
        this.run("DELETE FROM City"      , "command");
        this.run("DELETE FROM Species"   , "command");
        this.run("DELETE FROM Quests"    , "command");
        this.run("DELETE FROM Guilds"    , "command");
        this.run("DELETE FROM Realms"    , "command");
        this.run("DELETE FROM Talents"   , "command");
        this.run("DELETE FROM Classes"   , "command");
        this.run("DELETE FROM Characters", "command");
        this.run("DELETE FROM Equipment" , "command");
        this.run("DELETE FROM PreReq"    , "command");
        this.run("DELETE FROM Given"     , "command");
        this.run("DELETE FROM Owns"      , "command");
        this.run("DELETE FROM Tracks"    , "command");
        this.run("DELETE FROM Attains"   , "command");
        this.run("DELETE FROM Equips"    , "command");
        this.run("DELETE FROM Unlocks"   , "command");
        this.run("DROP TABLE Mounts"     , "command");
        this.run("DROP TABLE City"       , "command");
        this.run("DROP TABLE Species"    , "command");
        this.run("DROP TABLE Quests"     , "command");
        this.run("DROP TABLE Guilds"     , "command");
        this.run("DROP TABLE Realms"     , "command");
        this.run("DROP TABLE Talents"    , "command");
        this.run("DROP TABLE Classes"    , "command");
        this.run("DROP TABLE Characters" , "command");
        this.run("DROP TABLE Equipment"  , "command");
        this.run("DROP TABLE PreReq"     , "command");
        this.run("DROP TABLE Given"      , "command");
        this.run("DROP TABLE Owns"       , "command");
        this.run("DROP TABLE Tracks"     , "command");
        this.run("DROP TABLE Attains"    , "command");
        this.run("DROP TABLE Equips"     , "command");
        this.run("DROP TABLE Unlocks"    , "command");
    }

    public void get_overall_appearance() {
        String sql = "(SELECT appearance FROM Characters WHERE name='Alex') " + 
                     "UNION " + 
                     "(SELECT e.appearance FROM ((Equips JOIN Equipment AS e ON equipment=e.name) " + 
                     "JOIN Characters as a ON player=a.name) WHERE a.name=player and player='Alex')";
        this.run(sql, "query");
    }

    public void bought_mount_from() {
        String sql = "SELECT c.name, m.name, city FROM (Characters AS c JOIN Owns ON c.name=player) JOIN Mounts AS m ON m.id=mount";
        this.run(sql, "query");
    }

    public void num_tracked_quests() {
        String sql = "SELECT COUNT(id) FROM (Tracks JOIN Quests ON quest=id) JOIN Characters ON name=player WHERE player='Mark'";
        this.run(sql, "query");
    }

    public void equippable() {
        String sql = "SELECT e.name FROM (Equips JOIN Equipment AS e ON equipment=name) JOIN Characters AS c ON player=c.name " +
                     "WHERE c.level >= e.required_level AND (c.class=e.required_class OR e.required_class IS NULL) AND player='Alex'";
        this.run(sql, "query");
    }
    
    public void trackable() {
        String sql = "SELECT id, title  FROM (Characters JOIN Tracks ON name=player) JOIN Quests ON quest=id WHERE " +
                     "level >= required_level AND (class=required_class OR required_class IS NULL) AND player='Joe'";
        this.run(sql, "query");
    }

}
