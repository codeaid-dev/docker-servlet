import java.io.IOException;
import java.io.PrintWriter;
<<<<<<< HEAD
=======
import java.time.format.DateTimeFormatter;
>>>>>>> 135ba0e9c1b5eceec0e5f59570561e94c8d1a062

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
    String table = "CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name VARCHAR(20), score INTEGER)";
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      out.println(e.getMessage());
    }
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://"+ hostname + "/" + dbname, username, password);
        Statement stmt = conn.createStatement()) {
      stmt.executeUpdate(table);
      out.println("<p>テーブル作成</p>");

      stmt.executeUpdate("INSERT INTO users VALUES(1, 'Yamada', 85)");
      stmt.executeUpdate("INSERT INTO users VALUES(2, 'Tanaka', 79)");
      stmt.executeUpdate("INSERT INTO users VALUES(3, 'Suzuki', 63)");
      out.println("<p>データ挿入</p>");
    } catch (SQLException e) {
      out.println(e.getMessage());
    }

    String select = "SELECT * FROM users WHERE score >= 70";
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://"+ hostname + "/" + dbname, username, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(select)) {
      out.println("<p>70点以上選択</p>");
      out.println("<p>");
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int score = rs.getInt("score");
        out.println(id + "\t" + name + "\t" + score + "<br>");
      }
      out.println("</p>");

      stmt.executeUpdate("DROP TABLE users");
      out.println("<p>テーブル削除</p>");
      out.println("</body></html>");
    } catch (SQLException e) {
      out.println(e.getMessage());
    }
  }
}
