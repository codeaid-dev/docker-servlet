import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import model.DBAccess;

@WebServlet("/delete")
public class Delete extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession(false);
    if (session == null || (session != null && session.getAttribute("user") == null)) {
      response.sendRedirect("/todo/login");
    } else {
      // ユーザー登録解除
      User user = (User)session.getAttribute("user");
      String username = user.getUsername();
      session.invalidate();
      request.setAttribute("username",username);
      try {
        DBAccess.delete(user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/delete.jsp");
        dispatcher.forward(request, response);
      } catch (Exception e) {
        out.println(e.getMessage());
      }
    }
  }
}
