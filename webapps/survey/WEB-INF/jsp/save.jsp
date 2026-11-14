<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.HashMap,java.util.List,java.util.Arrays" %>
<%
HashMap<String, String> survey = (HashMap<String, String>)request.getAttribute("survey");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>アンケート</title>
</head>
<body>
  <h1>ご回答ありがとうございました。</h1>
