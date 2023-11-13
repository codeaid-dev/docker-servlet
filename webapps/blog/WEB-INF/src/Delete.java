import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;
import model.DBAccess;

@WebServlet("/admin/delete")
public class Delete extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null || (session != null && session.getAttribute("user") == null)) {
      response.sendRedirect("/blog/admin/login");
    } else {
      // ユーザー登録解除
      User user = (User)session.getAttribute("user");
      String username = user.getUsername();
      session.invalidate();
      request.setAttribute("username",username);
      DBAccess db = new DBAccess(this.getServletContext());
      db.deleteUser();
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/delete.jsp");
      dispatcher.forward(request, response);
    }
  }
}
