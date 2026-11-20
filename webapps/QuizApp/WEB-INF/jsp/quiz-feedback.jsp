<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>採点結果</title>
  <link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
  <jsp:include page="/WEB-INF/jsp/header.jsp"/>
  <h1>採点結果</h1>
  <c:choose>
    <c:when test="${correct}">
      <h2 style="color:blue">正解です！</h2>
    </c:when>
    <c:otherwise>
      <h2 style="color:red">不正解です。</h2>
    </c:otherwise>
  </c:choose>
  <p>
    問題：
    <c:out value="${question.question}" />
  </p>
  <p>
    あなたの回答：
    <c:choose>
      <c:when test="${selectedAnswer == 1}">
        <c:out value="${question.choice1}" />
      </c:when>
      <c:when test="${selectedAnswer == 2}">
        <c:out value="${question.choice2}" />
      </c:when>
      <c:when test="${selectedAnswer == 3}">
        <c:out value="${question.choice3}" />
      </c:when>
      <c:otherwise>
        <c:out value="${question.choice4}" />
      </c:otherwise>
    </c:choose>
  </p>
  <p>
    正解：
    <c:choose>
      <c:when test="${correctAnswer == 1}">
        <c:out value="${question.choice1}" />
      </c:when>
      <c:when test="${correctAnswer == 2}">
        <c:out value="${question.choice2}" />
      </c:when>
      <c:when test="${correctAnswer == 3}">
        <c:out value="${question.choice3}" />
      </c:when>
      <c:otherwise>
        <c:out value="${question.choice4}" />
      </c:otherwise>
    </c:choose>
  </p>
  <h3>解説</h3>
  <p>
    <c:out value="${explanation}" />
  </p>
  <form action="${pageContext.request.contextPath}/quiz"
        method="post">
    <input type="hidden" name="action" value="next">
    <button type="submit">次の問題へ</button>
  </form>
  <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
</body>
</html>
