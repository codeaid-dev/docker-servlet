import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.User;
import model.DBAccess;

@WebServlet("/signup")
public class Signup extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null) {
      response.sendRedirect("/todo/login");
    } else {
      // ユーザー登録画面表示
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signup.jsp");
      dispatcher.forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    User user = new User(username);
    user.setPassword(password, true);
    DBAccess db = new DBAccess(this.getServletContext());
    if (db.existUser(username)) {
      request.setAttribute("error","登録済ユーザーです。");
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signup.jsp");
      dispatcher.forward(request, response);
    } else {
      Pattern p = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!-\\/:-@\\[-`{-~])[!-~]{8,32}$");
      Matcher m = p.matcher(password);
      if (!m.matches()) {
        request.setAttribute("error","パスワードは8~32文字で大小文字英字数字記号をそれぞれ1文字以上含める必要があります。");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signup.jsp");
        dispatcher.forward(request, response);
      } else {
        db.insert(user);
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        response.sendRedirect("/todo/");
      }
    }
  }
}
