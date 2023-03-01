<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Book" %>
<%
Book book = (Book)request.getAttribute("book");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>書籍データ庫</title>
</head>
<body>
  <h1>書籍データ庫</h1>
  <%
  String isbn="";
  String name="";
  String price="";
  String pages="";
  String date="";
  if (book != null) {
    isbn = book.getISBN();
    name = book.getName();
    price = book.getPrice();
    pages = book.getPage();
    date = book.getDate();
  }
  %>
  <% if (book.getInfo().size() != 0) { %>
    <ul>
      <% for (String inf : book.getInfo()) { %>
      <li><%= inf %></li>
      <% } %>
    </ul>
  <% } %>
  <form action="/books/update" method="POST">
    <% if (book.getUpdateDone()) { %>
      <p>ISBN：<%= isbn %>(修正しましました)</p>
    <% } else { %>
      <p>ISBN：<%= isbn %></p>
    <% } %>
    <input type="hidden" name="isbn" value="<%= isbn %>">
    <p><label>書籍名：<input type="text" name="name" value="<%= name %>" required></label></p>
    <p><label>価格：<input type="text" name="price" value="<%= price %>" required></label></p>
    <p><label>ページ数：<input type="text" name="page" value="<%= pages %>" required></label></p>
    <p><label>発売日：<input type="date" name="date" value="<%= date %>" required></label></p>
    <button type="submit">修正</button>
  </form>
  <p><span style="margin-right: 30px"><a href="/books">トップ</a></span><a href="/books/read">閲覧ページ</a></p>
</body>
</html>