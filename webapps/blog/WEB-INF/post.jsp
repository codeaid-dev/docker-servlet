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
  <h1><%= post.getTitle() %></h1>
  <p>作成日時：<%= post.getCreatedDate() %><br>
  更新日時：<%= post.getUpdatedDate() %></p>
  <div>
    <p>
      <% String article = post.getArticle().replace("\n","<br>"); %>
      <%= article %>
    </p>
  </div>
  <p><a href="/blog/">トップ</a></p>
</body>
</html>