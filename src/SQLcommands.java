package src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLcommands {
    public String GetTimeLine(String username) {
        try {
            Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/codename_humankind",
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

}
