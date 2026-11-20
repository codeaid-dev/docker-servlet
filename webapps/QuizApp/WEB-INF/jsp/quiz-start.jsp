<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>4択クイズ</title>
  <link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/WEB-INF/jsp/header.jsp"/>
  <h1>4択クイズ</h1>
  <c:if test="${not empty errorMessage}">
    <p style="color:red">
      <c:out value="${errorMessage}" />
    </p>
  </c:if>
  <form action="${pageContext.request.contextPath}/quiz"
        method="post">
    <input type="hidden" name="action" value="start">
    <p>
      出題数：
      <input type="number"
              name="count"
              value="5"
              min="1"
              max="50"
              required>
    </p>
    <button type="submit">クイズ開始</button>
  </form>
  <p>
    <a href="${pageContext.request.contextPath}/questions">
        問題一覧に戻る
    </a>
  </p>
  <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
