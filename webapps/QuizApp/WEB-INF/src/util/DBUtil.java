package util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletContext;

public class DBUtil {
  private static volatile String dbUrl;
  private static final String USER = "root";
  private static final String PASS = "password";
  private DBUtil() {
  }
  /**
   * アプリケーション起動時の初期化
   */
  public static synchronized void init(
          ServletContext context) throws Exception {
    if (dbUrl != null) {
      return;
    }
    String realPath = context.getRealPath("/WEB-INF/db");
    Path dbDir;
    if (realPath != null) {
      dbDir = Paths.get(realPath);
    } else {
      // getRealPath()がnullの場合の代替保存先
      String appName = context.getContextPath()
          .replaceAll("[^a-zA-Z0-9_-]", "_");
      dbDir = Paths.get(
          System.getProperty("user.home"),
          ".quizapp",
          appName,
          "db"
      );
    }
    Files.createDirectories(dbDir);
    Path dbFile = dbDir.resolve("quiz.db");
    // Class.forName("org.sqlite.JDBC"); // SQLite
    Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL
    // dbUrl = "jdbc:sqlite:" + dbFile.toAbsolutePath(); // SQLite
    dbUrl = "jdbc:mysql://mysql:3306/QuizApp"
        + "?useUnicode=true"
        + "&characterEncoding=UTF-8"
        + "&serverTimezone=Asia/Tokyo"; // MySQL
    try (Connection con = getConnection()) {
      con.setAutoCommit(false);
      try {
        createTables(con);
        insertInitialCategories(con);
        insertInitialQuestions(con);
        con.commit();
      } catch (Exception e) {
        con.rollback();
        throw e;
      }
    } catch (Exception e) {
      dbUrl = null;
      throw e;
    }
    // SQLite
    // context.log(
    //   "QuizApp SQLite DB initialized: "
    //   + dbFile.toAbsolutePath()
    // );
  }
  /**
   * データベース接続
   */
  public static Connection getConnection()
          throws SQLException {
    String url = dbUrl;
    if (url == null) {
      throw new SQLException(
          "データベースが初期化されていません"
      );
    }
    // Connection con = DriverManager.getConnection(url); // SQLite
    Connection con = DriverManager.getConnection(url,USER,PASS); // MySQL
    try (Statement st = con.createStatement()) {
      // SQLiteでは接続ごとに有効化する必要がある
      // st.execute("PRAGMA foreign_keys = ON"); // SQLite
      // st.execute("PRAGMA busy_timeout = 5000"); // SQLite
      return con;
    } catch (SQLException e) {
      con.close();
      throw e;
    }
  }
  /**
   * テーブル作成
   */
  private static void createTables(Connection con)
          throws SQLException {
    try (Statement st = con.createStatement()) {
      // For MySQL using AUTO_INCREMENT, for SQLite using AUTOINCREMENT
      // st.execute("""
      //     CREATE TABLE IF NOT EXISTS categories (
      //         id INTEGER PRIMARY KEY AUTOINCREMENT,
      //         name TEXT NOT NULL UNIQUE
      //     )
      //     """);
      // st.execute("""
      //     CREATE TABLE IF NOT EXISTS questions (
      //         id INTEGER PRIMARY KEY AUTOINCREMENT,
      //         category_id INTEGER NOT NULL,
      //         question TEXT NOT NULL,
      //         choice1 TEXT NOT NULL,
      //         choice2 TEXT NOT NULL,
      //         choice3 TEXT NOT NULL,
      //         choice4 TEXT NOT NULL,
      //         correct_answer INTEGER NOT NULL
      //             CHECK(correct_answer BETWEEN 1 AND 4),
      //         explanation TEXT NOT NULL DEFAULT '',
      //         created_at TEXT NOT NULL
      //             DEFAULT CURRENT_TIMESTAMP,

      //         FOREIGN KEY(category_id)
      //             REFERENCES categories(id)
      //             ON DELETE RESTRICT
      //     )
      //     """);
      // st.execute("""
      //     CREATE TABLE IF NOT EXISTS wrong_answers (
      //         id INTEGER PRIMARY KEY AUTOINCREMENT,
      //         question_id INTEGER NOT NULL UNIQUE,
      //         wrong_count INTEGER NOT NULL DEFAULT 1
      //             CHECK(wrong_count >= 1),
      //         last_wrong_at TEXT NOT NULL
      //             DEFAULT CURRENT_TIMESTAMP,
      //         is_reviewed INTEGER NOT NULL DEFAULT 0
      //             CHECK(is_reviewed IN (0, 1)),

      //         FOREIGN KEY(question_id)
      //             REFERENCES questions(id)
      //             ON DELETE CASCADE
      //     )
      //     """);
      // MySQL
      st.execute("""
          CREATE TABLE IF NOT EXISTS categories (
              id INT PRIMARY KEY AUTO_INCREMENT,
              name VARCHAR(100) NOT NULL UNIQUE
          )
          """);
      st.execute("""
          CREATE TABLE IF NOT EXISTS questions (
              id INT PRIMARY KEY AUTO_INCREMENT,
              category_id INT NOT NULL,
              question VARCHAR(500) NOT NULL,
              choice1 VARCHAR(200) NOT NULL,
              choice2 VARCHAR(200) NOT NULL,
              choice3 VARCHAR(200) NOT NULL,
              choice4 VARCHAR(200) NOT NULL,
              correct_answer INT NOT NULL
                  CHECK(correct_answer BETWEEN 1 AND 4),
              explanation TEXT NOT NULL,
              created_at TIMESTAMP NOT NULL
                  DEFAULT CURRENT_TIMESTAMP,

              FOREIGN KEY(category_id)
                  REFERENCES categories(id)
                  ON DELETE RESTRICT
          )
          """);
      st.execute("""
          CREATE TABLE IF NOT EXISTS wrong_answers (
              id INT PRIMARY KEY AUTO_INCREMENT,
              question_id INT NOT NULL UNIQUE,
              wrong_count INT NOT NULL DEFAULT 1
                  CHECK(wrong_count >= 1),
              last_wrong_at TIMESTAMP NOT NULL
                  DEFAULT CURRENT_TIMESTAMP,
              is_reviewed INT NOT NULL DEFAULT 0
                  CHECK(is_reviewed IN (0, 1)),

              FOREIGN KEY(question_id)
                  REFERENCES questions(id)
                  ON DELETE CASCADE
          )
          """);
    }
  }
  /**
   * 初期カテゴリー登録
   */
  private static void insertInitialCategories(
          Connection con) throws SQLException {
    String sql = """
        INSERT INTO categories(name)
        SELECT ?
        WHERE NOT EXISTS (
            SELECT 1
            FROM categories
            WHERE name = ?
        )
        """;
    String[] categories = {
        "Java",
        "Servlet/JSP",
        "JavaScript",
        "Python",
        "PHP",
        "SQL",
        "その他"
    };
    try (PreparedStatement ps =
            con.prepareStatement(sql)) {
      for (String name : categories) {
        ps.setString(1, name);
        ps.setString(2, name);
        ps.executeUpdate();
      }
    }
  }
  /**
   * 初期問題登録
   */
  private static void insertInitialQuestions(
          Connection con) throws SQLException {
    insertSeedQuestion(
        con,
        "Java",
        "Javaのソースコードをコンパイルすると生成されるファイルの拡張子は？",
        ".java",
        ".class",
        ".jar",
        ".exe",
        2,
        "Javaのソースコードをコンパイルすると、"
        + "通常は.classファイルが生成されます。"
    );
    insertSeedQuestion(
        con,
        "SQL",
        "SQLでデータを検索するときに使用する命令は？",
        "INSERT",
        "UPDATE",
        "SELECT",
        "DELETE",
        3,
        "SELECT文はテーブルからデータを検索するための命令です。"
    );
    insertSeedQuestion(
        con,
        "Servlet/JSP",
        "HttpServletでGETリクエストを処理するメソッドは？",
        "doGet()",
        "doPost()",
        "init()",
        "destroy()",
        1,
        "GETリクエストはHttpServletのdoGet()で処理します。"
    );
  }
  /**
   * 初期問題を1件登録する
   */
  private static void insertSeedQuestion(
        Connection con,
        String categoryName,
        String question,
        String choice1,
        String choice2,
        String choice3,
        String choice4,
        int correctAnswer,
        String explanation) throws SQLException {
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
        SELECT
            c.id,?,?,?,?,?,?,?
        FROM categories c
        WHERE c.name = ?
        AND NOT EXISTS (
            SELECT 1
            FROM questions q
            WHERE q.category_id = c.id
            AND q.question = ?
        )
        """;
    try (PreparedStatement ps =
            con.prepareStatement(sql)) {
      ps.setString(1, question);
      ps.setString(2, choice1);
      ps.setString(3, choice2);
      ps.setString(4, choice3);
      ps.setString(5, choice4);
      ps.setInt(6, correctAnswer);
      ps.setString(7, explanation);
      ps.setString(8, categoryName);
      ps.setString(9, question);
      ps.executeUpdate();
    }
  }
}
