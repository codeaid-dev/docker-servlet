import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    DBAccess db = new DBAccess(this.getServletContext());
    User target = db.selectUser(username);
    if (target != null && target.verifyPassword(password, true)) {
      HttpSession session = request.getSession(true);
      session.setAttribute("user", target);
      response.sendRedirect("/blog/admin");
    } else {
      request.setAttribute("error","ログイン失敗");
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
      dispatcher.forward(request, response);
    }
  }
}
