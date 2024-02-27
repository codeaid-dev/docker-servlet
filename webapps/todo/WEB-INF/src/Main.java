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
import java.util.List;

import model.User;
import model.DBAccess;

@WebServlet("/")
public class Main extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null || (session != null && session.getAttribute("user") == null)) {
      response.sendRedirect("/todo/login");
    } else {
      // ToDo追加とリスト表示
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/main.jsp");
      dispatcher.forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    String task = request.getParameter("task");
    HttpSession session = request.getSession();
    User user = (User)session.getAttribute("user");
    try {
      DBAccess.insert(user.getUsername(), task);
      user = DBAccess.select(user.getUsername());
      session.setAttribute("user", user);
      response.sendRedirect("/todo/");
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }
}
