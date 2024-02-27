import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;

@WebServlet("/admin/logout")
public class Logout extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session != null) {
      User user = (User)session.getAttribute("user");
      if (user != null) {
        String username = User.escape(user.getUsername());
        session.invalidate();
        request.setAttribute("username",username);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/logout.jsp");
        dispatcher.forward(request, response);
      }
    }
  }
}
