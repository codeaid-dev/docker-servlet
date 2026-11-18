package servlet;

import java.io.IOException;

import dao.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Question;

@WebServlet("/questions/delete")
public class QuestionDelete extends HttpServlet {
  /**
   * 削除確認画面
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
      int id = getId(request);
      Question question =
          new QuestionDAO().findById(id);
      if (question == null) {
        response.sendError(
            HttpServletResponse.SC_NOT_FOUND,
            "指定された問題は存在しません"
        );
        return;
      }
      request.setAttribute("question", question);
      request.getRequestDispatcher(
          "/WEB-INF/jsp/question-delete.jsp")
          .forward(request, response);
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
   * 削除処理
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    try {
      int id = getId(request);
      int result = new QuestionDAO().delete(id);
      if (result == 0) {
          response.sendRedirect(
              request.getContextPath()
              + "/questions?notFound=1"
          );
          return;
      }
      // POST-Redirect-GET
      response.sendRedirect(
          request.getContextPath()
          + "/questions?deleted=1"
      );
    } catch (NumberFormatException e) {
      response.sendError(
          HttpServletResponse.SC_BAD_REQUEST,
          "問題IDが不正です"
      );
    } catch (Exception e) {
      getServletContext().log(
          "問題削除に失敗しました",
          e
      );
      throw new ServletException(e);
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
}
