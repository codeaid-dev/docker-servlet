import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.PrintWriter;

import model.User;

@WebServlet("/hashsample")
public class HashSample extends HttpServlet  {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/hashsample.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      // login process
      if (request.getParameter("login") != null) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = HashSampleDB.select(username);
        if (user == null) {
          request.setAttribute("errorinfo", escape(username)+" - ユーザーが登録されていません。");
        } else if (!user.verifyPassword(password)) {
          request.setAttribute("errorinfo", escape(username)+" - パスワードが違います。");
        } else {
          HttpSession session = request.getSession(true);
          session.setMaxInactiveInterval(30);
          session.setAttribute("username", escape(username));
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/hashsample.jsp");
        dispatcher.forward(request, response);
      
      // signup process
      } else if (request.getParameter("signup") != null) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String salt = User.generateSalt();
        String hash = User.hashPassword(password,salt);

        User user = HashSampleDB.select(username);
        if (user != null) {
          HashSampleDB.update(username, hash);
        } else {
          HashSampleDB.insert(username, hash);
        }

      // logout process
      }  else {
          HttpSession session = request.getSession(false);
          if (session != null) {
            session.removeAttribute("username");
            session.removeAttribute("error");
            session.invalidate();
          }
      }

      response.sendRedirect("/sample/hashsample");
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }

  protected String escape(String str) {
    if (str != null) {
      str = str.replaceAll("&","&amp;");
      str = str.replaceAll("<","&lt;");
      str = str.replaceAll(">","&gt;");
      str = str.replaceAll("'","&#39;");
      str = str.replaceAll("\"","&quot;");
      return str;
    }
    return null;
  }

}
