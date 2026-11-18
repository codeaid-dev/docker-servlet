package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.QuizQuestion;
import util.DBUtil;

public class QuizDAO {
  public List<QuizQuestion> findAll() throws Exception {
    List<QuizQuestion> questions = new ArrayList<>();
    String sql = """
        SELECT
            id,
            question,
            choice1,
            choice2,
            choice3,
            choice4,
            correct_answer,
            explanation
        FROM questions
        ORDER BY id
        """;
    try (Connection conn = DBUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()) {
      while (rs.next()) {
        QuizQuestion q = new QuizQuestion();
        q.setId(rs.getInt("id"));
        q.setQuestion(rs.getString("question"));
        q.setChoice1(rs.getString("choice1"));
        q.setChoice2(rs.getString("choice2"));
        q.setChoice3(rs.getString("choice3"));
        q.setChoice4(rs.getString("choice4"));
        q.setCorrectAnswer(rs.getInt("correct_answer"));
        q.setExplanation(rs.getString("explanation"));
        questions.add(q);
      }
    }
    return questions;
  }
}
