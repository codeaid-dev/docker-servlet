<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>問題削除確認</title>
  <link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/WEB-INF/jsp/header.jsp"/>
  <h1>問題削除確認</h1>
  <p>以下の問題を削除しますか？</p>
  <table cellpadding="8">
    <tr>
      <th>ID</th>
      <td>
        <c:out value="${question.id}"/>
      </td>
    </tr>
    <tr>
      <th>カテゴリー</th>
      <td>
        <c:out value="${question.categoryName}"/>
      </td>
    </tr>
    <tr>
      <th>問題文</th>
      <td>
        <c:out value="${question.question}"/>
      </td>
    </tr>
    <tr>
        <th>正解番号</th>
        <td>
            <c:out value="${question.correctAnswer}"/>
        </td>
    </tr>
  </table>
  <!-- <p style="color:red">
      削除すると、この問題に関連する間違い問題の記録も削除されます。
  </p> -->
  <form action="${pageContext.request.contextPath}/questions/delete"
        method="post">
    <input type="hidden"
            name="id"
            value="${question.id}">
    <input type="hidden"
            name="csrfToken"
            value="<c:out value='${csrfToken}'/>">
    <p><button type="submit">
        削除する
    </button></p>
  </form>
  <p>
    <a href="${pageContext.request.contextPath}/questions">
        キャンセル
    </a>
  </p>
  <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
