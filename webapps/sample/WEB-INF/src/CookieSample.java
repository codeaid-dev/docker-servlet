import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

@WebServlet("/cookie")
public class CookieSample extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("""
      <!DOCTYPE html>
      <html lang="ja">
      <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cookieサンプル</title>
      </head>
      <body>
      <h1>Cookieサンプル</h1>
        """);
    int count = 0;
    boolean init = true;
    Cookie cookies[] = request.getCookies();
    if (cookies != null && cookies.length > 0) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("count")) {
          count = Integer.parseInt(cookie.getValue()) + 1;
          out.println("<h2>アクセス数："+count+"回</h2>");
          cookie.setValue(Integer.toString(count));
          cookie.setMaxAge(30);
          response.addCookie(cookie);
          init = false;
        }
      }
    }
    if (init) {
      count = 1;
      out.println("<h2>アクセス数："+count+"回</h2>");
      Cookie cookie = new Cookie("count", Integer.toString(count));
      cookie.setMaxAge(30); //30秒
      response.addCookie(cookie);
    }
    out.println("""
        <form action="/sample/cookie" method="POST">
          <button type="submit" name="clear">カウントクリア</button>
        </form>
      </body>
      </html>
      """);
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    Cookie cookies[] = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("count")) {
          cookie.setMaxAge(0);
          response.addCookie(cookie);
          response.sendRedirect("/sample/cookie");
        }
      }
    }
  }
}
