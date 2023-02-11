<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>どこつぶ</title>
</head>
<body>
  <h1>どこつぶへようこそ</h1>
  <form action="/sukkiri/Login" method="POST">
    ユーザー名:<input type="text" name="name"><br>
    パスワード:<input type="password" name="pass"><br>
    <input type="submit" value="ログイン">
  </form>
</body>
</html>