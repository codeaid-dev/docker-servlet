<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User,model.Post,java.util.ArrayList" %>
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
  <h1>管理画面</h1>
  <p><a href="/blog/admin/add"><button>新規追加</button></a></p>
  <h2>記事一覧</h2>
  <% if (posts != null && posts.size() != 0) { %>
    <ul>
      <% for (Post post : posts) { %>
        <li>
          <p><%= post.getTitle() %><br>
          <span style="font-size:10px; margin-right:10px;">作成日：<%= post.getCreatedDate() %></span>
          <span style="font-size:10px;">更新日：<%= post.getUpdatedDate() %></span><br>
          <a href=<%= "/blog/admin/edit?post=" + post.getId() %>>編集</a></p>
        </li>
      <% } %>
    </ul>
  <% } else { %>
    <p>記事がありません。</p>
  <% } %>
  <p><a href="/blog/admin/logout">ログアウト</a>
  <a href="/blog/admin/delete" style="margin-left: 20px;">ユーザー登録解除</a></p>
</body>
</html>