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

import model.User;
import model.DBAccess;

@WebServlet("/admin/signup")
public class Signup extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      DBAccess db = new DBAccess();
      if (db.existUser()) {
        out.println("""
          <!DOCTYPE html>
          <html lang="ja">
          <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>ブログアプリ</title>
          </head>
          <body>
            <p>管理者はすでに登録済みです。</p>
            <p><a href="/blog/admin/login">戻る</a>
          </body>
          </html>
        """);
      } else {
        // ユーザー登録画面表示
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signup.jsp");
        dispatcher.forward(request, response);
      }
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();

    try {
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      Pattern p = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!-\\/:-@\\[-`{-~])[!-~]{8,32}$");
      Matcher m = p.matcher(password);
      if (!m.matches()) {
        request.setAttribute("error","パスワードは8~32文字で大小文字英字数字記号をそれぞれ1文字以上含める必要があります。");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/signup.jsp");
        dispatcher.forward(request, response);
      } else {
        User user = new User(username);
        String salt = user.generateSalt();
        password = user.hashPassword(password, salt);
        user.setPassword(password);
        DBAccess db = new DBAccess();
        db.addUser(user);
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        response.sendRedirect("/blog/admin");
      }
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }
}
