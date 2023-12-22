import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLSample {
  public static void main(String[] args) throws SQLException {
    String hostname = "mysql";
    String dbname = "sampledb";
    String username = "root";
    String password = "password";
    Connection conn = null;
    Statement stmt = null;
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection("jdbc:mysql://"+ hostname + "/" + dbname, username, password);
      System.out.println("接続成功");

      stmt = conn.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name VARCHAR(20), score INTEGER)");
      System.out.println("テーブル作成");

      stmt.executeUpdate("INSERT INTO users VALUES(1, 'Yamada', 85)");
      stmt.executeUpdate("INSERT INTO users VALUES(2, 'Tanaka', 79)");
      stmt.executeUpdate("INSERT INTO users VALUES(3, 'Suzuki', 63)");
      System.out.println("データ挿入");

      ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE score >= 70");
      System.out.println("70点以上選択");
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int score = rs.getInt("score");
        System.out.println(id + "\t" + name + "\t" + score);
      }
      rs.close();

      stmt.executeUpdate("DROP TABLE users");
      System.out.println("テーブル削除");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (stmt != null) { stmt.close(); }
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
