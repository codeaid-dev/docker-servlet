import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.DBAccess;

@WebServlet("/read")
public class Read extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/read.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String keyword = request.getParameter("keyword");
    String delete = request.getParameter("delete");
    DBAccess db = new DBAccess(this.getServletContext());
    ArrayList<Book> results = null;
    Book result = null;
    if (keyword != null) {
      results = db.select(keyword);
    } else if (delete != null) {
      result = new Book();
      result.setDelete(delete);
      db.delete(result);
    }
    request.setAttribute("book", result);
    request.setAttribute("books", results);
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/read.jsp");
    dispatcher.forward(request, response);
  }
}
