import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/mysqlsample")
public class MySQLSampleWeb extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html lang=\"ja\"><head>");
    out.println("<meta charset=\"UTF-8\"><title>MySQLSample</title>");
    out.println("</head><body>");

    String hostname = "mysql";
    String dbname = "sampledb";
    String username = "root";
    String password = "password";
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection("jdbc:mysql://"+ hostname + "/" + dbname, username, password);
      out.println("<p>接続成功</p>");

      stmt = conn.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name VARCHAR(20), score INTEGER)");
      out.println("<p>テーブル作成</p>");

      stmt.executeUpdate("INSERT INTO users VALUES(1, 'Yamada', 85)");
      stmt.executeUpdate("INSERT INTO users VALUES(2, 'Tanaka', 79)");
      stmt.executeUpdate("INSERT INTO users VALUES(3, 'Suzuki', 63)");
      out.println("<p>データ挿入</p>");

      ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE score >= 70");
      out.println("<p>70点以上選択</p>");
      out.println("<p>");
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int score = rs.getInt("score");
        out.println(id + "\t" + name + "\t" + score + "<br>");
      }
      rs.close();
      out.println("</p>");

      stmt.executeUpdate("DROP TABLE users");
      out.println("<p>テーブル削除</p>");
      out.println("</body></html>");
    } catch (Exception e) {
      out.println(e.getMessage());
    } finally {
      try {
        if (stmt != null) { stmt.close(); }
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        out.println(e.getMessage());
      }
    }
  }
}
