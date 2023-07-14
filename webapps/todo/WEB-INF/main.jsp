<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User,java.util.HashMap,java.util.Map" %>
<%
User user = (User)session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ToDoアプリ</title>
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
  <h1>ToDo - <%= user.escape(user.getUsername()) %></h1>
  <form action="/todo/" method="post">
    <textarea name="task" rows="4" cols="30" placeholder="タスク追加" required></textarea><br>
    <button type="submit" name="add">追加</button>
  </form>
  <br>
  <table>
    <tr>
      <th>タスク</th>
      <th>処理</th>
    </tr>
    <% HashMap<String, String> tasks = user.getTasks(); %>
    <% for (Map.Entry<String, String> entry : tasks.entrySet()) { %>
    <tr>
      <td><%= user.escape(entry.getValue()).replace("\n","<br>") %></td>
      <td><a href=<%= "/todo/edit?del=" + entry.getKey() %>>削除</a>
      <a href=<%= "/todo/edit?edit=" + entry.getKey() %>>修正</a></td>
    </tr>
    <% } %>
  </table>
  <br>
  <a href="/todo/logout">ログアウト</a>
  <a href="/todo/delete" style="margin-left: 20px;">ユーザー登録解除</a>
</body>
</html>