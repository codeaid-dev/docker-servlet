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

@WebServlet("/admin/edit")
public class Edit extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    HttpSession session = request.getSession(false);
    if (session == null || (session != null && session.getAttribute("user") == null)) {
      response.sendRedirect("/blog/admin/login");
    } else {
      String postId = request.getParameter("post");
      if (postId != null) {
        DBAccess db = new DBAccess(this.getServletContext());
        Post post = db.selectPost(Integer.parseInt(postId));
        request.setAttribute("post",post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/edit.jsp");
        dispatcher.forward(request, response);
      } else {
        // 404
      }
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    String title = request.getParameter("title");
    String article = request.getParameter("article");
    int id = Integer.parseInt(request.getParameter("id"));
    DBAccess db = new DBAccess(this.getServletContext());
    if (request.getParameter("update") != null) { //更新
      db.updatePost(id, title, article);
    } else if (request.getParameter("delete") != null) { //削除
      db.deletePost(id);
    }
    response.sendRedirect("/blog/admin");
  }
}