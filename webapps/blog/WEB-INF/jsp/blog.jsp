<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Post,java.util.ArrayList" %>
<%
ArrayList<Post> posts = (ArrayList<Post>)request.getAttribute("blog");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ブログアプリ</title>
</head>
<body>
  <h1>ブログアプリ</h1>
  <h2>記事一覧</h2>
  <% if (posts.size() != 0) { %>
    <% for (Post post : posts) { %>
      <p><a href=<%= "/blog?post=" + post.getId() %>><%= post.getTitle() %></a><br>
      <span style="font-size:10px; margin-right:10px;">作成日：<%= post.getCreatedDate() %></span>
      <span style="font-size:10px; margin-right:10px;">更新日：<%= post.getUpdatedDate() %></span></p>
    <% } %>
  <% } else { %>
    <p>記事がありません。</p>
  <% } %>
</body>
</html>