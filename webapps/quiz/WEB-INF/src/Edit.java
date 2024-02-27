import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import model.Quiz;
import model.DBAccess;

@WebServlet("/edit")
public class Edit extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      ArrayList<Quiz> quiz_list = DBAccess.select(null);
      request.setAttribute("quizlist", quiz_list);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/edit.jsp");
      dispatcher.forward(request, response);
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      ArrayList<Quiz> quiz_list = DBAccess.select(null);
      request.setAttribute("quizlist", quiz_list);

      String id = request.getParameter("id");
      String question = request.getParameter("question");
      String answer = request.getParameter("answer");

      Quiz quiz = new Quiz(id,question,answer);
      request.setAttribute("quiz",quiz);

      boolean exist = false;
      if (id.chars().allMatch(Character::isDigit)) { // 番号が数字のときに読込・修正・削除を実施
        for (Quiz q : quiz_list) {
          if (q.getID().equals(id)) {
            exist = true;
          }
        }
        if (exist) {
          if (request.getParameter("edit") != null) { // 修正
            DBAccess.update(quiz);
            quiz.setInfo("番号"+quiz.getID()+"の問題を修正しました。");
          }
          if (request.getParameter("delete") != null) { // 削除
            DBAccess.delete(quiz);
            quiz.setInfo("番号"+quiz.getID()+"の問題を削除しました。");
          }
          if (request.getParameter("get") != null) { // 読込
            ArrayList<Quiz> ql = DBAccess.select(quiz);
            if (ql.size() != 0) {
              Quiz q = ql.get(0);
              request.setAttribute("quiz", q);
            }
          }
        } else {
          quiz.setInfo("指定した番号はありません。");
        }
      } else {
        quiz.setInfo("番号は数字を入力してください。");
      }
      
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/edit.jsp");
      dispatcher.forward(request, response);
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }
}
