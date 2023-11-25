import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import model.User;
import model.DBAccess;
import model.Post;

@WebServlet("/")
public class Main extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    DBAccess db = new DBAccess(this.getServletContext());
    //db.create();
    String postId = request.getParameter("post");
    if (postId == null) {
      ArrayList<Post> posts = db.selectPostAll();
      request.setAttribute("blog", posts);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/blog.jsp");
      dispatcher.forward(request, response);
    } else {
      Post post = db.selectPost(Integer.parseInt(postId));
      if (post.getId() == 0) {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "投稿ページが見つかりません");
        //response.sendRedirect(request.getContextPath()+"/404.jsp");
      } else {
        request.setAttribute("post", post);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/post.jsp");
        dispatcher.forward(request, response);
      }
    }
  }
}
