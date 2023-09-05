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
    HttpSession session = request.getSession(false);
    if (session == null || (session != null && session.getAttribute("user") == null)) {
      response.sendRedirect("/blog/admin/login");
    } else {
      DBAccess db = new DBAccess(this.getServletContext());
      ArrayList<Post> posts = db.selectPostAll();
      request.setAttribute("blog",posts);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/main.jsp");
      dispatcher.forward(request, response);
    }
  }
}
