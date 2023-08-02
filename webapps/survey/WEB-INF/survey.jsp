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
  <h1>アンケート</h1>
  <table>
    <% if (survey.containsKey("error") && survey.get("error")!=null) { %>
      <tr>
        <td>以下のエラーを修正してください：</td>
      </tr><tr>
        <td style="color:red;"><ul>
          <li><%= survey.get("error") %></li>
        </ul></td>
      </tr>
    <% } %>
  </table>
  <form action="/survey/" method="POST">
    <table>
      <tr>
      <td><label>名前：</td><td><input type="text" name="name" value="<%= survey.get("name") %>" required></label></td>
      </tr><tr>
      <td><label>メールアドレス：</td><td><input type="email" name="email" value="<%= survey.get("email") %>" required></label></td>
      </tr><tr>
      <td><label>年齢：</td><td><input type="number" name="age" min="18" max="110" value="<%= survey.get("age") %>" required></label></td>
      </tr>
    </table>
    <p>
    <fieldset style="width:500px;">
      <legend>興味のあるプログラム言語：</legend>
      <%
      String[] progs = survey.get("program").split("\\|");
      String checked = Arrays.asList(progs).contains("PHP") ? "checked" : "";
      %>
      <label><input type="checkbox" name="program" value="PHP" <%= checked %>>PHP</label>
      <% checked = Arrays.asList(progs).contains("JavaScript") ? "checked" : ""; %>
      <label><input type="checkbox" name="program" value="JavaScript" <%= checked %>>JavaScript</label>
      <% checked = Arrays.asList(progs).contains("Python") ? "checked" : ""; %>
      <label><input type="checkbox" name="program" value="Python" <%= checked %>>Python</label>
      <% checked = Arrays.asList(progs).contains("Java") ? "checked" : ""; %>
      <label><input type="checkbox" name="program" value="Java" <%= checked %>>Java</label>
      <% checked = Arrays.asList(progs).contains("C/C++") ? "checked" : ""; %>
      <label><input type="checkbox" name="program" value="C/C++" <%= checked %>>C/C++</label>
      <% checked = Arrays.asList(progs).contains("C#") ? "checked" : ""; %>
      <label><input type="checkbox" name="program" value="C#" <%= checked %>>C#</label>
      <% checked = Arrays.asList(progs).contains("Ruby") ? "checked" : ""; %>
      <label><input type="checkbox" name="program" value="Ruby" <%= checked %>>Ruby</label>
    </fieldset>
    <fieldset style="width:500px;">
      <legend>学習に使っているパソコン：</legend>
      <% if (survey.get("pc").equals("ノートPC")) { %>
        <label><input type="radio" name="pc" value="デスクトップPC">デスクトップPC</label>
        <label><input type="radio" name="pc" value="ノートPC" checked>ノートPC</label>
      <% } else { %>
        <label><input type="radio" name="pc" value="デスクトップPC" checked>デスクトップPC</label>
        <label><input type="radio" name="pc" value="ノートPC">ノートPC</label>
      <% } %>
    </fieldset>
    <label for="maker">パソコンメーカー：</label>
    <select name="maker" id="maker">
      <option value="その他">選択してください。</option>
      <% String selected = survey.get("maker").equals("lenovo") ? "selected" : ""; %>
      <option value="Lenovo" <%= selected %>>Lenovo</option>
      <% selected = survey.get("maker").equals("dell") ? "selected" : ""; %>
      <option value="DELL" <%= selected %>>DELL</option>
      <% selected = survey.get("maker").equals("hp") ? "selected" : ""; %>
      <option value="HP" <%= selected %>>HP</option>
      <% selected = survey.get("maker").equals("apple") ? "selected" : ""; %>
      <option value="Apple" <%= selected %>>Apple</option>
      <% selected = survey.get("maker").equals("dynabook") ? "selected" : ""; %>
      <option value="Dynabook" <%= selected %>>Dynabook</option>
      <% selected = survey.get("maker").equals("nec") ? "selected" : ""; %>
      <option value="NEC" <%= selected %>>NEC</option>
      <% selected = survey.get("maker").equals("vaio") ? "selected" : ""; %>
      <option value="VAIO" <%= selected %>>VAIO</option>
      <% selected = survey.get("maker").equals("asus") ? "selected" : ""; %>
      <option value="ASUS" <%= selected %>>ASUS</option>
      <% selected = survey.get("maker").equals("self") ? "selected" : ""; %>
      <option value="自作" <%= selected %>>自作</option>
      <% selected = survey.get("maker").equals("other") ? "selected" : ""; %>
      <option value="その他" <%= selected %>>その他</option>
    </select>
    </p><p>
      <label for="comments">コメント：</label><br>
      <textarea name="comments" id="comments" rows="5" cols="40"><%= survey.get("comments")!=null ? survey.get("comments") : "" %></textarea>
    </p>
    <p><button type="submit">送信</button></p>
  </form>
</body>
</html>