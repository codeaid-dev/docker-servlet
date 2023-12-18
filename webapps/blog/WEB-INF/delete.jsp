<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
String username = (String)request.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>アンケート</title>
</head>
<body>
  <p><%= User.escape(username) %>の管理者登録を解除しました。</p>
  <p><a href="/blog/admin/signup">管理者登録</a></p>
</body>
</html>