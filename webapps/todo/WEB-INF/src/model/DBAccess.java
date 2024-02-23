package model;

import java.util.ArrayList;
import java.util.List;
import java.security.NoSuchAlgorithmException;
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

  public DBAccess() {}

  public void create() throws Exception,SQLException {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/todo", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/todo/WEB-INF/db/todo.db"); //SQLite
      Statement stmt =  conn.createStatement();
      String sql = """
            CREATE TABLE IF NOT EXISTS users (
                username VARCHAR(256) NOT NULL PRIMARY KEY,
                password VARCHAR(256) NOT NULL
                )
                """;
      stmt.executeUpdate(sql);
      sql = """
            CREATE TABLE IF NOT EXISTS tasks (
                id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
                username VARCHAR(256) NOT NULL,
                task VARCHAR(256) NOT NULL
                )
                """;
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

  public void insert(String username, String task) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/todo", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/todo/WEB-INF/db/todo.db"); //SQLite
      String sql = "INSERT INTO tasks (username, task) VALUES (?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,username);
      pstmt.setString(2,task);
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

  public void insert(User user) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/todo", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/todo/WEB-INF/db/todo.db"); //SQLite
      String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
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

  public boolean existUser(String username) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/todo", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/todo/WEB-INF/db/todo.db"); //SQLite
      ResultSet rs = null;
      if (username.length() != 0) {
        String sql = "SELECT * FROM users WHERE username=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,username);
        rs = pstmt.executeQuery();
        return rs.next();
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
    return false;
  }

  public User select(String username) throws Exception,SQLException {
    User result = null;
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/todo", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/todo/WEB-INF/db/todo.db"); //SQLite
      ResultSet rs = null;
      if (username.length() != 0) {
        User user = new User(username);
        String sql = "SELECT * FROM users WHERE username=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,username);
        rs = pstmt.executeQuery();
        if (rs.next()) {
          String password = rs.getString("password");
          user.setPassword(password);
          sql = "SELECT * FROM tasks WHERE username=?";
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1,username);
          rs = pstmt.executeQuery();
          while (rs.next()) {
            String task = rs.getString("task");
            int id = rs.getInt("id");
            user.setTask(Integer.toString(id),task);
          }
          result = user;
        }
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
    return result;
  }

  public void update(String table, User user) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/todo", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/todo/WEB-INF/db/todo.db"); //SQLite
      if (table.equals("tasks")) {
        String sql = "UPDATE tasks SET task=? WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,user.getTask());
        pstmt.setInt(2,Integer.parseInt(user.getEditId()));
        int num = pstmt.executeUpdate();
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
  }

  public void delete(User user) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/todo", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/todo/WEB-INF/db/todo.db"); //SQLite

      String sql = "DELETE FROM tasks WHERE username=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,user.getUsername());
      int num = pstmt.executeUpdate();

      sql = "DELETE FROM users WHERE username=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,user.getUsername());
      num = pstmt.executeUpdate();

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

  public void deleteTask(User user, String id) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/todo", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/todo/WEB-INF/db/todo.db"); //SQLite
      String sql = "DELETE FROM tasks WHERE id=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1,Integer.parseInt(id));
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
}
