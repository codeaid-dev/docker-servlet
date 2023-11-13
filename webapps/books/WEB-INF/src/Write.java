import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Book;
import model.DBAccess;

@WebServlet("/write")
public class Write extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/write.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    String isbn = request.getParameter("isbn");
    String update = request.getParameter("update");
    String name = request.getParameter("name");
    String price = request.getParameter("price");
    String page = request.getParameter("page");
    String date = request.getParameter("date");

    Book book = new Book(isbn,name,price,page,date);
    if (!isbn.chars().allMatch(Character::isDigit)) {
      book.setInfo("ISBNは数字で入力してください。");
    }
    if (!price.chars().allMatch(Character::isDigit)) {
      book.setInfo("価格は数字で入力してください。");
    }
    if (!page.chars().allMatch(Character::isDigit)) {
      book.setInfo("ページ数は数字で入力してください。");
    }
    if (update != null) {
      book.setISBN(update);
      if (book.getInfo().size() == 0) {
        DBAccess db = new DBAccess(this.getServletContext());
        db.update(book);
      }
      request.setAttribute("book", book);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/update.jsp");
      dispatcher.forward(request, response);
    } else {
      if (book.getInfo().size() == 0) {
        DBAccess db = new DBAccess(this.getServletContext());
        if (db.select(book.getISBN()).size() != 0) {
          book.setInfo("入力したISBNはすでに保存されています。");
        } else {
          db.insert(book);
          book.setInfo("保存できました。");
        }
      }
      request.setAttribute("book", book);
      RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/write.jsp");
      dispatcher.forward(request, response);
    }
  }
}
