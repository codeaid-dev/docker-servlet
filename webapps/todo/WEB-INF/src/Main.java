import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    String task = request.getParameter("task");
    HttpSession session = request.getSession();
    User user = (User)session.getAttribute("user");
    DBAccess db = new DBAccess(this.getServletContext());
    db.insert(user.getUsername(), task);
    user = db.select(user.getUsername());
    session.setAttribute("user", user);
    response.sendRedirect("/todo/");
  }
}
