<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Quiz,java.util.ArrayList" %>
<%
Quiz quiz = (Quiz)request.getAttribute("quiz");
ArrayList<Quiz> quizlist = (ArrayList<Quiz>)request.getAttribute("quizlist");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>クイズ</title>
  <style>
    table {
        border-collapse: collapse;
        border: 2px solid rgb(200, 200, 200);
        letter-spacing: 1px;
        font-family: sans-serif;
        font-size: 0.8rem;
    }
    td,th {
        border: 1px solid rgb(190, 190, 190);
        padding: 5px 10px;
    }
    td {
        text-align: center;
    }
  </style>
</head>
<body>
  <h1>クイズ編集</h1>
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
  <% if (quiz != null && quiz.getInfo() != null) { %>
    <p><%= quiz.getInfo() %></p>
  <% } %>
  <form action="/quiz/edit" method="POST">
    <p><label>番号：<input type="text" name="id" value="<%= id %>" required></label></p>
    <p><label>問題：<input type="text" name="question" value="<%= question %>"></label></p>
    <p><label>答え：<input type="text" name="answer" value="<%= answer %>"></label></p>
    <button type="submit" name="get">読込(番号)</button>
    <button type="submit" name="edit">修正</button>
    <button type="submit" name="delete">削除</button>
  </form>
  <hr>
  <p>クイズ一覧</p>
  <% if (quizlist != null) { %>
    <table>
      <thead>
        <tr>
          <th>番号</th>
          <th>問題</th>
          <th>答え</th>
        </tr>
      </thead>
      <tbody>
        <% for (Quiz l : quizlist) { %>
          <tr><td><%= l.getID() %></td>
          <td><%= l.escape(l.getQuestion()) %></td>
          <td><%= l.escape(l.getAnswer()) %></td></tr>
        <% } %>
      </tbody>
    </table>
  <% } %>
  <hr>
  <p><span style="margin-right: 30px"><a href="/quiz">トップ</a></span><span style="margin-right: 30px"><a href="/quiz/save">新規作成</a></span><a href="/quiz/quiz">出題</a></p>
</body>
</html>