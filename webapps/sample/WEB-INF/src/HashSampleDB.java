import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class HashSampleDB {
  private static final String USER = "root";
  private static final String PASS = "password";

  public static void insert(String username, String password) throws SQLException, ClassNotFoundException{
    String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
      try {
        Class.forName("com.mysql.jdbc.Driver"); //MySQL
        //Class.forName("org.sqlite.JDBC"); //SQLite
      } catch (ClassNotFoundException e) {
        throw e;
      }
      try (Connection conn = DriverManager.getConnection("jdbc:mysql://mysql/sampledb", USER, PASS);
        PreparedStatement pstmt = conn.prepareStatement(sql);) {
//    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:webapps/sample/WEB-INF/db/sample.db");
//    PreparedStatement pstmt = conn.prepareStatement(sql);) {      
      pstmt.setString(1,username);
      pstmt.setString(2,password);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw e;
    }
  }

  public static void update(String username, String password) throws SQLException, ClassNotFoundException{
    String sql = "UPDATE users SET password=? WHERE username=?";
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
    } catch (ClassNotFoundException e) {
      throw e;
    }
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://mysql/sampledb", USER, PASS);
        PreparedStatement pstmt = conn.prepareStatement(sql);) {
//    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:webapps/sample/WEB-INF/db/sample.db");
//    PreparedStatement pstmt = conn.prepareStatement(sql);) {
      pstmt.setString(1,password);
      pstmt.setString(2,username);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw e;
    }
  }

  public static User select(String username) throws SQLException, ClassNotFoundException {
    User user = null;
    String sql = "SELECT * FROM users WHERE username=?";
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
    } catch (ClassNotFoundException e) {
      throw e;
    }
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://mysql/sampledb", USER, PASS);
        PreparedStatement pstmt = conn.prepareStatement(sql);) {
//    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:webapps/sample/WEB-INF/db/sample.db");
//    PreparedStatement pstmt = conn.prepareStatement(sql);) {
      pstmt.setString(1,username);
      try (ResultSet rs = pstmt.executeQuery();) {
        while (rs.next()) {
          String password = rs.getString("password");
          user = new User(username);
          user.setPassword(password);
          break;
        }
      } catch (SQLException e) {
        throw e;
      }
    } catch (SQLException e) {
      throw e;
    }
    return user;
  }
}
