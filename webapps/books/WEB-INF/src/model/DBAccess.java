package model;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

import jakarta.servlet.ServletContext;

import model.Book;

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
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/books", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/books/WEB-INF/db/books.db"); //SQLite
      String sql = """
        CREATE TABLE IF NOT EXISTS books (
          isbn VARCHAR(17) NOT NULL PRIMARY KEY,
          name VARCHAR(255) NOT NULL,
          price INTEGER NOT NULL,
          page INTEGER NOT NULL,
          date TEXT NOT NULL)
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

  public void insert(Book book) {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/books", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/books/WEB-INF/db/books.db"); //SQLite
      String sql = "INSERT INTO books (isbn, name, price, page, date) VALUES (?, ?, ?, ?, ?)";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,book.getISBN());
      pstmt.setString(2,book.getName());
      pstmt.setInt(3,Integer.parseInt(book.getPrice()));
      pstmt.setInt(4,Integer.parseInt(book.getPage()));
      pstmt.setString(5,book.getDate());
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

  public ArrayList<Book> select(String... keyword) {
    ArrayList<Book> result = new ArrayList<Book>();
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/books", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/books/WEB-INF/db/books.db"); //SQLite
      ResultSet rs = null;
      if (keyword.length != 0) {
        String name = "%"+keyword[0]+"%";
        String sql = "SELECT * FROM books WHERE isbn=? OR name LIKE ?";
        app.log(keyword[0] + " " + name);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,keyword[0]);
        pstmt.setString(2,name);
        rs = pstmt.executeQuery();
      } else {
        String sql = "SELECT * FROM books";
        Statement stmt =  conn.createStatement();
        rs = stmt.executeQuery(sql);
      }
      while (rs.next()) {
        String isbn = rs.getString("isbn");
        String name = rs.getString("name");
        String price = Integer.toString(rs.getInt("price"));
        String page = Integer.toString(rs.getInt("page"));
        String date = rs.getString("date");
        result.add(new Book(isbn,name,price,page,date));
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

  public void update(Book book) {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/books", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/books/WEB-INF/db/books.db"); //SQLite
      String sql = "UPDATE books SET name=?, price=?, page=?, date=? WHERE isbn=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,book.getName());
      pstmt.setInt(2,Integer.parseInt(book.getPrice()));
      pstmt.setInt(3,Integer.parseInt(book.getPage()));
      pstmt.setString(4,book.getDate());
      pstmt.setString(5,book.getISBN());
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

  public void delete(Book book) {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //MySQL
      //Class.forName("org.sqlite.JDBC"); //SQLite
      conn = DriverManager.getConnection("jdbc:mysql://mysql/books", USER, PASS); //MySQL
      //conn = DriverManager.getConnection("jdbc:sqlite:webapps/books/WEB-INF/db/books.db"); //SQLite
      String sql = "DELETE FROM books WHERE isbn=?";
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1,book.getDelete());
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
