import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

class DataMgmt {

    public static void main(String[] args) {
        DatabaseConnection db = new DatabaseConnection("jdbc:mysql://localhost" + 
                                           "/test?user=t_user&password=hunter2");
        if (args.length > 0) {
            if (args[0] == "populate") {
                db.delete_from_relations();
                db.populate_relations();
            }
            else {
                System.out.println("usage: java DataMgmt [populate]");
            }
        }
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Query 1: Character appearance combined with armor appearance for Character: 'Alex'");
        System.out.println("| Appearance |");
        db.get_overall_appearance();  
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Query 2: Find list of cities in which any given character has bought a mount.");
        System.out.println("| Character | Mount | City |");
        db.bought_mount_from();              
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Query 3: Retrieve the number of tracked quests for character 'Mark'"); 
        System.out.println("| Count |");
        db.num_tracked_quests();
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Query 4: All equipment that character 'Alex' is able to equip");
        System.out.println("| Equipment |");
        db.equippable();
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Query 5: All quests that are trackable by player character 'Joe'");
        System.out.println("| ID | Name |");
        db.trackable();
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Query 6: Returns the highest leveled character in the guild 'Relations'"); 
        System.out.println("| Name |");
        db.get_best_char_in_guild();
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Query 7: Returns characters level 45 and higher that are only a part of raiding   ");
        System.out.println("         guilds of a level lower than 20");
        System.out.println("| Name |");
        db.get_high_level_low_raiding_guild_players();
    }   
}
