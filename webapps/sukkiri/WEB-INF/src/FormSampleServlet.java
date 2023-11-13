import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FormSampleServlet")
public class FormSampleServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // request.setCharacterEncoding("UTF-8"); // Filterで実装したため不要
    String name = request.getParameter("name");
    String gender = request.getParameter("gender");

    String errorMsg = "";
    if (name == null || name.length() == 0) {
      errorMsg += "名前が入力されていません<br>";
    }
    if (gender == null || gender.length() == 0) {
      errorMsg += "性別が入力されていません<br>";
    } else {
      if (gender.equals("0")) { gender = "男性"; }
      else if (gender.equals("1")) { gender = "女性"; }
    }

    String msg = name + "さん（" + gender + "）を登録しました";
    if (errorMsg.length() != 0) {
      msg = errorMsg;
    }

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<title>ユーザー登録結果</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<p>"+msg+"</p>");
    out.println("</body>");
    out.println("</html>");
  }
}
