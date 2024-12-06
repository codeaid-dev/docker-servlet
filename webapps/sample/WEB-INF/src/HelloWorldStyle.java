import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/helloworldstyle")
public class HelloWorldStyle extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        LocalDateTime lt = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
        PrintWriter out = response.getWriter();
        out.println("<html lang=\"ja\"><head>");
        out.println("<meta charset=\"UTF-8\"><title>サンプル</title>");
        out.println("<link rel=\"stylesheet\" href=\"" + request.getContextPath() + "/css/style.css\"");
        out.println("</head><body>");
        out.println("<h1>サンプルページ</h1>");
        out.println("<p>Hello World!</p>");
        out.println("<p>現在日時："+lt.format(fmt)+"</p>");
        out.println("</body></html>");
    }
}