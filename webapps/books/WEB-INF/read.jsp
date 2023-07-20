<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Book,java.util.ArrayList" %>
<%
  ArrayList<Book> books = (ArrayList<Book>)request.getAttribute("books");
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
  <form action="/books/read" method="POST">
    <p><label>ISBNもしくは書籍名：<input type="text" name="keyword"></label></p>
    <p><button type="submit">表示</button></p>
  </form>
  <p><span style="margin-right: 30px"><a href="/books">トップ</a></span><a href="/books/write">保存ページ</a></p>
  <hr>
  <% if (book != null && book.getDelete() != null) { %>
    <p>ISBN:<%= book.getDelete() %>を削除しました。</p>
  <% } %>

  <% if (books != null) { %>
    <% for (Book bk : books) { %>
      <p>ISBN：<%= bk.getISBN() %></p>
      <p>書籍名：<%= bk.escape(bk.getName()) %></p>
      <p>価格：<%= bk.getPrice() %></p>
      <p>ページ数：<%= bk.getPage() %></p>
      <p>発売日：<%= bk.getDate() %></p>
      <form action="/books/read" method="POST" style="display:inline-block">
        <input type="hidden" name="delete" value="<%= bk.getISBN() %>">
        <p><button type="submit">削除</button></p>
      </form>
      <form action="/books/update" method="POST" style="display:inline-block">
        <input type="hidden" name="update" value="<%= bk.getISBN() %>">
        <input type="hidden" name="name" value="<%= bk.escape(bk.getName()) %>">
        <input type="hidden" name="price" value="<%= bk.getPrice() %>">
        <input type="hidden" name="page" value="<%= bk.getPage() %>">
        <input type="hidden" name="date" value="<%= bk.getDate() %>">
        <p><button type="submit">修正</button></p>
      </form>
      <p>-------------------------</p>
      <br>
    <% } %>
  <% } %>
</body>
</html>