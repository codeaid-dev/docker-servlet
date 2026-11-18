<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.SiteEV" %>
<%
SiteEV siteEV = (SiteEV)application.getAttribute("siteEV");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>湊くんのページ</title>
</head>
<body>
  <h1>湊くんのページへようこそ</h1>
  <p>
    <a href="/sukkiri/MinatoIndex?action=like">よいね</a>:
    <%= siteEV.getLike() %>人
    <a href="/sukkiri/MinatoIndex?action=dislike">よくないね</a>:
    <%= siteEV.getDislike() %>人
  </p>
  <h2>湊くんとは！？</h2>
  <p>・・・</p>
</body>
</html>