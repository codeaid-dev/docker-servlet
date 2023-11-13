import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Person;

@WebServlet("/ascope")
public class Ascope extends HttpServlet {
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
        <title>アプリケーションサンプル</title>
      </head>
      <body>
        <h1>アプリケーションサンプル</h1>
        <form action="/sample/ascope" method="POST">
          <p><label>名前:<input type="text" name="name"></label></p>
          <p><label>趣味:<input type="text" name="hobby"></label></p>
          <button type="submit">表示</button>
        </form>
      </body>
      </html>
      """);
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String name = request.getParameter("name");
    String hobby = request.getParameter("hobby");

    Person person = new Person();
    person.setName(escape(name));
    person.setHobby(hobby);

    ServletContext application = this.getServletContext();
    application.setAttribute("data", person);

    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ascope.jsp");
    dispatcher.forward(request, response);
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
