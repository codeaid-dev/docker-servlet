<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.HashMap,java.util.ArrayList" %>
<%
ArrayList<HashMap<String,String>> survey = (ArrayList<HashMap<String,String>>)session.getAttribute("survey");
%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>アンケート</title>
  <style>
    table {
      border-collapse: collapse;
      border: 2px solid rgb(200, 200, 200);
      letter-spacing: 1px;
      font-family: sans-serif;
      font-size: 0.8rem;
    }
    td,th {
      border: 1px solid rgb(190, 190, 190);
      padding: 5px 10px;
    }
    td {
      text-align: center;
    }
  </style>
</head>
<body>
  <h2>回答一覧</h2>
  <table border="1">
    <tr>
      <th>回答日時</th>
      <th>名前</th>
      <th>メールアドレス</th>
      <th>年齢</th>
      <th>興味のあるプログラム言語</th>
      <th>学習に使っているパソコン</th>
      <th>パソコンメーカー</th>
      <th>コメント</th>
      <th>処理</th>
    </tr>
    <% for (HashMap<String,String> answer : survey) { %>
    <tr>
      <td><%= answer.get("created_at") %></td>
      <td><%= answer.get("name") %></td>
      <td><%= answer.get("email") %></td>
      <td><%= answer.get("age") %></td>
      <td><%= answer.get("program") %></td>
      <td><%= answer.get("pc") %></td>
      <td><%= answer.get("maker") %></td>
      <% String str = answer.get("comments"); %>
      <td>
      <% for (String row : str.split("\n")) { %>
        <%= row %><br>
      <% } %>
      </td>
      <td>
        <form method="POST">
          <input type="hidden" name="delete" value=<%= answer.get("email") %>>
          <button type="submit">削除</button>
        </form>
      </td>
    </tr>
    <% } %>
  </table>
  <form method="POST">
    <p><button type="submit" name="download">ダウンロード</button>
    <button type="submit" name="delall" style="margin-left:20px;">全て削除</button></p>
  </form>
  <p><a href="/survey/admin/logout">ログアウト</a>
  <a href="/survey/admin/delete" style="margin-left: 20px;">管理者登録解除</a></p>
</body>
</html>