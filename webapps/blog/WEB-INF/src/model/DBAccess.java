package model;

import java.util.HashMap;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import jakarta.servlet.ServletContext;

import model.User;
import model.Post;

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
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite
      Statement stmt =  conn.createStatement();
      String sql = """
            CREATE TABLE IF NOT EXISTS siteadmin (
                username VARCHAR(256) NOT NULL PRIMARY KEY,
                password VARCHAR(256) NOT NULL
                )""";
      stmt.executeUpdate(sql);
      //MySQL
      sql = """
          CREATE TABLE IF NOT EXISTS posts (
            id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
            title VARCHAR(256) NOT NULL,
            article VARCHAR(256) NOT NULL
          )""";
      //SQLite
      /*sql = """
          CREATE TABLE IF NOT EXISTS posts (
            id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
            created_at DATETIME DEFAULT (DATETIME('now','localtime')),
            updated_at DATETIME DEFAULT (DATETIME('now','localtime')),
            title VARCHAR(256) NOT NULL,
            article VARCHAR(256) NOT NULL
            )""";
      stmt.executeUpdate(sql);
      sql = """
          CREATE TRIGGER IF NOT EXISTS trigger_updated_at AFTER UPDATE ON posts
              BEGIN
                UPDATE posts SET updated_at = DATETIME('now', 'localtime') WHERE rowid == NEW.rowid;
              END
            """;*/
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

  public void addPost(String title, String article) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite
      String sql = "INSERT INTO posts (title,article) VALUES (?,?)";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,title);
      pstmt.setString(2,article);
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

  public void addUser(User user) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite
      String sql = "INSERT INTO siteadmin (username, password) VALUES (?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,user.getUsername());
      pstmt.setString(2,user.getPassword());
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

  public boolean existUser() throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite
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

  public User selectUser(String username) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite
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
    } catch (Exception e) {
      throw e;
    } finally {
      try {
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        throw e;
      }
    }
    return null;
  }

  public ArrayList<Post> selectPostAll() throws Exception,SQLException {
    ArrayList<Post> result = new ArrayList<>();
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite
      ResultSet rs = null;
      String sql = "SELECT * FROM posts ORDER BY updated_at DESC";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Post post = new Post();
        post.setId(rs.getInt("id"));
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date wd = sd.parse(rs.getString("created_at"));
        String datetime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(wd);
        String date = new SimpleDateFormat("yyyy/MM/dd").format(wd);
        post.setCreatedDatetime(datetime);
        post.setCreatedDate(date);
        sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        wd = sd.parse(rs.getString("updated_at"));
        datetime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(wd);
        date = new SimpleDateFormat("yyyy/MM/dd").format(wd);
        post.setUpdatedDatetime(datetime);
        post.setUpdatedDate(date);
        post.setTitle(escape(rs.getString("title")));
        post.setArticle(escape(rs.getString("article")));
        result.add(post);
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

  public Post selectPost(int id) throws Exception,SQLException {
    Post result = new Post();
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite
      ResultSet rs = null;
      String sql = "SELECT * FROM posts WHERE id=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1,id);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        result.setId(rs.getInt("id"));
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date wd = sd.parse(rs.getString("created_at"));
        String datetime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(wd);
        String date = new SimpleDateFormat("yyyy/MM/dd").format(wd);
        result.setCreatedDatetime(datetime);
        result.setCreatedDate(date);
        sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        wd = sd.parse(rs.getString("updated_at"));
        datetime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(wd);
        date = new SimpleDateFormat("yyyy/MM/dd").format(wd);
        result.setUpdatedDatetime(datetime);
        result.setUpdatedDate(date);
        result.setTitle(escape(rs.getString("title")));
        result.setArticle(escape(rs.getString("article")));
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

  public void deletePost(int id) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite

      String sql = "DELETE FROM posts WHERE id=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1,id);
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

  public void deletePostAll() throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite

      String sql = "DELETE FROM posts";
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

  public void deleteUser() throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite

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

  public void updatePost(int id,String title,String article) throws Exception,SQLException {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/blog", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/blog/WEB-INF/db/blog.db"); //SQLite

      String sql = "UPDATE posts SET title=?, article=? WHERE id=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,title);
      pstmt.setString(2,article);
      pstmt.setInt(3,id);
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
