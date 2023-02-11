<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ユーザー登録もどき</title>
</head>
<body>
  <form action="/sukkiri/FormSampleServlet" method="POST">
    名前：<br>
    <input type="text" name="name"><br>
    性別：<br>
    男<input type="radio" name="gender" value="0">
    女<input type="radio" name="gender" value="1">
    <input type="submit" value="登録">
  </form>
</body>
</html>