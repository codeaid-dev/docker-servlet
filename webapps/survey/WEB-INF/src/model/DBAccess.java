package model;

import java.util.HashMap;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import jakarta.servlet.ServletContext;

import model.User;

public class DBAccess {
  private static final String USER = "root";
  private static final String PASS = "password";
  public DBAccess() {}

  public static void create() throws Exception,SQLException {
    Connection conn = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite
      Statement stmt =  conn.createStatement();
      String sql = """
            CREATE TABLE IF NOT EXISTS siteadmin (
                username VARCHAR(256) NOT NULL PRIMARY KEY,
                password VARCHAR(256) NOT NULL
                )""";
      stmt.executeUpdate(sql);
      //MySQL
      sql = """
            CREATE TABLE IF NOT EXISTS answers (
                created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                name VARCHAR(256) NOT NULL,
                email VARCHAR(256) NOT NULL PRIMARY KEY,
                age VARCHAR(256) NOT NULL,
                program VARCHAR(256),
                pc VARCHAR(256) NOT NULL,
                maker VARCHAR(256) NOT NULL,
                comments VARCHAR(256)
                )""";
      //SQLite
      /*sql = """
              CREATE TABLE IF NOT EXISTS answers (
                created_at DATETIME DEFAULT (DATETIME('now','localtime')),
                name VARCHAR(256) NOT NULL,
                email VARCHAR(256) NOT NULL PRIMARY KEY,
                age VARCHAR(256) NOT NULL,
                program VARCHAR(256),
                pc VARCHAR(256) NOT NULL,
                maker VARCHAR(256) NOT NULL,
                comments VARCHAR(256)
                )""";*/
      stmt.executeUpdate(sql);
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

  public static void addSurvey(String[] columns) throws Exception,SQLException {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite
      String sql = "INSERT INTO answers (name,email,age,program,pc,maker,comments) VALUES (?,?,?,?,?,?,?)";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,columns[0]);
      pstmt.setString(2,columns[1]);
      pstmt.setString(3,columns[2]);
      pstmt.setString(4,columns[3]);
      pstmt.setString(5,columns[4]);
      pstmt.setString(6,columns[5]);
      pstmt.setString(7,columns[6]);
      int num = pstmt.executeUpdate();
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

  public static void addUser(User user) throws Exception,SQLException {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite
      String sql = "INSERT INTO siteadmin (username, password) VALUES (?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,user.getUsername());
      pstmt.setString(2,user.getPassword());
      int num = pstmt.executeUpdate();
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

  public static boolean existUser() throws Exception,SQLException {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite
      ResultSet rs = null;
      String sql = "SELECT * FROM siteadmin";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      return rs.next();
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

  public static User selectUser(String username) throws Exception,SQLException {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite
      ResultSet rs = null;
      String sql = "SELECT * FROM siteadmin WHERE username=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,username);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String password = rs.getString("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
      }
      return null;
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

  public static boolean existMail(String email) throws Exception,SQLException {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite
      ResultSet rs = null;
      String sql = "SELECT * FROM answers WHERE email=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,email);
      rs = pstmt.executeQuery();
      return rs.next();
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

  public static ArrayList<HashMap<String,String>> selectSurvey() throws Exception,SQLException {
    Connection conn = null;
    ArrayList<HashMap<String,String>> result = new ArrayList<>();
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite
      ResultSet rs = null;
      String sql = "SELECT * FROM answers";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        HashMap<String,String> survey = new HashMap<>();
        survey.put("created_at",rs.getString("created_at"));
        survey.put("name",rs.getString("name"));
        survey.put("email",rs.getString("email"));
        survey.put("age",rs.getString("age"));
        survey.put("program",rs.getString("program"));
        survey.put("pc",rs.getString("pc"));
        survey.put("maker",rs.getString("maker"));
        survey.put("comments",rs.getString("comments"));
        result.add(survey);
      }
      return result;
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

  public static void deleteSurvey(String email) throws Exception,SQLException {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite

      String sql = "DELETE FROM answers WHERE email=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,email);
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
  public static void deleteSurveyAll() throws Exception,SQLException {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite

      String sql = "DELETE FROM answers";
      PreparedStatement pstmt = conn.prepareStatement(sql);
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
  public static void deleteUser() throws Exception,SQLException {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite

      String sql = "DELETE FROM siteadmin";
      PreparedStatement pstmt = conn.prepareStatement(sql);
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
}
