<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<%
String[] luckArray = {"超スッキリ","スッキリ","最悪"};
int index = (int)(Math.random()*3);
String luck = luckArray[index];

Date date = new Date();
SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
String today = sdf.format(date);
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>スッキリ占い</title>
</head>
<body>
  <p><%= today %>の運勢は「<%= luck %>」です</p>
</body>
</html>