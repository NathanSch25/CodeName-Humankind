package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLcommands {
    String SQLaddress = "jdbc:mysql://127.0.0.1:3306/codename_humankind";
    public String GetTimeLine(String username) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            username = "'" + username + "'";
            Statement statement = connection.createStatement();
            String searchQuery = "SELECT CurrTimeLine FROM user_table where username = " + username;
            ResultSet resultSet = statement.executeQuery(searchQuery);
            while(resultSet.next()){
                return(resultSet.getString("Curr TimeLine"));
                //System.out.println(resultSet.getString("password"));
            }
            } catch (Exception e) {
                System.out.println(e);
            }
        return("-1");
    }
    public ArrayList<String> GetItem(String itemname) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            itemname = "'" + itemname + "'";
            Statement statement = connection.createStatement();
            String searchQuery = "SELECT * FROM item_table where name = " + itemname;
            ResultSet resultSet = statement.executeQuery(searchQuery);
            ArrayList<String> item = new ArrayList<String>();
            while(resultSet.next()){
                item.add(resultSet.getString("name"));
                item.add(resultSet.getString("corruption"));
                item.add(GetModifiersForItem(itemname).toString());
                
            }
            return(item);
            } catch (Exception e) {
                System.out.println(e);
            }
        return(null);
        
    }
    public ArrayList<String> GetModifiersForItem(String itemname){
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            itemname = "'" + itemname + "'";
            Statement statement = connection.createStatement();
            String searchQuery = "SELECT * FROM modifiershash where entityName = " + itemname;
            ResultSet resultSet = statement.executeQuery(searchQuery);
            ArrayList<String> item = new ArrayList<String>();
            while(resultSet.next()){
                item.add(resultSet.getString("Modifier"));
                item.add(resultSet.getString("value"));
                
            }
            return(item);
            } catch (Exception e) {
                System.out.println(e);
            }
        return(null);
    }
    public int GetModifierValue(String entity, String modifier) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            String theRest = "'" + entity + "' AND Modifier = " + "'" + modifier + "'";
            Statement statement = connection.createStatement();
            String searchQuery = "SELECT value FROM modifiershash where entityName = " + theRest;
            ResultSet resultSet = statement.executeQuery(searchQuery);
            while(resultSet.next()){
                return(resultSet.getInt("value"));
                //System.out.println(resultSet.getString("password"));
            }
            } catch (Exception e) {
                System.out.println(e);
            }
        return(-1);
    }
    public ArrayList<String> GetALLModifiers(String entity) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            entity = "'" + entity + "'";
            Statement statement = connection.createStatement();
            String searchQuery = "SELECT * FROM modifiershash where entityName = " + entity;
            ResultSet resultSet = statement.executeQuery(searchQuery);
            ArrayList<String> modifiers = new ArrayList<String>();
            while(resultSet.next()){
                modifiers.add(resultSet.getString("Modifier"));
                //System.out.println(resultSet.getString("password"));
            }
            return(modifiers);
            } catch (Exception e) {
                System.out.println(e);
            }
        return(null);
    }
    
    public void SetModifierValue(String entity, String modifier, int increment) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            boolean errors = false;
            String theRest = "WHERE entityName = '" + entity + "' AND Modifier = '" + modifier + "'";
            Statement statement = connection.createStatement();
            String searchQuery;
            try{
                int value = GetModifierValue(entity, modifier);
                searchQuery = "UPDATE modifiershash SET value = " + (value + increment) + " " + theRest;
                //statement.executeQuery(searchQuery);
                if ((statement.executeUpdate(searchQuery)) == 0){
                    errors = true;
                }
                System.out.println("Updated");
                
            }
            catch(Exception e){
                System.out.println("Not found");
                errors = true;

            }
            if (errors){
                try {
                    theRest = "('" + entity + "', '" + modifier + "', '" + increment + "')";
                    searchQuery = "INSERT INTO modifiershash (entityName, Modifier, value) VALUES " + theRest;
                    statement.executeUpdate(searchQuery);
                    System.out.println("added");
                } catch (Exception e) {
                    System.out.println("not added");
                }
            }
            
                //System.out.println(resultSet.getString("password"));
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    public void AddItem(String itemname, String corruption, String modifierName, String modifierValue) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            itemname = "'" + itemname + "'";
            corruption = "'" + corruption + "'";
            
            Statement statement = connection.createStatement();
            String searchQuery = "INSERT INTO item_table (name, corruption) VALUES (" + itemname + ", " + corruption +")";
            statement.executeUpdate(searchQuery);
            
            } catch (Exception e) {
                System.out.println(e);
            }
            SetModifierValue(itemname, modifierName, Integer.parseInt(modifierValue));
        
    }
    public void SetTimeLine(String username, String timeline) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            username = "'" + username + "'";
            timeline = "'" + timeline + "'";
            Statement statement = connection.createStatement();
            String searchQuery = "UPDATE user_table SET CurrTimeLine = " + timeline + " WHERE username = " + username;
            statement.executeUpdate(searchQuery);
            } catch (Exception e) {
                System.out.println(e);
            }
    }
    public void AddUser(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            username = "'" + username + "'";
            password = "'" + password + "'";
            Statement statement = connection.createStatement();
            String searchQuery = "INSERT INTO user_table (username, password) VALUES (" + username + ", " + password +")";
            statement.executeUpdate(searchQuery);
            } catch (Exception e) {
                System.out.println(e);
            }
    }
    public void UpdateTimeLine(String username, String timeline) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            username = "'" + username + "'";
            timeline = "'" + GetTimeLine(username) + timeline + "'";
            Statement statement = connection.createStatement();
            String searchQuery = "UPDATE user_table SET CurrTimeLine = " + timeline + " WHERE username = " + username;
            statement.executeUpdate(searchQuery);
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    public Boolean UserExists(String username) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            username = "'" + username + "'";
            Statement statement = connection.createStatement();
            String searchQuery = "SELECT * FROM user_table where username = " + username;
            ResultSet resultSet = statement.executeQuery(searchQuery);
            while(resultSet.next()){
                return(true);
                //System.out.println(resultSet.getString("password"));
            }
            } catch (Exception e) {
                System.out.println(e);
            }
        return(false);
    }
    public Boolean checkPassword(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(
                SQLaddress,
                "root",
                "test"
            );
            username = "'" + username + "'";
            password = "'" + password + "'";
            Statement statement = connection.createStatement();
            String searchQuery = "SELECT * FROM user_table where username = " + username + " AND password = " + password;
            ResultSet resultSet = statement.executeQuery(searchQuery);
            while(resultSet.next()){
                return(true);
                //System.out.println(resultSet.getString("password"));
            }
            } catch (Exception e) {
                System.out.println(e);
            }
        return(false);
    }
    
}
