<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
String error = (String)request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>アンケート</title>
</head>
<body>
  <h1>ユーザー登録</h1>
  <% if (error!=null) { %>
    <p><%= error %></p>
  <% } %>
  <form action="/survey/admin/signup" method="post">
    <p><label>名前：<input type="text" name="username" required></label><br>
    <label>パスワード：<input type="password" name="password" required></label></p>
    <button type="submit" name="signup">登録</button>
  </form>
  <br>
  <a href="/survey/admin">トップ</a>
</body>
</html>