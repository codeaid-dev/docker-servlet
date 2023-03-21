import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Random;

import model.Quiz;
import model.DBAccess;

@WebServlet("/quiz")
public class Question extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    DBAccess db = new DBAccess(this.getServletContext());
    ArrayList<Quiz> quiz_list = db.select(null);
    if (quiz_list.size() != 0) {
      int index = new Random().nextInt(quiz_list.size());
      Quiz quiz = quiz_list.get(index);
      request.setAttribute("quiz", quiz);
    }
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/quiz.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String id = request.getParameter("id");
    String question = request.getParameter("question");
    String answer = request.getParameter("answer");
    Quiz quiz = new Quiz(id,question,answer);
    request.setAttribute("quiz", quiz);
    DBAccess db = new DBAccess(this.getServletContext());
    ArrayList<Quiz> quiz_list = db.select(quiz);
    String right = quiz_list.get(0).getAnswer();
    if (right.equals(answer)) {
      request.setAttribute("result", "正解です");
    } else {
      request.setAttribute("result", "不正解です(正解："+right+")");
    }
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/quiz.jsp");
    dispatcher.forward(request, response);
  }
}
