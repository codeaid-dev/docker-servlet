import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class SQLitePlaceholder {
  public static void main(String[] args) throws SQLException {
    String dbname = "sample.db";
    String table = "CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name TEXT, score INTEGER)";
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:../db/" + dbname);
        PreparedStatement pstmt = conn.prepareStatement(table)) {
      pstmt.executeUpdate();
      System.out.println("テーブル作成");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    int[] ids = {1,2,3};
    String[] names = {"Yamada","Tanaka","Suzuki"};
    int[] scores = {85,79,63};
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:../db/" + dbname);
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users VALUES(?, ?, ?)")) {
      for (int i=0; i<3; i++) {
        pstmt.setInt(1, ids[i]);
        pstmt.setString(2, names[i]);
        pstmt.setInt(3, scores[i]);
        pstmt.executeUpdate();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("データ挿入");

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:../db/" + dbname);
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE score >= ?")) {
      pstmt.setInt(1, 70);
      ResultSet rs = pstmt.executeQuery();
      System.out.println("70点以上選択");
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int score = rs.getInt("score");
        System.out.println(id + "\t" + name + "\t" + score);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:../db/" + dbname);
        PreparedStatement pstmt = conn.prepareStatement("DROP TABLE users")) {
      pstmt.executeUpdate();
      System.out.println("テーブル削除");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
