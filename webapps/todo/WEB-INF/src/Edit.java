import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import model.User;
import model.DBAccess;

@WebServlet("/edit")
public class Edit extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    HttpSession session = request.getSession(false);
    if (session == null) {
      response.sendRedirect("/todo/login");
    } else {
      User user = (User)session.getAttribute("user");
      String id = request.getParameter("edit");
      if (id == null) {
        id = request.getParameter("del");
        if (id == null) {
          response.sendRedirect("/todo/");
        } else {
          // ToDo削除
          DBAccess db = new DBAccess(this.getServletContext());
          db.deleteTask(user,id);
          user.removeTask(id);
          response.sendRedirect("/todo/");
        }
      } else {
        // ToDo編集表示
        user.setEditId(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/edit.jsp");
        dispatcher.forward(request, response);
      }
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // 編集したToDoをDBに保存しトップへリダイレクト
    request.setCharacterEncoding("UTF-8");
    String task = request.getParameter("task");
    HttpSession session = request.getSession();
    User user = (User)session.getAttribute("user");
    user.setTask(task);
    DBAccess db = new DBAccess(this.getServletContext());
    db.update("tasks", user);
    response.sendRedirect("/todo/");
  }
}
