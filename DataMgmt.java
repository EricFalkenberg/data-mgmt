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
            if (args[0].equals("populate")) {
                db.delete_from_relations();
                db.populate_relations();
            }
            else if (args[0].equals("profile_indexing")) {
                long startTime;
                long endTime;
                System.out.println("Profiling Queries 7 and 8 without indexes...");
                System.out.println("----------------------------------------------------------------------------------");
                startTime = System.nanoTime();
                db.get_high_level_low_raiding_guild_players();
                endTime = System.nanoTime();
                System.out.println("QUERY 7 DURATION: " + (endTime-startTime)/1000000 + " milliseconds");
                System.out.println("----------------------------------------------------------------------------------");
                startTime = System.nanoTime();
                db.no_guild_aerie_peak();
                endTime = System.nanoTime();
                System.out.println("QUERY 8 DURATION: " + (endTime-startTime)/1000000 + " milliseconds");
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println("Adding indexes to model...");
                db.add_indexes();
                System.out.println("Profiling Queries 7 and 8 with indexes...");
                System.out.println("----------------------------------------------------------------------------------");
                startTime = System.nanoTime();
                db.get_high_level_low_raiding_guild_players();
                endTime = System.nanoTime();
                System.out.println("QUERY 7 DURATION: " + (endTime-startTime)/1000000 + " milliseconds");
                System.out.println("----------------------------------------------------------------------------------");
                startTime = System.nanoTime();
                db.no_guild_aerie_peak();
                endTime = System.nanoTime();
                System.out.println("QUERY 8 DURATION: " + (endTime-startTime)/1000000 + " milliseconds");
                System.out.println("----------------------------------------------------------------------------------");
                return;
            }
            else {
                System.out.println("usage: java DataMgmt [populate] [profile_indexing]");
                return;
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
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Query 8: Query to return all the characters in the realm 'Aerie Peak' who are not"); 
        System.out.println("         in a guild and whose level is above 50");
        System.out.println("| Name |");
        db.no_guild_aerie_peak();
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Query 9: List the names and the levels of all characters who completed a quest ");
        System.out.println("         below the required level. (Expected: No Entries Returned)");
        System.out.println("No Entries Returned");
        db.below_required_level();
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Query 10: List of all warrior talents that grant abilities");
        System.out.println("| Name |");
        db.warrior_ability_grants();
    }   
}
