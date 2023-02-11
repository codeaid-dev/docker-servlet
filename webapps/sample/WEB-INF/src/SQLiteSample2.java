import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class SQLiteSample2 {
  public static void main(String[] args) throws SQLException {
    String dbname = "sample.db";
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:../db/" + dbname);
      System.out.println("接続成功");

      pstmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name TEXT, score INTEGER)");
      pstmt.executeUpdate();
      System.out.println("テーブル作成");

      pstmt = conn.prepareStatement("INSERT INTO users VALUES(?, ?, ?)");
      pstmt.setInt(1, 1);
      pstmt.setString(2, "Yamada");
      pstmt.setInt(3, 85);
      pstmt.executeUpdate();
      pstmt.setInt(1, 2);
      pstmt.setString(2, "Tanaka");
      pstmt.setInt(3, 79);
      pstmt.executeUpdate();
      pstmt.setInt(1, 3);
      pstmt.setString(2, "Suszuki");
      pstmt.setInt(3, 63);
      pstmt.executeUpdate();
      System.out.println("データ挿入");

      pstmt = conn.prepareStatement("SELECT * FROM users WHERE score >= ?");
      pstmt.setInt(1, 70);
      ResultSet rs = pstmt.executeQuery();
      System.out.println("70点以上選択");
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int score = rs.getInt("score");
        System.out.println(id + "\t" + name + "\t" + score);
      }
      rs.close();

      pstmt = conn.prepareStatement("DROP TABLE users");
      pstmt.executeUpdate();
      System.out.println("テーブル削除");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (pstmt != null) { pstmt.close(); }
        if (conn != null) { conn.close(); }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
