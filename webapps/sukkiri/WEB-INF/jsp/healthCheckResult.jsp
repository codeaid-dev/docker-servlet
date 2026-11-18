<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Health" %>
<%
Health health = (Health)request.getAttribute("health");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>スッキリ健康診断</title>
</head>
<body>
  <h1>スッキリ健康診断の結果</h1>
  <p>
    身長:${health.height}<!--<%= health.getHeight() %>--><br>
    体重:${health.weight}<!--<%= health.getWeight() %>--><br>
    BMI:<%= health.getBmi() %><br>
    体型:<%= health.getBodyType() %>
  </p>
  <a href="/sukkiri/HealthCheck">戻る</a>
</body>
</html>