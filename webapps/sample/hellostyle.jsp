<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.time.*, java.time.format.DateTimeFormatter" %>
<% String hello = "ハローワールド！！"; %>
<%
LocalDateTime lt = LocalDateTime.now();
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
String date = "現在日時：" + lt.format(fmt);
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <title>サンプルJSP</title>
</head>
<body>
  <h1>サンプルアプリ JSPページ</h1>
  <%= hello %>
  <br>
  <%= date %>
</body>
</html>