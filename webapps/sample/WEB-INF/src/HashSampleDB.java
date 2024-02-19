import model.User;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class HashSampleDB {
  private static final String USER = "root";
  private static final String PASS = "password";
  private static Connection conn = null;

  public static void insert(String username, String password) throws Exception, SQLException{
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/sampledb", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/sample/WEB-INF/db/sample.db"); //SQLite
      String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,username);
      pstmt.setString(2,password);
      pstmt.executeUpdate();
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        throw e;
      }
    }
  }

  public static void update(String username, String password) throws Exception, SQLException{
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/sampledb", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/sample/WEB-INF/db/sample.db"); //SQLite
      String sql = "UPDATE users SET password=? WHERE username=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,password);
      pstmt.setString(2,username);
      pstmt.executeUpdate();
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        throw e;
      }
    }
  }

  public static User select(String username) throws Exception, SQLException {
    User user = null;
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/sampledb", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/sample/WEB-INF/db/sample.db"); //SQLite
      ResultSet rs = null;
      String sql = "SELECT * FROM users WHERE username=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,username);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String password = rs.getString("password");
        user = new User(username);
        user.setPassword(password);
        break;
      }
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        throw e;
      }
    }
    return user;
  }
}
