<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
String username = (String)session.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sessionサンプル</title>
</head>
<body>
  <h1>Sessionサンプル</h1>
  <p>
    <%= username %> - ログイン中です。
  </p>
  <form action="/sample/sscope" method="post">
    <button type="submit" name="logout">ログアウト</button>
  </form>
</body>
</html>
