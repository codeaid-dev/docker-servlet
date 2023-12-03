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
  private final String USER = "root";
  private final String PASS = "password";
  private Connection conn = null;
  private ServletContext app = null; // for Logging
  public DBAccess(ServletContext app) {
    this.app = app;
  }
  public void create() {
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
      stmt.executeUpdate(sql);
    } catch (Exception e) {
      app.log(e.getMessage(), e);
      //e.printStackTrace();
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        app.log(e.getMessage(), e);
        //e.printStackTrace();
      }
    }
  }

  public void addSurvey(String[] columns) {
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
      app.log(e.getMessage(), e);
      //e.printStackTrace();
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        app.log(e.getMessage(), e);
        //e.printStackTrace();
      }
    }
  }

  public void addUser(User user) {
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
      app.log(e.getMessage(), e);
      //e.printStackTrace();
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        app.log(e.getMessage(), e);
        //e.printStackTrace();
      }
    }
  }

  public boolean existUser() {
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
      app.log(e.getMessage(), e);
      //e.printStackTrace();
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        app.log(e.getMessage(), e);
        //e.printStackTrace();
      }
    }
    return false;
  }

  public User selectUser(String username) {
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
        user.setPassword(password, false);
        return user;
      }
    } catch (Exception e) {
      app.log(e.getMessage(), e);
      //e.printStackTrace();
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        app.log(e.getMessage(), e);
        //e.printStackTrace();
      }
    }
    return null;
  }

  public boolean existMail(String email) {
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
      app.log(e.getMessage(), e);
      //e.printStackTrace();
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        app.log(e.getMessage(), e);
        //e.printStackTrace();
      }
    }
    return false;
  }

  public ArrayList<HashMap<String,String>> selectSurvey() {
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
      app.log(e.getMessage(), e);
      //e.printStackTrace();
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        app.log(e.getMessage(), e);
        //e.printStackTrace();
      }
    }
    return result;
  }

  public void deleteSurvey(String email) {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite

      String sql = "DELETE FROM answers WHERE email=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,email);
      int num = pstmt.executeUpdate();
    } catch (Exception e) {
      app.log(e.getMessage(), e);
      //e.printStackTrace();
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        app.log(e.getMessage(), e);
        //e.printStackTrace();
      }
    }
  }
  public void deleteSurveyAll() {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite

      String sql = "DELETE FROM answers";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      int num = pstmt.executeUpdate();
    } catch (Exception e) {
      app.log(e.getMessage(), e);
      //e.printStackTrace();
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        app.log(e.getMessage(), e);
        //e.printStackTrace();
      }
    }
  }
  public void deleteUser() {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/survey", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/survey/WEB-INF/db/survey.db"); //SQLite

      String sql = "DELETE FROM siteadmin";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      int num = pstmt.executeUpdate();
    } catch (Exception e) {
      app.log(e.getMessage(), e);
      //e.printStackTrace();
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        app.log(e.getMessage(), e);
        //e.printStackTrace();
      }
    }
  }

  public static String escape(String str) {
    if (str != null) {
      str = str.replaceAll("&","&amp;");
      str = str.replaceAll("<","&lt;");
      str = str.replaceAll(">","&gt;");
      str = str.replaceAll("'","&#39;");
      str = str.replaceAll("\"","&quot;");
      return str;
    }
    return null;
  }
}
