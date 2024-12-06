import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLSample {
  public static void main(String[] args) {
    String hostname = "mysql";
    String dbname = "sampledb";
    String username = "root";
    String password = "password";
    String table = "CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name VARCHAR(20), score INTEGER)";
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://"+ hostname + "/" + dbname, username, password);
        Statement stmt = conn.createStatement()) {
      stmt.executeUpdate(table);
      System.out.println("テーブル作成");

      stmt.executeUpdate("INSERT INTO users VALUES(1, 'Yamada', 85)");
      stmt.executeUpdate("INSERT INTO users VALUES(2, 'Tanaka', 79)");
      stmt.executeUpdate("INSERT INTO users VALUES(3, 'Suzuki', 63)");
      System.out.println("データ挿入");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    String select = "SELECT * FROM users WHERE score >= 70";
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://"+ hostname + "/" + dbname, username, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(select)) {
      System.out.println("70点以上選択");
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int score = rs.getInt("score");
        System.out.println(id + "\t" + name + "\t" + score);
      }
      stmt.executeUpdate("DROP TABLE users");
      System.out.println("テーブル削除");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
