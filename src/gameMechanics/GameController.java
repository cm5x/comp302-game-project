package gameMechanics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gameComponents.Player;

public class GameController {
    
    public HashMap<String, String> getDataFromText(){
        HashMap<String, String> userdata = new HashMap<>();
        try {
            
            FileReader fread = new FileReader("src/utilities/GameSaves/players.txt");
            BufferedReader reader = new BufferedReader(fread);

            String line;
         
            while ((line = reader.readLine()) != null) {
                String[] splitted = line.split(",");
                String username = splitted[0];
                String password = splitted[1];
                userdata.put(username, password);
            }
            reader.close();
        } 
        catch (IOException e) {
            System.out.println(e);
        }

        return userdata;
    }
    
   
    public ArrayList<Player> getPlayerList(){
        ArrayList<Player> plist = new ArrayList<>();
        HashMap<String, String> dlist = this.getDataFromText();
        for (Map.Entry<String, String> entry : dlist.entrySet()){
            Player player = new Player(entry.getKey(), entry.getValue());
            plist.add(player);
        }

        return plist;

        
    }

    public void addPlayer(String username, String password){
        try {
            
            FileWriter fw = new FileWriter("src/utilities/GameSaves/players.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(username + "," + password);
            writer.newLine();          
            writer.close();
        } 
        catch (IOException e) {
            System.out.println(e);
        }

    }

    public void update(ArrayList<Player> updatedplayers){
        try {
            
            FileWriter fw = new FileWriter("src/utilities/GameSaves/players.txt");
            BufferedWriter writer = new BufferedWriter(fw);
            for (Player p : updatedplayers){
                writer.write(p.getName() + "," + p.getPass());
                writer.newLine();
            }
          
            writer.close();
        } 
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public boolean verifyPlayer(String username, String password){
       
        HashMap<String, String> plist = this.getDataFromText();
        for (Map.Entry<String, String> entry : plist.entrySet()){
            if (entry.getKey().equals(username)){
                if (entry.getValue().equals(password)){
                    return true;
                }
                break;
                
                
            }

            
        }
        
        return false;
    }

}
