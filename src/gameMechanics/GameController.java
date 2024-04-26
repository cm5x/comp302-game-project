package gameMechanics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import gameComponents.Player;

public class GameController {
    public static ArrayList<Player> plist = new ArrayList<>();
    
    //database info
    String url = "jdbc:mysql://localhost:3306/sys";
    String usernamedb = "root";
    String passworddb = "ABC123*.";


    public void addPlayer(String username, String password){
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, usernamedb, passworddb);

            //yeni player ekleme
            String insertSql = "INSERT INTO PLAYER (username, pass) VALUES ('" + username + "', '" + password + "')";
            Statement insertStatement = connection.createStatement();
            insertStatement.executeUpdate(insertSql);
            Player newp = new Player(username, password);
            plist.add(newp);
        }

        catch(Exception e){
            System.out.println(e);
        }
    }

    public boolean verifyPlayer(String username, String password){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, usernamedb, passworddb);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PLAYER");
       

            while (resultSet.next()){
                if (resultSet.getString("username").equals(username)){
                    if (resultSet.getString("pass").equals(password)){
                        return true;
                    }
                }
                
            }

            return false;
        }

        catch(Exception e){
            System.out.println(e);
        }
        return false;
    }

    public void removePlayer(Player p){
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, usernamedb, passworddb);

        String deleteSql = "DELETE FROM PLAYER WHERE username = " + p.getName();
        PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
        deleteStatement.setString(1, p.getName());
        deleteStatement.executeUpdate();

        }

        catch(Exception e){
            System.out.println(e);
        }

        for(int i = 0; i < plist.size(); i++){
            Player player = plist.get(i);
            if(player.getName().equals(p.getName())){
                plist.remove(i);
                break; 
            }
        }
    }
}
