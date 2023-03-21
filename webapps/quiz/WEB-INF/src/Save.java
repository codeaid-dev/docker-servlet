import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Quiz;
import model.DBAccess;

@WebServlet("/save")
public class Save extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/save.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String question = request.getParameter("question");
    String answer = request.getParameter("answer");

    Quiz quiz = new Quiz(question,answer);
    DBAccess db = new DBAccess(this.getServletContext());
    db.insert(quiz);
    quiz.setInfo("保存できました。");
    request.setAttribute("quiz", quiz);
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/save.jsp");
    dispatcher.forward(request, response);
  }
}
