import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import model.User;
import model.DBAccess;

@WebServlet("/")
public class Main extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    DBAccess db = new DBAccess(this.getServletContext());
    db.create();
    HashMap<String,String> survey = new HashMap<>();
    survey.put("name","");
    survey.put("email","");
    survey.put("age","");
    survey.put("program","");
    survey.put("pc","");
    survey.put("maker","");
    survey.put("comments","");
    request.setAttribute("survey", survey);
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/survey.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String age = request.getParameter("age");
    String program = String.join("|",request.getParameterValues("program"));
    String pc = request.getParameter("pc");
    String maker = request.getParameter("maker");
    String comments = request.getParameter("comments");

    HashMap<String,String> survey = new HashMap<String,String>();
    survey.put("name",name);
    survey.put("email",email);
    survey.put("age",age);
    survey.put("program",program);
    survey.put("pc",pc);
    survey.put("maker",maker);
    survey.put("comments",comments);
    request.setAttribute("survey", survey);

    DBAccess db = new DBAccess(this.getServletContext());
    if (db.existMail(email)) {
      survey.put("error","すでにこのメールアドレスで回答済みです。");
    } else {
      Pattern p = Pattern.compile("[!-~]+@[\\w\\-.]+\\.[a-zA-Z]+");
      Matcher m = p.matcher(email);
      if (!m.matches()) {
        survey.put("error","正しいメールアドレスを入力してください。");
      } else {
        String[] columns = {name,email,age,program,pc,maker,comments};
        db.addSurvey(columns);
        String str = String.format("""
          <!DOCTYPE html>
          <html lang="ja">
          <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>アンケート</title>
          </head>
          <body>
            <h2>ご回答ありがとうございました。</h2>
            <p>
            名前：%s<br>
            メールアドレス：%s<br>
            年齢：%s<br>
            興味のあるプログラム言語：%s<br>
            学習に使われるパソコン：%s<br>
            パソコンメーカー：%s<br>
            コメント：<br>%s
            </p>
            <p><a href="/survey">トップ</a></p>
          </body>
          </html>
          """,db.escape(name),db.escape(email),age,program,pc,maker,db.escape(comments).replace("\n","<br>"));
          out.println(str);
          return;
      }
    }
    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/survey.jsp");
    dispatcher.forward(request, response);
  }
}
