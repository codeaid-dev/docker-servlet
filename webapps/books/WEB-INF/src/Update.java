import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Book;
import model.DBAccess;

@WebServlet("/update")
public class Update extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    try {
      String isbn = request.getParameter("isbn");
      String update = request.getParameter("update");
      String name = request.getParameter("name");
      String price = request.getParameter("price");
      String page = request.getParameter("page");
      String date = request.getParameter("date");

      Book book = null;
      if (update != null) {
        book = new Book(update,name,price,page,date);
      } else {
        book = new Book(isbn,name,price,page,date);
        if (!price.chars().allMatch(Character::isDigit)) {
          book.setInfo("価格は数字で入力してください。");
        }
        if (!page.chars().allMatch(Character::isDigit)) {
          book.setInfo("ページ数は数字で入力してください。");
        }
        if (book.getInfo().size() == 0) {
          DBAccess.update(book);
          book.setUpdateDone(true);
        }
      }
      request.setAttribute("book", book);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/update.jsp");
      dispatcher.forward(request, response);
    } catch (Exception e) {
      out.println(e.getMessage());
    }
  }
}
