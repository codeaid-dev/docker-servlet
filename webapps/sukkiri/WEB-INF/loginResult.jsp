<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
User loginUser = (User)session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>どこつぶ</title>
</head>
<body>
  <h1>どこつぶログイン</h1>
  <% if (loginUser != null) { %>
    <p>ログインに成功しました</p>
    <p>ようこそ<%= loginUser.getName() %>さん</p>
    <a href="/sukkiri/Main">つぶやき投稿・閲覧へ</a>
  <% } else { %>
    <p>ログインに失敗しました</p>
    <a href="/sukkiri/">TOPへ</a>
  <% } %>
</body>
</html>