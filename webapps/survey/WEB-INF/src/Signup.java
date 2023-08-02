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

import model.User;
import model.DBAccess;

@WebServlet("/admin/signup")
public class Signup extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    DBAccess db = new DBAccess(this.getServletContext());
    if (db.existUser()) {
      PrintWriter out = response.getWriter();
      out.println("""
        <!DOCTYPE html>
        <html lang="ja">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>アンケート</title>
        </head>
        <body>
          <p>管理者はすでに登録済みです。</p>
          <p><a href="/survey/admin/login">戻る</a>
        </body>
        </html>
      """);
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
    Pattern p = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!-\\/:-@\\[-`{-~])[!-~]{8,32}$");
    Matcher m = p.matcher(password);
    if (!m.matches()) {
      request.setAttribute("error","パスワードは8~32文字で大小文字英字数字記号をそれぞれ1文字以上含める必要があります。");
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signup.jsp");
      dispatcher.forward(request, response);
    } else {
      db.addUser(user);
      HttpSession session = request.getSession(true);
      session.setAttribute("user", user);
      response.sendRedirect("/survey/admin");
    }
  }
}
