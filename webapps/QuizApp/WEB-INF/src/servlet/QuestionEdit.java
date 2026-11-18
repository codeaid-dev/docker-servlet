package servlet;

import java.io.IOException;

import dao.CategoryDAO;
import dao.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Question;

@WebServlet("/questions/edit")
public class QuestionEdit extends HttpServlet {
  /**
   * 編集画面表示
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      int id = getId(request);
      QuestionDAO dao = new QuestionDAO();
      Question question = dao.findById(id);
      if (question == null) {
        response.sendError(
            HttpServletResponse.SC_NOT_FOUND,
            "指定された問題は存在しません"
        );
        return;
      }
      request.setAttribute("question", question);
      showForm(request, response);
    } catch (NumberFormatException e) {
        response.sendError(
            HttpServletResponse.SC_BAD_REQUEST,
            "問題IDが不正です"
        );
    } catch (Exception e) {
        throw new ServletException(e);
    }
  }
  /**
   * 問題更新
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    try {
      int id = getId(request);
      int categoryId = Integer.parseInt(
          request.getParameter("categoryId")
      );
      int correctAnswer = Integer.parseInt(
          request.getParameter("correctAnswer")
      );
      String question = getValue(request, "question");
      String choice1 = getValue(request, "choice1");
      String choice2 = getValue(request, "choice2");
      String choice3 = getValue(request, "choice3");
      String choice4 = getValue(request, "choice4");
      String explanation =
          getValue(request, "explanation");
      validate(
          question,
          choice1,
          choice2,
          choice3,
          choice4,
          explanation,
          correctAnswer
      );
      CategoryDAO categoryDAO = new CategoryDAO();
      if (!categoryDAO.exists(categoryId)) {
          throw new IllegalArgumentException(
              "指定されたカテゴリーが存在しません"
          );
      }
      QuestionDAO questionDAO = new QuestionDAO();
      int result = questionDAO.update(
          id,
          categoryId,
          question,
          choice1,
          choice2,
          choice3,
          choice4,
          correctAnswer,
          explanation
      );
      if (result == 0) {
        response.sendError(
            HttpServletResponse.SC_NOT_FOUND,
            "更新対象の問題が存在しません"
        );
        return;
      }
      // POST-Redirect-GET
      response.sendRedirect(
        request.getContextPath()
        + "/questions?updated=1"
      );
    } catch (NumberFormatException e) {
      request.setAttribute(
          "errorMessage",
          "問題ID、カテゴリーID、正解番号が不正です"
      );
      showForm(request, response);
    } catch (IllegalArgumentException e) {
      request.setAttribute(
          "errorMessage",
          e.getMessage()
      );
      showForm(request, response);
    } catch (Exception e) {
      getServletContext().log(
          "問題更新に失敗しました",
          e
      );
      request.setAttribute(
          "errorMessage",
          "問題の更新に失敗しました"
      );
      showForm(request, response);
    }
  }
  /**
   * 問題ID取得
   */
  private int getId(HttpServletRequest request) {
    int id = Integer.parseInt(
        request.getParameter("id")
    );
    if (id <= 0) {
      throw new NumberFormatException();
    }
    return id;
  }
  /**
   * パラメータ取得
   */
  private String getValue(
          HttpServletRequest request,
          String name) {
    String value = request.getParameter(name);
    return value == null ? "" : value.trim();
  }
  /**
   * 入力検証
   */
  private void validate(
          String question,
          String choice1,
          String choice2,
          String choice3,
          String choice4,
          String explanation,
          int correctAnswer) {
    if (question.isEmpty() ||
        choice1.isEmpty() ||
        choice2.isEmpty() ||
        choice3.isEmpty() ||
        choice4.isEmpty()) {
      throw new IllegalArgumentException(
          "問題文と4つの選択肢は必須です"
      );
    }
    if (question.length() > 1000 ||
        choice1.length() > 300 ||
        choice2.length() > 300 ||
        choice3.length() > 300 ||
        choice4.length() > 300 ||
        explanation.length() > 2000) {
      throw new IllegalArgumentException(
          "入力文字数が上限を超えています"
      );
    }
    if (correctAnswer < 1 || correctAnswer > 4) {
      throw new IllegalArgumentException(
          "正解番号は1～4を指定してください"
      );
    }
  }
  /**
   * 編集画面表示
   */
  private void showForm(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      Question question = new QuestionDAO().findById(
          getId(request)
      );
      if (question == null) {
        response.sendError(
            HttpServletResponse.SC_NOT_FOUND
        );
        return;
      }
      request.setAttribute("question", question);
      request.setAttribute("categories",
          new CategoryDAO().findAll()
      );
      // 入力エラー時は入力内容を優先する
      if (request.getAttribute("errorMessage") != null) {
        request.setAttribute("formCategoryId",
            request.getParameter("categoryId")
        );
        request.setAttribute("formQuestion",
            request.getParameter("question")
        );
        request.setAttribute("formChoice1",
            request.getParameter("choice1")
        );
        request.setAttribute("formChoice2",
            request.getParameter("choice2")
        );
        request.setAttribute("formChoice3",
            request.getParameter("choice3")
        );
        request.setAttribute("formChoice4",
            request.getParameter("choice4")
        );
        request.setAttribute("formCorrectAnswer",
            request.getParameter("correctAnswer")
        );
        request.setAttribute("formExplanation",
            request.getParameter("explanation")
        );
      } else {
        request.setAttribute("formCategoryId",
            String.valueOf(question.getCategoryId())
        );
        request.setAttribute("formQuestion",
            question.getQuestion()
        );
        request.setAttribute("formChoice1",
            question.getChoice1()
        );
        request.setAttribute("formChoice2",
            question.getChoice2()
        );
        request.setAttribute("formChoice3",
            question.getChoice3()
        );
        request.setAttribute("formChoice4",
            question.getChoice4()
        );
        request.setAttribute("formCorrectAnswer",
            String.valueOf(question.getCorrectAnswer())
        );
        request.setAttribute("formExplanation",
            question.getExplanation()
        );
      }
      request.getRequestDispatcher(
          "/WEB-INF/jsp/question-edit.jsp")
          .forward(request, response);
    } catch (Exception e) {
      throw new ServletException(
                "問題登録画面の表示に失敗しました。", e);
    }
  }
}
