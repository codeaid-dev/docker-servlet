import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

import model.User;
import model.DBAccess;

@WebServlet("/admin")
public class Admin extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession(false);
    if (session == null || (session != null && session.getAttribute("user") == null)) {
      response.sendRedirect("/survey/admin/login");
    } else {
      DBAccess db = new DBAccess(this.getServletContext());
      ArrayList<HashMap<String,String>> survey = db.selectSurvey();
      for (HashMap<String,String> row : survey) {
        row.put("name",db.escape(row.get("name")));
        row.put("email",db.escape(row.get("email")));
        row.put("comments",db.escape(row.get("comments")));
      }
      session.setAttribute("survey",survey);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/main.jsp");
      dispatcher.forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    DBAccess db = new DBAccess(this.getServletContext());
    String email = request.getParameter("delete");
    if (email != null && !email.isEmpty()) {
      db.deleteSurvey(email);
    }
    if (request.getParameter("delall") != null) {
      db.deleteSurveyAll();
    }
    if (request.getParameter("download") != null) {
      String filename = "download.csv";
      response.setHeader("Content-Type","text/csv; charset=utf-8");
      response.setHeader("Content-Disposition","attachment; filename=\""+filename+"\"");
      try (PrintWriter out = response.getWriter()) {
        String str = "回答日時,名前,メールアドレス,年齢,興味のあるプログラム言語,学習に使っているパソコン,パソコンメーカー,コメント\n";
        ArrayList<HashMap<String,String>> survey = db.selectSurvey();
        for (HashMap<String,String> row : survey) {
          str += row.get("created_at")+","+row.get("name")+","+row.get("email")+","+row.get("age")+","+row.get("program")+","+row.get("pc")+","+row.get("maker")+","+"\""+row.get("comments")+"\""+"\n";
        }
        out.print(str);
      } catch (IOException e) {
        log(e.getMessage(), e);
      }
    }
    response.sendRedirect("/survey/admin");
  }
}
