package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Category;
import util.DBUtil;

public class CategoryDAO {
  /**
   * 全カテゴリーを取得
   */
  public List<Category> findAll() throws Exception {
    List<Category> list = new ArrayList<>();
    String sql = """
        SELECT id, name
        FROM categories
        ORDER BY id
        """;
    try (Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        Category category = new Category(
          rs.getInt("id"),
          rs.getString("name")
        );
        list.add(category);
      }
    }
    return list;
  }
  /**
   * カテゴリーの存在確認
   */
  public boolean exists(int id) throws Exception {
    String sql = """
        SELECT 1
        FROM categories
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
