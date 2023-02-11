<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ユーザー登録</title>
</head>
<body>
  <form action="/sukkiri/RegisterUser" method="POST">
    ログインID:<input type="text" name="id"><br>
    パスワード:<input type="password" name="pass"><br>
    名前:<input type="text" name="name"><br>
    <input type="submit" value="確認">
  </form>
</body>
</html>