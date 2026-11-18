package servlet;

import java.io.IOException;

import dao.QuestionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/questions")
public class QuestionList extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
      request.setAttribute(
          "questions",
          new QuestionDAO().findAll()
      );
      request.getRequestDispatcher(
          "/WEB-INF/jsp/question-list.jsp"
      ).forward(request, response);
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
