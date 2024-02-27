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

import model.User;
import model.Post;
import model.DBAccess;

@WebServlet("/admin")
public class Admin extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      HttpSession session = request.getSession(false);
      if (session == null || (session != null && session.getAttribute("user") == null)) {
        response.sendRedirect("/blog/admin/login");
      } else {
        DBAccess db = new DBAccess();
        ArrayList<Post> posts = db.selectPostAll();
        request.setAttribute("blog",posts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/main.jsp");
        dispatcher.forward(request, response);
      }
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }
}
