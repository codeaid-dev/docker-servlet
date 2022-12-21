import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/sqlitesample")
public class SQLiteSampleWeb extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<html lang=\"ja\"><head>");
    out.println("<meta charset=\"UTF-8\"><title>SQLiteSample</title>");
    out.println("</head><body>");

    String dbname = "sample.db";
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:webapps/sample/WEB-INF/db/" + dbname);
      out.println("<p>接続成功</p>");

      stmt = conn.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name TEXT, score INTEGER)");
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
        if (stmt != null) {
          stmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
