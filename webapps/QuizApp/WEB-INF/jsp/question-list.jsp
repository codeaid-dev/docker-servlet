<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>問題一覧</title>
  <link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/WEB-INF/jsp/header.jsp"/>
  <h1>問題一覧</h1>
  <c:if test="${param.registered eq '1'}">
    <p style="color:green;">
        問題を登録しました。
    </p>
  </c:if>
  <c:if test="${param.updated eq '1'}">
    <p style="color:green;">
        問題を更新しました。
    </p>
  </c:if>
  <c:if test="${param.deleted eq '1'}">
    <p style="color:green;">
        問題を削除しました。
    </p>
  </c:if>
  <c:if test="${param.notFound eq '1'}">
    <p style="color:red;">
        対象の問題は既に存在しません。
    </p>
  </c:if>
  <p>
    <a href="${pageContext.request.contextPath}/questions/register">
        新しい問題を登録する
    </a>
  </p>
  <table cellpadding="8">
    <tr>
      <th>ID</th>
      <th>カテゴリー</th>
      <th>問題文</th>
      <th>正解番号</th>
      <th>操作</th>
    </tr>
    <c:forEach var="q" items="${questions}">
      <tr>
        <td>
          <c:out value="${q.id}"/>
        </td>
        <td>
          <c:out value="${q.categoryName}"/>
        </td>
        <td>
          <c:out value="${q.question}"/>
        </td>
        <td>
          <c:out value="${q.correctAnswer}"/>
        </td>
        <td>
          <a href="${pageContext.request.contextPath}/questions/edit?id=${q.id}">
              編集
          </a>
          |
          <a href="${pageContext.request.contextPath}/questions/delete?id=${q.id}">
              削除
          </a>
        </td>
      </tr>
    </c:forEach>
  </table>
  <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
