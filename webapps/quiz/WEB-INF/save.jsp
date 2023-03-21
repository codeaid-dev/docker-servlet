<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Quiz" %>
<%
Quiz quiz = (Quiz)request.getAttribute("quiz");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>クイズ</title>
</head>
<body>
  <h1>クイズ新規作成</h1>
  <%
  String question="";
  String answer="";
  if (quiz != null) {
    question = quiz.getQuestion();
    answer = quiz.getAnswer();
  }
  %>
  <% if (quiz != null && quiz.getInfo() != null) { %>
    <p><%= quiz.getInfo() %></p>
  <% } %>
  <form action="/quiz/save" method="POST">
    <p><label>問題：<input type="text" name="question" value="<%= question %>" required></label></p>
    <p><label>答え：<input type="text" name="answer" value="<%= answer %>" required></label></p>
    <button type="submit">保存</button>
  </form>
  <p><span style="margin-right: 30px"><a href="/quiz">トップ</a></span><span style="margin-right: 30px"><a href="/quiz/edit">編集</a></span><a href="/quiz/quiz">出題</a></p>
</body>
</html>