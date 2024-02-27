import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Random;

import model.Quiz;
import model.DBAccess;

@WebServlet("/quiz")
public class Question extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      ArrayList<Quiz> quiz_list = DBAccess.select(null);
      if (quiz_list.size() != 0) {
        int index = new Random().nextInt(quiz_list.size());
        Quiz quiz = quiz_list.get(index);
        request.setAttribute("quiz", quiz);
      }
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/quiz.jsp");
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
      String id = request.getParameter("id");
      String question = request.getParameter("question");
      String answer = request.getParameter("answer");
      Quiz quiz = new Quiz(id,question,answer);
      request.setAttribute("quiz", quiz);
      ArrayList<Quiz> quiz_list = DBAccess.select(quiz);
      String right = quiz_list.get(0).getAnswer();
      if (right.equals(answer)) {
        request.setAttribute("result", "正解です");
      } else {
        request.setAttribute("result", "不正解です(正解："+quiz.escape(right)+")");
      }
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/quiz.jsp");
      dispatcher.forward(request, response);
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }
}
