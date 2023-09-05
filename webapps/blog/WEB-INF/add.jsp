<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ブログアプリ</title>
</head>
<body>
  <h1>記事追加</h1>
  <form method="POST">
    <label for="title">タイトル</label><br>
    <input type="text" id="title" name="title" required><br><br>

    <label for="article">記事</label><br>
    <textarea id="article" name="article" rows="5" cols="40" required></textarea>
    <p><button type="submit" name="add">追加</button></p>
  </form>
  <p><a href="/blog/">トップ</a></p>
</body>
</html>