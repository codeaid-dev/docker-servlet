<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.Person" %>
<%
Person person = (Person)application.getAttribute("data");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>アプリケーションスコープ</title>
</head>
<body>
  <h1>アプリケーションスコープ</h1>
  <p>
    <%= person.getName() %>さんの趣味は、<br>
    <c:out value="${data.hobby}" /><br>
    です。
  </p>
  <a href="/sample/ascope">戻る</a>
</body>
</html>
<% application.removeAttribute("data"); %>