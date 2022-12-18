import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/helloworld")
public class HelloWorld extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        LocalDateTime lt = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
        PrintWriter out = response.getWriter();
        out.println("<html lang=\"ja\"><head><meta charset=\"UTF-8\"></head><body>");
        out.println("<h1>サンプルページ</h1>");
        out.println("<p>Hello World!</p>");
        out.println("<p>現在日時："+lt.format(fmt)+"</p>");
        out.println("</body></html>");
    }
}