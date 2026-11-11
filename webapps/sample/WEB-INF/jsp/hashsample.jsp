<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
String username = (String)session.getAttribute("username");
String errorinfo = (String)request.getAttribute("errorinfo");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>パスワード保存とログイン</title>
</head>
<body>
  <h1>パスワード保存とログイン</h1>
  <% if (errorinfo!=null)  { %>
    <p>
      <%= errorinfo %>
    </p>
    <a href="/sample/hashsample">ログインへ</a>
  <% } else if (username!=null) { %>
    <p>
      <%= username %> - ログイン中です。
    </p>
    <form action="/sample/hashsample" method="post">
      <button type="submit" name="logout">ログアウト</button>
    </form>
  <% } else { %>
    <h3>ログイン</h3>
    <form method="POST">
      <label>ユーザー名：<input type="text" name="username" required></label><br>
      <label>パスワード：<input type="password" name="password" required></label><br>
      <button type="submit" name="login">ログイン</button>
    </form>
  <% } %>
  <p>----------</p>
  <h3>ユーザー登録</h3>
  <form method="POST">
    <label>ユーザー名：<input type="text" name="username" required></label><br>
    <label>パスワード：<input type="password" name="password" required></label><br>
    <button type="submit" name="signup">登録</button>
  </form>
  </body>
</html>
