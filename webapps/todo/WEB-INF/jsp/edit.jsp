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
</head>
<body>
  <h1>ToDo - <%= user.escape(user.getUsername()) %></h1>
  <form action="/todo/edit" method="post">
    <textarea name="task" rows="4" cols="30" required><%=  user.escape(user.getTask()) %></textarea><br>
    <button type="submit" name="edit">修正</button>
  </form>
  <br>
  <a href="/todo/logout">ログアウト</a>
  <a href="/todo/delete" style="margin-left: 20px;">ユーザー登録解除</a>
</body>
</html>