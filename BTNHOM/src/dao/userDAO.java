package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class userDAO {
	public static Connection getConnection() {
        Connection connection = null;
        try {
            //Dang ky 
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
         
            String url = "jdbc:mysql://localhost:3306/qlsv";
            String user = "ca";
            String password = "H963852741";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return connection;
    }
}
