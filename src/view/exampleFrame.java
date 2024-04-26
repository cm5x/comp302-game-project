package view;


import java.sql.Statement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class exampleFrame {
    public static void main(String[] args) {
        //test the frame
        //settingsPage st = new settingsPage();
        String url = "jdbc:mysql://localhost:3306/sys";
        String username = "root";
        String password = "ABC123*.";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            //yeni player ekleme
            String insertSql = "INSERT INTO PLAYER (username, pass) VALUES ('user1', 'password1')";
            Statement insertStatement = connection.createStatement();
            insertStatement.executeUpdate(insertSql);

            //playerları listeleme
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PLAYER");
       

            while (resultSet.next()){
                System.out.println(resultSet.getString("username"));
                System.out.println(resultSet.getString("pass"));
            }

        }

        catch(Exception e){
            System.out.println();
            System.out.println();
            System.out.println(e);
            System.out.println();
            System.out.println();
        }

    }
}