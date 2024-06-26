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

import model.DBAccess;
import model.User;

@WebServlet("/admin/login")
public class Login extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null || (session != null && session.getAttribute("user") == null)) {
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
      dispatcher.forward(request, response);
    } else {
      response.sendRedirect("/blog/admin");
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      DBAccess db = new DBAccess();
      User target = db.selectUser(username);
      if (target != null && target.verifyPassword(password)) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(60); //セッション有効期限60秒
        session.setAttribute("user", target);
        response.sendRedirect("/blog/admin");
      } else {
        request.setAttribute("error","ログイン失敗");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
        dispatcher.forward(request, response);
      }
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }
}
