<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Quiz" %>
<%
Quiz quiz = (Quiz)request.getAttribute("quiz");
String result = (String)request.getAttribute("result");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>クイズ</title>
</head>
<body>
  <h1>クイズ出題</h1>
  <%
  String id="";
  String question="";
  String answer="";
  if (quiz != null) {
    id = quiz.getID();
    question = quiz.escape(quiz.getQuestion());
    answer = quiz.escape(quiz.getAnswer());
  }
  %>
  <form action="/quiz/quiz" method="POST">
    <input type="hidden" name="id" value="<%= id %>">
    <p>問題：<%= question %></p>
    <input type="hidden" name="question" value="<%= question %>">
    <p><label>答え：<input type="text" name="answer" required></label></p>
    <button type="submit">解答</button>
  </form>
  <h2>結果表示</h2>
  <% if (result != null) { %>
    <p><%= result %></p>
  <% } %>
  <a href="/quiz/quiz"><button type="submit">次の問題</button></a>
  <p><span style="margin-right: 30px"><a href="/quiz">トップ</a></span><span style="margin-right: 30px"><a href="/quiz/save">新規作成</a></span><a href="/quiz/edit">編集</a></p>
</body>
</html>