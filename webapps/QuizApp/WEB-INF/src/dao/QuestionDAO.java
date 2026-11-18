package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Question;
import util.DBUtil;

public class QuestionDAO {
  /**
   * 問題登録
   */
  public int insert(
          int categoryId,
          String question,
          String choice1,
          String choice2,
          String choice3,
          String choice4,
          int correctAnswer,
          String explanation) throws Exception {
    String sql = """
        INSERT INTO questions (
            category_id,
            question,
            choice1,
            choice2,
            choice3,
            choice4,
            correct_answer,
            explanation
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
    try (Connection con = DBUtil.getConnection();
        PreparedStatement ps =
            con.prepareStatement(sql)) {
      ps.setInt(1, categoryId);
      ps.setString(2, question);
      ps.setString(3, choice1);
      ps.setString(4, choice2);
      ps.setString(5, choice3);
      ps.setString(6, choice4);
      ps.setInt(7, correctAnswer);
      ps.setString(8, explanation);
      return ps.executeUpdate();
    }
  }
  /**
   * 全問題を取得
   */
  public List<Question> findAll() throws Exception {
    List<Question> list = new ArrayList<>();
    String sql = """
        SELECT
            q.id,
            q.category_id,
            c.name AS category_name,
            q.question,
            q.choice1,
            q.choice2,
            q.choice3,
            q.choice4,
            q.correct_answer,
            q.explanation
        FROM questions q
        INNER JOIN categories c
            ON q.category_id = c.id
        ORDER BY q.id DESC
        """;
    try (Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        Question question = new Question(
          rs.getInt("id"),
          rs.getInt("category_id"),
          rs.getString("category_name"),
          rs.getString("question"),
          rs.getString("choice1"),
          rs.getString("choice2"),
          rs.getString("choice3"),
          rs.getString("choice4"),
          rs.getInt("correct_answer"),
          rs.getString("explanation")
        );
        list.add(question);
      }
    }
    return list;
  }
  /**
   * IDを指定して問題を取得
   */
  public Question findById(int id) throws Exception {
    String sql = """
        SELECT
            q.id,
            q.category_id,
            c.name AS category_name,
            q.question,
            q.choice1,
            q.choice2,
            q.choice3,
            q.choice4,
            q.correct_answer,
            q.explanation
        FROM questions q
        INNER JOIN categories c
            ON q.category_id = c.id
        WHERE q.id = ?
        """;
    try (Connection con = DBUtil.getConnection();
          PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return mapQuestion(rs);
        }
      }
    }
    return null;
  }
  /**
   * ResultSetからQuestionを生成
   */
  private Question mapQuestion(ResultSet rs) throws Exception {
    return new Question(
        rs.getInt("id"),
        rs.getInt("category_id"),
        rs.getString("category_name"),
        rs.getString("question"),
        rs.getString("choice1"),
        rs.getString("choice2"),
        rs.getString("choice3"),
        rs.getString("choice4"),
        rs.getInt("correct_answer"),
        rs.getString("explanation")
    );
  }
  /**
   * 問題更新
   */
  public int update(
          int id,
          int categoryId,
          String question,
          String choice1,
          String choice2,
          String choice3,
          String choice4,
          int correctAnswer,
          String explanation) throws Exception {
    String sql = """
        UPDATE questions
        SET
            category_id = ?,
            question = ?,
            choice1 = ?,
            choice2 = ?,
            choice3 = ?,
            choice4 = ?,
            correct_answer = ?,
            explanation = ?
        WHERE id = ?
        """;

    try (Connection con = DBUtil.getConnection();
          PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, categoryId);
      ps.setString(2, question);
      ps.setString(3, choice1);
      ps.setString(4, choice2);
      ps.setString(5, choice3);
      ps.setString(6, choice4);
      ps.setInt(7, correctAnswer);
      ps.setString(8, explanation);
      ps.setInt(9, id);
      return ps.executeUpdate();
    }
  }
  /**
   * 問題削除
   */
  public int delete(int id) throws Exception {
    String sql = """
        DELETE FROM questions
        WHERE id = ?
        """;
    try (Connection con = DBUtil.getConnection();
          PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      return ps.executeUpdate();
    }
  }
  /**
   * 問題の存在確認
   */
  public boolean exists(int id) throws Exception {
    String sql = """
        SELECT 1
        FROM questions
        WHERE id = ?
        """;
    try (Connection con = DBUtil.getConnection();
          PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    }
  }
}
