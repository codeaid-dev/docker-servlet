import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.RegisterUserLogic;
import model.User;

@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String forwardPath = null;
    String action = request.getParameter("action");
    if (action == null) {
      forwardPath = "/WEB-INF/registerForm.jsp";
    } else if (action.equals("done")) {
      HttpSession session = request.getSession();
      User registerUser = (User)session.getAttribute("registerUser");
      RegisterUserLogic logic = new RegisterUserLogic();
      logic.execute(registerUser);
      session.removeAttribute("registerUser");
      forwardPath = "/WEB-INF/registerDone.jsp";
    }
    RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
    dispatcher.forward(request, response);
  }
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // request.setCharacterEncoding("UTF-8"); // Filterで実装したため不要
    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String pass = request.getParameter("pass");

    User registerUser = new User(id, name, pass);

    HttpSession session = request.getSession();
    session.setAttribute("registerUser", registerUser);

    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/registerConfirm.jsp");
    dispatcher.forward(request, response);
  }
}
