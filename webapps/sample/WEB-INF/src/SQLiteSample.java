import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteSample {
  public static void main(String[] args) throws SQLException {
    String dbname = "sample.db";
    String table = "CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name TEXT, score INTEGER)";
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:../db/" + dbname);
        Statement stmt = conn.createStatement();) {
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
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:../db/" + dbname);
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
