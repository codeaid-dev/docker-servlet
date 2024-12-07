import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLitePlaceholderOld {
  public static void main(String[] args) throws SQLException {
    String dbname = "sample.db";
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = DriverManager.getConnection("jdbc:sqlite:../db/" + dbname);
      System.out.println("接続成功");

      pstmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY, name TEXT, score INTEGER)");
      pstmt.executeUpdate();
      System.out.println("テーブル作成");

      int[] ids = {1,2,3};
      String[] names = {"Yamada","Tanaka","Suzuki"};
      int[] scores = {85,79,63};
      pstmt = conn.prepareStatement("INSERT INTO users VALUES(?, ?, ?)");
      for (int i=0; i<3; i++) {
        pstmt.setInt(1, ids[i]);
        pstmt.setString(2, names[i]);
        pstmt.setInt(3, scores[i]);
        pstmt.executeUpdate();
      }
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
    } catch (SQLException e) {
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
