import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Mutter;
import model.PostMutterLogic;
import model.User;

@WebServlet("/Main")
public class Main extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    ServletContext application = this.getServletContext();
    List<Mutter> mutterList = (List<Mutter>)application.getAttribute("mutterList");
    if (mutterList == null) {
      mutterList = new ArrayList<>();
      application.setAttribute("mutterList", mutterList);
    }

    HttpSession session = request.getSession();
    User loginUser = (User)session.getAttribute("loginUser");
    if (loginUser == null) {
      response.sendRedirect("/sukkiri/");
    } else {
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/main.jsp");
      dispatcher.forward(request, response);
    }
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // request.setCharacterEncoding("UTF-8"); // Filterで実装したため不要
    String text = request.getParameter("text");
    if (text != null && text.length() != 0) {
      ServletContext application = this.getServletContext();
      List<Mutter> mutterList = (List<Mutter>)application.getAttribute("mutterList");

      HttpSession session = request.getSession();
      User loginUser = (User)session.getAttribute("loginUser");
      Mutter mutter = new Mutter(loginUser.getName(), text);
      PostMutterLogic postMutterLogic = new PostMutterLogic();
      postMutterLogic.execute(mutter, mutterList);
      application.setAttribute("mutterList", mutterList);
    } else {
      request.setAttribute("errorMsg", "つぶやきが入力されていません");
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/main.jsp");
    dispatcher.forward(request, response);
  }
}
