package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DBUtil;

public class WrongAnswerDAO {
  public void recordWrong(int questionId) throws Exception {
    String selectSql = """
        SELECT id
        FROM wrong_answers
        WHERE question_id = ?
        """;
    String insertSql = """
        INSERT INTO wrong_answers (
            question_id,
            wrong_count,
            last_wrong_at,
            is_reviewed
        )
        VALUES (?, 1, CURRENT_TIMESTAMP, 0)
        """;
    String updateSql = """
        UPDATE wrong_answers
        SET
            wrong_count = wrong_count + 1,
            last_wrong_at = CURRENT_TIMESTAMP,
            is_reviewed = 0
        WHERE question_id = ?
        """;
    try (Connection conn = DBUtil.getConnection()) {
      conn.setAutoCommit(false);
      try {
        boolean exists;
        try (PreparedStatement pstmt =
              conn.prepareStatement(selectSql)) {
          pstmt.setInt(1, questionId);
          try (ResultSet rs = pstmt.executeQuery()) {
            exists = rs.next();
          }
        }
        if (exists) {
          try (PreparedStatement pstmt =
                conn.prepareStatement(updateSql)) {
            pstmt.setInt(1, questionId);
            pstmt.executeUpdate();
          }
        } else {
          try (PreparedStatement pstmt =
                conn.prepareStatement(insertSql)) {
            pstmt.setInt(1, questionId);
            pstmt.executeUpdate();
          }
        }
        conn.commit();
      } catch (Exception e) {
        conn.rollback();
        throw e;
      } finally {
        conn.setAutoCommit(true);
      }
    }
  }
}
