<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
String username = (String)request.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ToDoアプリ</title>
</head>
<body>
  <p><%= User.escape(username) %> - ログアウトしました。</p>
  <p><a href="/todo/login">ログイン</a>
  <a href="/todo/signup" style="margin-left: 20px;">ユーザー登録</a></p>
</body>
</html>