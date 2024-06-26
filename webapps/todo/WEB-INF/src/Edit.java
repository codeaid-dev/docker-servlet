import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import model.User;
import model.DBAccess;

@WebServlet("/edit")
public class Edit extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
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
          try {
            DBAccess.deleteTask(user,id);
            user.removeTask(id);
            response.sendRedirect("/todo/");
          } catch (Exception e) {
            out.println(e.getMessage());
          }
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
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    String task = request.getParameter("task");
    HttpSession session = request.getSession();
    User user = (User)session.getAttribute("user");
    user.setTask(task);
    try {
      DBAccess.update("tasks", user);
      response.sendRedirect("/todo/");
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }
}
