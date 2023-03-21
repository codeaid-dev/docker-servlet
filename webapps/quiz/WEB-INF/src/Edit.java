import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import model.Quiz;
import model.DBAccess;

@WebServlet("/edit")
public class Edit extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    DBAccess db = new DBAccess(this.getServletContext());
    ArrayList<Quiz> quiz_list = db.select(null);
    request.setAttribute("quizlist", quiz_list);
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/edit.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    DBAccess db = new DBAccess(this.getServletContext());
    ArrayList<Quiz> quiz_list = db.select(null);
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
          db.update(quiz);
          quiz.setInfo("番号"+quiz.getID()+"の問題を修正しました。");
        }
        if (request.getParameter("delete") != null) { // 削除
          db.delete(quiz);
          quiz.setInfo("番号"+quiz.getID()+"の問題を削除しました。");
        }
        if (request.getParameter("get") != null) { // 読込
          ArrayList<Quiz> ql = db.select(quiz);
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
  }
}
