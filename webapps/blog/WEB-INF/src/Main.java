import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    db.create();
    String postId = request.getParameter("post");
    if (postId == null) {
      ArrayList<Post> posts = db.selectPostAll();
      request.setAttribute("blog", posts);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/blog.jsp");
      dispatcher.forward(request, response);
    } else {
      Post post = db.selectPost(Integer.parseInt(postId));
      request.setAttribute("post", post);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/post.jsp");
      dispatcher.forward(request, response);
    }
  }
}
