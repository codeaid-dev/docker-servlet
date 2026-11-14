<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Post,java.util.List,java.util.Arrays" %>
<% Post post = (Post)request.getAttribute("post"); %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ブログアプリ</title>
</head>
<body>
  <h1>記事編集</h1>
  <form method="POST">
    <label for="title">タイトル</label><br>
    <input type="text" id="title" name="title" value="<%= post.getTitle() %>" required><br><br>

    <label for="article">記事</label><br>
    <textarea id="article" name="article" rows="5" cols="40" required><%= post.getArticle() %></textarea>
    <input type="hidden" name="id" value="<%= post.getId() %>">
    <p><button type="submit" name="update" style="margin-right:20px;">更新</button><button type="submit" name="delete">削除</button></p>
  </form>
  <p><a href="/blog/admin">トップ</a></p>
</body>
</html>