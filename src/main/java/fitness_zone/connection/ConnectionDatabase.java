package fitness_zone.connection;

import java.sql.*;

public class ConnectionDatabase {
  public static Connection connect() {
      Connection connection = null;
      String databaseName = "fitness_zone";
      String url = "jdbc:mysql://localhost:3306/" + databaseName;
      String user = "";
      String password = "";
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException | SQLException e) {
      System.out.println("Error when trying to connect to the database: " + e.getMessage());
      System.out.println("Exception type: " + e.getClass().getName());
      System.out.println("Exception parent class: " + e.getClass().getSuperclass().getName());
    }
    return connection;
  }
}
