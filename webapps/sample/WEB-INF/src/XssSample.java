import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/xss")
public class XssSample  extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("""
      <html lang="ja">
      <head>
        <title>クロスサイト・スクリプティング対策</title>
      </head>
      <body>
        <form method="POST">
        <p>名前：<input type="test" name="name"></p>
        <button type="submit">表示</button>
      </body>
      </html>
      """);
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    String name = "";
    if (request.getParameter("name") != null) {
      name = escape(request.getParameter("name"));
    }
    out.println("""
      <html lang="ja">
      <head>
        <title>クロスサイト・スクリプティング対策</title>
      </head>
      <body>""");
    out.println("<p>「"+name+"」さん、こんにちは！</p>");
    out.println("""
        <a href="/sample/xss">戻る</a>
      </body>
      </html>""");
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
