import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sscope")
public class Sscope extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    HttpSession session = request.getSession(false);
    if (session == null || (session != null && session.getAttribute("username") == null)) {
      PrintWriter out = response.getWriter();
      out.println("""
        <html lang="ja">
        <head>
          <title>Sessionサンプル</title>
        </head>
        <body>
          <h2>Sessionサンプル</h2>
          <form action="/sample/sscope" method="POST">
            <p><label>ユーザー名：<input type="text" name="username"></label></p>
            <p><label>パスワード：<input type="password" name="password"></label></p>
            <button type="submit" name="login">ログイン</button>
          </form>
        </body>
        </html>
        """);
    } else {
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sscope.jsp");
      dispatcher.forward(request, response);
    }
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    HttpSession session = request.getSession();
    if (request.getParameter("login") != null) {
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      if (password.equals("1234")) {
        session.setMaxInactiveInterval(30); //セッション有効期限30秒
        session.setAttribute("username", escape(username));
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/sscope.jsp");
        dispatcher.forward(request, response);
      } else {
        PrintWriter out = response.getWriter();
        out.println("""
          <html lang="ja">
          <head>
            <title>Sessionサンプル</title>
          </head>
          <body>
            <h2>Sessionサンプル</h2>
            <p>パスワードが違います。</p>
            <a href="/sample/sscope">トップ</a>
          </body>
          </html>
          """);
      }
    } else {
      session.removeAttribute("username"); //セッション破棄
      session.invalidate();
      response.sendRedirect("/sample/sscope");
    }
  }

  private static String escape(String str) {
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
