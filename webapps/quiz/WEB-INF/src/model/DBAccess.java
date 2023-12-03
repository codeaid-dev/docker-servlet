package model;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import jakarta.servlet.ServletContext;

import model.Quiz;

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
      conn = DriverManager.getConnection("jdbc:mysql://mysql/quiz", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/quiz/WEB-INF/db/quiz.db"); //SQLite
      // For MySQL using AUTO_INCREMENT, for SQLite using AUTOINCREMENT
      String sql = """
        CREATE TABLE IF NOT EXISTS questions (
            id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
            question VARCHAR(255) NOT NULL,
            answer VARCHAR(255) NOT NULL)
        """;
      Statement stmt =  conn.createStatement();
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

  public void insert(Quiz quiz) {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/quiz", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/quiz/WEB-INF/db/quiz.db"); //SQLite
      String sql = "INSERT INTO questions (question, answer) VALUES (?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,quiz.getQuestion());
      pstmt.setString(2,quiz.getAnswer());
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

  public ArrayList<Quiz> select(Quiz quiz) {
    ArrayList<Quiz> result = new ArrayList<Quiz>();
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/quiz", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/quiz/WEB-INF/db/quiz.db"); //SQLite
      ResultSet rs = null;
      if (quiz != null) {
        String sql = "SELECT * FROM questions WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,Integer.parseInt(quiz.getID()));
        rs = pstmt.executeQuery();
      } else {
        String sql = "SELECT * FROM questions";
        Statement stmt =  conn.createStatement();
        rs = stmt.executeQuery(sql);
      }
      while (rs.next()) {
        String id = String.valueOf(rs.getInt("id"));
        String answer = rs.getString("answer");
        String question = rs.getString("question");
        result.add(new Quiz(id,question,answer));
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
    return result;
  }

  public void update(Quiz quiz) {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/quiz", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/quiz/WEB-INF/db/quiz.db"); //SQLite
      String sql = "UPDATE questions SET question=?, answer=? WHERE id=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,quiz.getQuestion());
      pstmt.setString(2,quiz.getAnswer());
      pstmt.setInt(3,Integer.parseInt(quiz.getID()));
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

  public void delete(Quiz quiz) {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/quiz", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/quiz/WEB-INF/db/quiz.db"); //SQLite
      String sql = "DELETE FROM questions WHERE id=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1,Integer.parseInt(quiz.getID()));
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
}
